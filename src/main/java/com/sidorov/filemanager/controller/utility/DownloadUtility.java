package com.sidorov.filemanager.controller.utility;

import com.sidorov.filemanager.cloud.googledrive.GoogleDriveManager;
import com.sidorov.filemanager.model.entity.Error;
import com.sidorov.filemanager.model.entity.ExecutionResult;
import com.sidorov.filemanager.model.entity.Status;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;


public final class DownloadUtility {

    private DownloadUtility() {}

    public Status downloadGoogleDriveFile(String id) {
        /*
        ExecutionResult executionResult;
        try {
            GoogleDriveManager.uploadFile(fileId);
            executionResult = new ExecutionResult();
        } catch (IOException e) {
            executionResult = new ExecutionResult(Error.FILE_NOT_UPLOAD_ERROR);
        }
*/

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Download", ButtonType.CANCEL);
            alert.getDialogPane().setContent(new ProgressIndicator());
            alert.showAndWait();
        });

        //Task<StatusEntity> downloadTask = new DownloadFileTask(id);
        //System.out.println("download");
        //
        //progressBar.progressProperty().bind(downloadTask.progressProperty());
        //alert.getDialogPane().setContent(progressBar);
/*
        downloadTask.setOnSucceeded(event -> {
            StatusEntity status = downloadTask.getValue();
            if (status.getStatus() != Status.OK) {
                alert.setContentText(status.getStatus().getMessage());
            } else {
                alert.close();
            }
        });

 */


        //new Thread(downloadTask).start();
        return Status.OK;
    }
}
