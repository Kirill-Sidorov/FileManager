package com.sidorov.filemanager.controller;

import com.sidorov.filemanager.controller.service.ItemClickService;
import com.sidorov.filemanager.controller.service.PreviousDirectoryClickService;
import com.sidorov.filemanager.controller.service.TableUpdateService;
import com.sidorov.filemanager.controller.dialog.AlertDialogUtility;
import com.sidorov.filemanager.controller.utility.DownloadUtility;
import com.sidorov.filemanager.model.MappedDriveManager;
import com.sidorov.filemanager.model.entity.*;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.result.DataResult;
import com.sidorov.filemanager.model.result.Error;
import com.sidorov.filemanager.model.result.PathResult;
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
import java.util.Arrays;
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

    @FXML
    private Button previousDirectoryButton;

    private TableUpdateService tableUpdateService;
    private ItemClickService itemClickService;
    private PreviousDirectoryClickService previousDirectoryClickService;
    private DriveEntity currentDrive;
    private String rootDirectory;
    private String format;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        format = resources.getString("string.format.file_size");
        previousDirectoryButton.setDisable(true);
        initializeServices();
        initializeTable(resources);
        initializeComboBox();
        updateTable();
    }

    public void clickRefreshButton(ActionEvent actionEvent) { updateTable(); }

    public void selectDiskComboBox(ActionEvent actionEvent) {
        String drive = diskComboBox.getSelectionModel().getSelectedItem();
        if (drive != null && !drive.equals(currentDrive.getName())) {
            currentDrive = MappedDriveManager.getInstance().getDriveByName(drive);
            rootDirectory = currentDrive.getCurrentPath();
            updateTable();
        }
    }

    public void clickDiskComboBox(MouseEvent mouseEvent) { refreshComboBox(); }

    public void clickPreviousDirectoryButton(ActionEvent actionEvent) {
        if (!previousDirectoryClickService.isRunning()) {
            previousDirectoryClickService.reset();
            previousDirectoryClickService.setDrive(currentDrive);
            previousDirectoryClickService.start();
        }
    }

    public void clickOnItemTableView(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            FileEntity fileEntity = fileTableView.getSelectionModel().getSelectedItem();
            if (fileEntity != null && currentDrive != null && !itemClickService.isRunning()) {
                itemClickService.reset();
                itemClickService.setData(fileEntity, currentDrive);
                itemClickService.start();
            }
        }
    }

    public void updateTable() {
        pathTextField.setText(currentDrive.getHumanReadablePath());
        fileTableView.getItems().clear();
        if (!tableUpdateService.isRunning()) {
            previousDirectoryButton.setDisable(true);
            tableUpdateService.reset();
            tableUpdateService.setDrive(currentDrive);
            tableUpdateService.start();
        }
    }

    public List<File> getSelectedFiles() {
        // локальная реализация
        return fileTableView.getSelectionModel().getSelectedItems().stream()
                .map(item -> Paths.get(currentDrive.getCurrentPath()).resolve(item.getName()).toFile())
                .collect(Collectors.toList());
    }

    private void initializeServices() {
        tableUpdateService = new TableUpdateService();
        tableLoadProgressBar.progressProperty().bind(tableUpdateService.progressProperty());
        // без runLater ?
        tableUpdateService.setOnSucceeded(event -> Platform.runLater(() -> processDataResult(tableUpdateService.getValue())));

        itemClickService = new ItemClickService();
        itemClickService.setOnSucceeded(event -> processPathResult((PathResult) itemClickService.getValue()));

        previousDirectoryClickService = new PreviousDirectoryClickService();
        previousDirectoryClickService.setOnSucceeded(event -> processPathResult(previousDirectoryClickService.getValue()));
    }

    private void initializeComboBox() {
        diskComboBox.getItems().addAll(MappedDriveManager.getInstance().getDrives());
        diskComboBox.getSelectionModel().select(0);
        currentDrive = MappedDriveManager.getInstance().getDriveByName(diskComboBox.getSelectionModel().getSelectedItem());
        rootDirectory = currentDrive.getCurrentPath();
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
        diskComboBox.getItems().addAll(MappedDriveManager.getInstance().getDrives());
        diskComboBox.getSelectionModel().select(index);
    }

    private void updateTable(String pathId, String humanReadablePath) {
        currentDrive.setPaths(pathId, humanReadablePath);
        updateTable();
    }

    private void processDataResult(DataResult dataResult) {
        fileTableView.getItems().addAll(dataResult.getFiles());
        driveTotalSpaceLabel.setText(String.format(format, dataResult.getTotalSpace()));
        driveUnallocatedSpaceLabel.setText(String.format(format, dataResult.getUnallocatedSpace()));
        if (dataResult.getError() != Error.NO) { AlertDialogUtility.showErrorAlert(dataResult.getError().getMessage()); }
        if (!rootDirectory.equals(currentDrive.getCurrentPath())) { previousDirectoryButton.setDisable(false);}
    }

    private void processPathResult(PathResult pathResult) {
        switch (pathResult.getStatus()) {
            case NEED_UPDATE_TABLE -> updateTable(pathResult.getPathId(), pathResult.getPathHumanReadable());
            case NEED_DOWNLOAD_FILE -> {
                FileEntity fileEntity = fileTableView.getSelectionModel().getSelectedItem();
                if (fileEntity != null && currentDrive != null) {
                    DownloadUtility.downloadFile(Arrays.asList(fileEntity), currentDrive);
                }
            }
            case ERROR -> AlertDialogUtility.showErrorAlert(pathResult.getError().getMessage());
        }
    }
}
