package com.sidorov.filemanager.controller;

import com.sidorov.filemanager.model.FileInformation;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
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
    private TableView<FileInformation> fileTable;

    private Path currentPath = Paths.get("/");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable(resources);
        updateTable();
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
        fileTypeColumn.setCellValueFactory(file -> new SimpleObjectProperty<String>(file.getValue().getType().toString()));

        fileTable.getColumns().addAll(fileNameColumn, fileSizeColumn, fileDateColumn, fileTypeColumn);
    }

    private void updateTable() {
        try {
            pathTextField.setText(currentPath.normalize().toAbsolutePath().toString());
            fileTable.getItems().clear();
            fileTable.getItems().addAll(Files.list(currentPath).map(FileInformation::new).collect(Collectors.toList()));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "WARNING", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
