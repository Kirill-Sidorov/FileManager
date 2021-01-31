package com.sidorov.filemanager.controller;

import com.sidorov.filemanager.model.FileEntity;
import com.sidorov.filemanager.utility.BundleHolder;
import com.sidorov.filemanager.utility.FileManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
    private ComboBox<String> diskComboBox;

    @FXML
    private TextField pathTextField;

    @FXML
    private ProgressBar progressBarLoadingTable;

    @FXML
    private TableView<FileEntity> fileView;

    private Path currentPath;
    private String currentRootDirectoryString;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable(resources);
        currentPath = Paths.get("/");
        updateTable();
        refreshComboBox();
        diskComboBox.getSelectionModel().select(0);
        currentRootDirectoryString = diskComboBox.getSelectionModel().getSelectedItem();
        updateFileSystemInfoLabels(currentPath);
    }

    public void clickRefreshButton(ActionEvent actionEvent) {
        updateTable();
    }

    public void selectDiskComboBox(ActionEvent actionEvent) {
        String item = diskComboBox.getSelectionModel().getSelectedItem();
        if (item != null && !item.equals(currentRootDirectoryString)) {
            currentRootDirectoryString = item;
            updateTable(Paths.get(item));
            updateFileSystemInfoLabels(Paths.get(item));
        }
    }

    public void clickDiskComboBox(MouseEvent mouseEvent) {
        refreshComboBox();
    }

    public void goPreviousFilesButton(ActionEvent actionEvent) {
        if (currentPath.getParent() != null) {
            updateTable(currentPath.getParent());
        }
    }

    public void clickOnItemTableView(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            FileEntity file = fileView.getSelectionModel().getSelectedItem();
            if (file == null) {
                return;
            }
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
        }
    }

    public void updateTable() {
        pathTextField.setText(currentPath.normalize().toAbsolutePath().toString());
        fileView.getItems().clear();
        TableUpdateTask task = new TableUpdateTask(currentPath);
        progressBarLoadingTable.visibleProperty().bind(task.runningProperty());
        progressBarLoadingTable.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(event -> Platform.runLater(() -> fileView.getItems().addAll(task.getValue())));
        new Thread(task).start();
    }

    public List<File> getSelectedFiles() {
        return fileView.getSelectionModel().getSelectedItems().stream()
                .map(item -> currentPath.resolve(item.getName()).toFile())
                .collect(Collectors.toList());
    }

    private void updateTable(Path path) {
        currentPath = path;
        updateTable();
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

    private void updateFileSystemInfoLabels(Path path) {
        long totalSpace = 1L;
        long unallocatedSpace = 1L;
        String format = BundleHolder.getBundle().getString("string.format.file_size");
        try {
            totalSpace = FileManager.getFileSystemTotalSpace(path);
            unallocatedSpace = FileManager.getFileSystemUnallocatedSpace(path);
        } catch (IOException e) {
            fileSystemTotalSpaceLabel.setText(null);
            fileSystemUnallocatedSpaceLabel.setText(null);
            return;
        }
        fileSystemTotalSpaceLabel.setText(String.format(format, totalSpace));
        fileSystemUnallocatedSpaceLabel.setText(String.format(format, unallocatedSpace));
    }

    private void refreshComboBox() {
        int index = diskComboBox.getSelectionModel().getSelectedIndex();
        diskComboBox.getItems().clear();
        diskComboBox.getItems().addAll(FileManager.getFileSystemRootDirectories());
        diskComboBox.getSelectionModel().select(index);
    }

    private void runFile(Path path) {
        try {
            FileManager.runFile(path);
        } catch (IOException e) {
            AlertUtility.showErrorAlert(BundleHolder.getBundle().getString("message.alert.failed_to_run_file"), ButtonType.OK);
        }
    }
}
