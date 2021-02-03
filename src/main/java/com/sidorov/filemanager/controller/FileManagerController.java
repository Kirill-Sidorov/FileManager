package com.sidorov.filemanager.controller;

import com.sidorov.filemanager.utility.BundleHolder;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

public class FileManagerController implements Initializable {

    @FXML
    private FileTableController rightFileTableController;

    @FXML
    private FileTableController leftFileTableController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void copyFileButton(ActionEvent actionEvent) {
        List<File> leftFiles = leftFileTableController.getSelectedFiles();
        List<File> rightFiles = rightFileTableController.getSelectedFiles();
        System.out.println(leftFiles.stream().count());
        System.out.println(rightFiles.stream().count());
        /*
        List<File> leftFiles = leftFileTableController.getSelectedFiles();
        List<File> rightFiles = rightFileTableController.getSelectedFiles();
        if (!leftFiles.isEmpty()) {
            copyFiles(leftFiles, rightFileTableController.getCurrentPath());
        } else if (!rightFiles.isEmpty()) {
            copyFiles(rightFiles, leftFileTableController.getCurrentPath());
        } else {
            AlertUtility.showWarningAlert(BundleHolder.getBundle().getString("message.alert.no_files_selected"), ButtonType.OK);
        }
        */
    }

    private void copyFiles(List<File> files, Path dstPath) {
        /*
        try {
            System.out.println(files.stream().count());
            FileUtils.copyToDirectory(files, dstPath.toFile());
        } catch (IOException e) {

        }
        */
    }

    public void moveFileButton(ActionEvent actionEvent) {
    }

    public void createDirectoryButton(ActionEvent actionEvent) {
    }

    public void renameButton(ActionEvent actionEvent) {
    }

    public void deleteButton(ActionEvent actionEvent) {
    }

    public void clickAddGoogleDrive(ActionEvent actionEvent) {

    }
}
