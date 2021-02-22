package com.sidorov.filemanager.controller;

import com.sidorov.filemanager.cloud.CloudConnector;
import com.sidorov.filemanager.cloud.CloudDriveType;
import com.sidorov.filemanager.controller.utility.AlertUtility;
import com.sidorov.filemanager.controller.utility.InformationUtility;
import com.sidorov.filemanager.model.MappedDriveManager;
import com.sidorov.filemanager.model.entity.Error;
import com.sidorov.filemanager.utility.BundleHolder;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class FileManagerController implements Initializable {

    @FXML
    private FileTableController rightFileTableController;
    @FXML
    private FileTableController leftFileTableController;

    @FXML
    private Menu googleDriveMenu;
    @FXML
    private MenuItem addGoogleDriveMenuItem;
    @FXML
    private MenuItem removeGoogleDriveMenuItem;

    @FXML
    private Menu dropboxDriveMenu;
    @FXML
    private MenuItem addDropboxMenuItem;
    @FXML
    private MenuItem removeDropboxMenuItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CloudConnector connector = new CloudConnector();
        connector.setOnSucceeded(event -> {
            initializeCloudDrives(connector.getValue());
        });
        Thread thread = new Thread(connector);
        thread.setDaemon(true);
        thread.start();
    }

    private void initializeCloudDrives(Map<CloudDriveType, Error> drivesErrors) {
        StringBuilder errors = new StringBuilder();
        for (CloudDriveType cloudDrive : drivesErrors.keySet()) {
            if (drivesErrors.get(cloudDrive) == Error.NO) {
                setCloudDriveMenuItems(cloudDrive, true);
                cloudDrive.addToMappedDrives();
            } else {
                setCloudDriveMenuItems(cloudDrive, false);
                errors.append(String.format(BundleHolder.getBundle().getString("string.format.error.drives_connection"),
                        cloudDrive.getName(),
                        drivesErrors.get(cloudDrive).getMessage()));
            }
        }
        if (errors.length() != 0) {
            AlertUtility.showErrorWithTextArea(errors.toString());
        }
        googleDriveMenu.setDisable(false);
        dropboxDriveMenu.setDisable(false);
    }

    private void setCloudDriveMenuItems(CloudDriveType cloudDrive, boolean isDriveConnect) {
        switch (cloudDrive) {
            case GOOGLE:
                addGoogleDriveMenuItem.setDisable(isDriveConnect);
                removeGoogleDriveMenuItem.setDisable(!isDriveConnect);
                break;
            case DROPBOX:
                addDropboxMenuItem.setDisable(isDriveConnect);
                removeDropboxMenuItem.setDisable(isDriveConnect);
                break;
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
        Task<Error> task = new Task<Error>() {
            @Override
            protected Error call() throws Exception {
                return CloudDriveType.GOOGLE.createDrive();
            }
        };
        task.setOnSucceeded(event -> {
            Error error = task.getValue();
            if (error == Error.NO) {
                setCloudDriveMenuItems(CloudDriveType.GOOGLE, true);
                MappedDriveManager.getInstance().addGoogleDrive();
                InformationUtility.showInformation(String
                        .format(BundleHolder.getBundle().getString("string.format.message.cloud_drive_connected"), CloudDriveType.GOOGLE.getName()));
            } else {
                setCloudDriveMenuItems(CloudDriveType.GOOGLE, false);
                AlertUtility.showErrorAlert(error.getMessage());
            }

        });
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void removeGoogleDriveMenuItem(ActionEvent actionEvent) {
        setCloudDriveMenuItems(CloudDriveType.GOOGLE,false);
        MappedDriveManager.getInstance().removeGoogleDrive();
    }

    public void addDropboxMenuItem(ActionEvent actionEvent) {
    }

    public void removeDropBoxMenuItem(ActionEvent actionEvent) {
    }
}
