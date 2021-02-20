package com.sidorov.filemanager.controller.utility;

import com.sidorov.filemanager.controller.task.DownloadTask;
import com.sidorov.filemanager.model.entity.*;
import com.sidorov.filemanager.model.entity.Error;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;

import java.util.List;


public final class DownloadUtility {

    private DownloadUtility() {}

    // download and run?
    public static void downloadFile(final List<FileEntity> files, final DriveEntity drive) {
        if (ConfirmationUtility.showDownloadConfirmation() == ButtonType.OK) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            ProgressBar progressBar = new ProgressBar();
            alert.getDialogPane().setExpandableContent(progressBar);

            DownloadTask task = drive.getDownloadTask(files);
            progressBar.progressProperty().bind(task.progressProperty());
            alert.contentTextProperty().bind(task.messageProperty());

            task.setOnSucceeded(event -> {
                Error error = task.getValue();
                if (error == Error.NO) {
                    alert.close();
                } else {
                    alert.setContentText(error.getMessage());
                }
            });

            alert.show();

            new Thread(task).start();
        }
    }
}
