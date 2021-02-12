package com.sidorov.filemanager.controller;

import com.sidorov.filemanager.controller.task.FileTableUpdateTask;
import com.sidorov.filemanager.model.DriveManager;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;
import com.sidorov.filemanager.model.entity.TableData;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FileTableController implements Initializable {

    @FXML
    private Label driveTotalSpaceLabel;
    @FXML
    private Label driveUnallocatedSpaceLabel;

    @FXML
    private ComboBox<String> diskComboBox;

    @FXML
    private TextField pathTextField;

    @FXML
    private ProgressBar tableLoadProgressBar;

    @FXML
    private TableView<FileEntity> fileTableView;

    private DriveEntity currentDrive;
    private String format;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        format =resources.getString("string.format.file_size");
        initializeTable(resources);
        initializeComboBox();
        updateTable();
    }

    public void clickRefreshButton(ActionEvent actionEvent) {
        updateTable();
    }

    public void selectDiskComboBox(ActionEvent actionEvent) {
        String drive = diskComboBox.getSelectionModel().getSelectedItem();
        if (drive != null && !drive.equals(currentDrive.getName())) {
            currentDrive = DriveManager.getInstance().getDriveByName(drive);
            updateTable();
        }
    }

    public void clickDiskComboBox(MouseEvent mouseEvent) { refreshComboBox(); }

    public void clickGoPreviousDirectoryButton(ActionEvent actionEvent) {
        boolean isNeedUpdateTable = FileControllerUtility.goPreviousDirectory(currentDrive);
        if (isNeedUpdateTable) {
            updateTable();
        }
    }

    public void clickOnItemTableView(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            FileEntity fileEntity = fileTableView.getSelectionModel().getSelectedItem();
            if (fileEntity != null && currentDrive != null) {
                boolean isNeedUpdateTable = FileControllerUtility.performAction(fileEntity, currentDrive);
                if (isNeedUpdateTable) {
                    updateTable();
                }
            }
        }
    }

    public void updateTable() {
        pathTextField.setText(currentDrive.getCurrentPath());
        fileTableView.getItems().clear();
        FileTableUpdateTask task = new FileTableUpdateTask(currentDrive);
        tableLoadProgressBar.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(event -> Platform.runLater(() -> {
            TableData tableData = task.getValue();
            fileTableView.getItems().addAll(tableData.getFiles());
            driveTotalSpaceLabel.setText(String.format(format, tableData.getTotalSpace()));
            driveUnallocatedSpaceLabel.setText(String.format(format, tableData.getUnallocatedSpace()));
        }));
        new Thread(task).start();
    }


    public List<File> getSelectedFiles() {
        // локальная реализация
        return fileTableView.getSelectionModel().getSelectedItems().stream()
                .map(item -> Paths.get(currentDrive.getCurrentPath()).resolve(item.getName()).toFile())
                .collect(Collectors.toList());
    }

    private void initializeComboBox() {
        diskComboBox.getItems().addAll(DriveManager.getInstance().getDrives());
        diskComboBox.getSelectionModel().select(0);
        currentDrive = DriveManager.getInstance().getDriveByName(diskComboBox.getSelectionModel().getSelectedItem());
    }

    private void initializeTable(ResourceBundle resources) {
        TableColumn<FileEntity, String> fileNameColumn = new TableColumn<FileEntity, String>(resources.getString("ui.tableview.files.column.name"));
        fileNameColumn.setCellValueFactory(file -> new SimpleObjectProperty<String>(file.getValue().getName()));

        TableColumn<FileEntity, String> fileTypeColumn = new TableColumn<FileEntity, String>(resources.getString("ui.tableview.files.column.type"));
        fileTypeColumn.setCellValueFactory(file -> new SimpleObjectProperty<String>(file.getValue().getTypeName()));

        TableColumn<FileEntity, Long> fileSizeColumn = new TableColumn<FileEntity, Long>(resources.getString("ui.tableview.files.column.size"));
        fileSizeColumn.setCellValueFactory(file -> new SimpleObjectProperty<Long>(file.getValue().getSize()));

        fileSizeColumn.setCellFactory(column -> {
            return new TableCell<FileEntity, Long>() {
                @Override
                protected void updateItem(Long item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        String text = (item == -1L) ? null : String.format(resources.getString("string.format.file_size"), item);
                        setText(text);
                    }
                }
            };
        });

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(resources.getString("pattern.date"));
        TableColumn<FileEntity, LocalDateTime> fileDateColumn = new TableColumn<FileEntity, LocalDateTime>(resources.getString("ui.tableview.files.column.date"));
        fileDateColumn.setCellValueFactory(file -> new SimpleObjectProperty<LocalDateTime>(file.getValue().getModifiedDate()));
        fileDateColumn.setCellFactory(column -> {
            return new TableCell<FileEntity, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.format(dtf));
                    }
                }
            };
        });
        fileTableView.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (oldValue) {
                fileTableView.getSelectionModel().clearSelection();
            }
        }));
        fileTableView.getColumns().addAll(fileNameColumn, fileTypeColumn, fileDateColumn, fileSizeColumn);
        fileTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void refreshComboBox() {
        int index = diskComboBox.getSelectionModel().getSelectedIndex();
        diskComboBox.getItems().clear();
        diskComboBox.getItems().addAll(DriveManager.getInstance().getDrives());
        diskComboBox.getSelectionModel().select(index);
    }
}
