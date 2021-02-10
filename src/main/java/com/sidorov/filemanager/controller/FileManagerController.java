package com.sidorov.filemanager.controller;

import com.sidorov.filemanager.cloud.googledrive.GoogleDriveHolder;
import com.sidorov.filemanager.controller.task.GoogleDriveAdderTask;
import com.sidorov.filemanager.model.DriveManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

public class FileManagerController implements Initializable {

    @FXML
    private FileTableController rightFileTableController;
    @FXML
    private FileTableController leftFileTableController;

    @FXML
    private MenuItem addGoogleDriveMenuItem;
    @FXML
    private MenuItem removeGoogleDriveMenuItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        removeGoogleDriveMenuItem.setDisable(true);
        if (GoogleDriveHolder.isConnectedDrive()) {
            addGoogleDriveMenuItem.setDisable(true);
            removeGoogleDriveMenuItem.setDisable(false);
        }
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

    public void addGoogleDriveMenuItem(ActionEvent actionEvent) {
        addGoogleDriveMenuItem.setDisable(true);
        removeGoogleDriveMenuItem.setDisable(false);
        GoogleDriveAdderTask task = new GoogleDriveAdderTask();
        //task.setOnSucceeded(event -> { DriveManager.getInstance().addGoogleDrive(); }); вызвать Alert, что диск подлючен
        //task.setOnFailed(); alert что диск не удалось подключить
        new Thread(task).start();
    }

    public void removeGoogleDriveMenuItem(ActionEvent actionEvent) {
        removeGoogleDriveMenuItem.setDisable(true);
        addGoogleDriveMenuItem.setDisable(false);
        DriveManager.getInstance().removeGoogleDrive();
    }
}
