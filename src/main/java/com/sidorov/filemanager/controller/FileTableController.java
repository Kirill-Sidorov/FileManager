package com.sidorov.filemanager.controller;

import com.sidorov.filemanager.model.FileInformation;
import com.sidorov.filemanager.model.FileType;
import com.sidorov.filemanager.utility.BundleHolder;
import com.sidorov.filemanager.utility.FileManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FileTableController implements Initializable {

    @FXML
    private TextField pathTextField;

    @FXML
    private ComboBox<String> diskComboBox;

    @FXML
    private TableView<FileInformation> fileTable;

    private Path currentPath = Paths.get("/");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable(resources);
        initializeComboBox();
        updateTable(currentPath);
    }

    private void initializeTable(ResourceBundle resources) {
        TableColumn<FileInformation, String> fileNameColumn = new TableColumn<FileInformation, String>(resources.getString("ui.tableview.files.column.name"));
        fileNameColumn.setCellValueFactory(file -> new SimpleObjectProperty<String>(file.getValue().getName()));

        TableColumn<FileInformation, Long> fileSizeColumn = new TableColumn<FileInformation, Long>(resources.getString("ui.tableview.files.column.size"));
        fileSizeColumn.setCellValueFactory(file -> new SimpleObjectProperty<Long>(file.getValue().getSize()));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        TableColumn<FileInformation, String> fileDateColumn = new TableColumn<FileInformation, String>(resources.getString("ui.tableview.files.column.date"));
        fileDateColumn.setCellValueFactory(file -> new SimpleObjectProperty<String>(file.getValue().getDate().format(dtf)));

        TableColumn<FileInformation, String> fileTypeColumn = new TableColumn<FileInformation, String>(resources.getString("ui.tableview.files.column.type"));
        fileTypeColumn.setCellValueFactory(file -> new SimpleObjectProperty<String>(file.getValue().getFileTypeString()));

        fileTable.getColumns().addAll(fileNameColumn, fileSizeColumn, fileDateColumn, fileTypeColumn);
        fileTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void initializeComboBox() {
        diskComboBox.getItems().clear();
        for (Path path : FileSystems.getDefault().getRootDirectories()) {
            diskComboBox.getItems().add(path.toString());
        }
        diskComboBox.getSelectionModel().select(0);
    }

    private void updateTable(Path path) {
        currentPath = path;
        try {
            pathTextField.setText(currentPath.normalize().toAbsolutePath().toString());
            fileTable.getItems().clear();
            fileTable.getItems().addAll(Files.list(currentPath).map(FileInformation::new).collect(Collectors.toList()));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, BundleHolder.getBundle().getString("message.alert.failed_to_upload_files"), ButtonType.OK);
            alert.setHeaderText(BundleHolder.getBundle().getString("message.alert.error"));
            alert.setTitle(BundleHolder.getBundle().getString("message.alert.error"));
            alert.showAndWait();
        }
    }

    public void refreshFileManagerButton(ActionEvent actionEvent) {
        initializeComboBox();
    }

    public void selectDiskComboBox(ActionEvent actionEvent) {
        ComboBox<String> item = (ComboBox<String>) actionEvent.getSource();
        updateTable(Paths.get(item.getSelectionModel().getSelectedItem()));
    }

    public void goPreviousFilesButton(ActionEvent actionEvent) {
        if (currentPath.getParent() != null) {
            updateTable(currentPath.getParent());
        }
    }

    public void clickOnItemTableView(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            FileInformation fileInformation = fileTable.getSelectionModel().getSelectedItem();
            if (fileInformation.getType() == FileType.DIRECTORY) {
                updateTable(currentPath.resolve(fileInformation.getName()));
            } else {
                FileManager.runFile(currentPath.resolve(fileInformation.getName()));
            }
        }
    }
}
