package com.sidorov.filemanager.controller;

import com.sidorov.filemanager.model.DriveManager;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;
import com.sidorov.filemanager.utility.BundleHolder;
import com.sidorov.filemanager.utility.LocalDriveManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FileTableController implements Initializable {

    @FXML
    private Label fileSystemTotalSpaceLabel;
    @FXML
    private Label fileSystemUnallocatedSpaceLabel;

    @FXML
    private ComboBox<DriveEntity> diskComboBox;

    @FXML
    private TextField pathTextField;

    @FXML
    private ProgressBar progressBarLoadingTable;

    @FXML
    private TableView<FileEntity> fileView;

    private DriveEntity currentDrive;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable(resources);
        initializeComboBox();
        updateFileSystemInfoLabels();
        updateTable();
    }

    public void clickRefreshButton(ActionEvent actionEvent) { updateTable(); }

    public void selectDiskComboBox(ActionEvent actionEvent) {
        DriveEntity drive = diskComboBox.getSelectionModel().getSelectedItem();
        if (drive != null && !drive.equals(currentDrive)) {
            currentDrive = drive;
            updateTable();
            updateFileSystemInfoLabels();
        }
    }

    public void clickDiskComboBox(MouseEvent mouseEvent) { refreshComboBox(); }

    public void goPreviousFilesButton(ActionEvent actionEvent) {
        /* локальная реализация
        if (currentPath.getParent() != null) {
            updateTable(currentPath.getParent());
        }
        */
    }

    public void clickOnItemTableView(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            FileEntity file = fileView.getSelectionModel().getSelectedItem();
            if (file == null) {
                return;
            }
            /* локальная реализация
            Path path = currentPath.resolve(file.getName());
            if (path.toFile().exists()) {
                if (Files.isDirectory(path)) {
                    updateTable(path);
                } else {
                    runFile(path);
                }
            } else {
                AlertUtility.showErrorAlert(BundleHolder.getBundle().getString("message.alert.file_or_directory_does_not_exist"), ButtonType.OK);
            }
            */
        }
    }

    public void updateTable() {
        // локальная реализация
        //Path currentPath = Paths.get(currentDrive.getCurrentPath());
        //pathTextField.setText(currentPath.normalize().toAbsolutePath().toString());
        //
        pathTextField.setText(currentDrive.getName());
        fileView.getItems().clear();
        TableUpdateTask task = new TableUpdateTask(currentDrive);
        progressBarLoadingTable.visibleProperty().bind(task.runningProperty());
        progressBarLoadingTable.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(event -> Platform.runLater(() -> fileView.getItems().addAll(task.getValue())));
        new Thread(task).start();
    }


    public List<File> getSelectedFiles() {
        // локальная реализация
        return fileView.getSelectionModel().getSelectedItems().stream()
                .map(item -> Paths.get(currentDrive.getCurrentPath()).resolve(item.getName()).toFile())
                .collect(Collectors.toList());
    }

    /*
    private void updateTable(String path) {
        currentPath = path;
        updateTable();
    }
    */

    private void initializeComboBox() {
        diskComboBox.setConverter(new StringConverter<DriveEntity>() {
            @Override
            public String toString(DriveEntity object) {
                return object == null ? "" : object.getName();
            }

            @Override
            public DriveEntity fromString(String string) {
                return null;
            }
        });
        refreshComboBox();
        diskComboBox.getSelectionModel().select(0);
        currentDrive = diskComboBox.getSelectionModel().getSelectedItem();
    }

    private void initializeTable(ResourceBundle resources) {
        TableColumn<FileEntity, String> fileNameColumn = new TableColumn<FileEntity, String>(resources.getString("ui.tableview.files.column.name"));
        fileNameColumn.setCellValueFactory(file -> new SimpleObjectProperty<String>(file.getValue().getName()));

        TableColumn<FileEntity, String> fileTypeColumn = new TableColumn<FileEntity, String>(resources.getString("ui.tableview.files.column.type"));
        fileTypeColumn.setCellValueFactory(file -> new SimpleObjectProperty<String>(file.getValue().getType()));

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
        fileDateColumn.setCellValueFactory(file -> new SimpleObjectProperty<LocalDateTime>(file.getValue().getLastDate()));
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
        fileView.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (oldValue) {
                fileView.getSelectionModel().clearSelection();
            }
        }));
        fileView.getColumns().addAll(fileNameColumn, fileTypeColumn, fileDateColumn, fileSizeColumn);
        fileView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void updateFileSystemInfoLabels() {
        String format = BundleHolder.getBundle().getString("string.format.file_size");
        fileSystemTotalSpaceLabel.setText(String.format(format, DriveManager.getInstance().getDriveTotalSpace(currentDrive)));
        fileSystemUnallocatedSpaceLabel.setText(String.format(format, DriveManager.getInstance().getDriveUnallocatedSpace(currentDrive)));
    }

    private void refreshComboBox() {
        int index = diskComboBox.getSelectionModel().getSelectedIndex();
        diskComboBox.getItems().clear();
        diskComboBox.getItems().addAll(DriveManager.getInstance().getDrives());
        diskComboBox.getSelectionModel().select(index);
    }

    // локальная реализация
    private void runFile(Path path) {
        try {
            LocalDriveManager.runFile(path);
        } catch (IOException e) {
            AlertUtility.showErrorAlert(BundleHolder.getBundle().getString("message.alert.failed_to_run_file"), ButtonType.OK);
        }
    }
}
