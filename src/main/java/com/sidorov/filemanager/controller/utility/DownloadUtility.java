package com.sidorov.filemanager.controller.utility;

import com.sidorov.filemanager.controller.task.DownloadTask;
import com.sidorov.filemanager.model.entity.*;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.List;

public final class DownloadUtility {

    private DownloadUtility() {}

    // download and run?
    public static void downloadFile(final List<FileEntity> files, final DriveEntity drive) {
        if (ConfirmationUtility.showDownloadConfirmation() == ButtonType.OK) {
            DownloadTask task = drive.getDownloadTask(files);
            Dialog dialog = DialogCreatorUtility.createDownloadDialog(task, drive.getName());

            task.setOnSucceeded(event -> dialog.close());

            Thread thread = new Thread(task);
            thread.setDaemon(true);

            dialog.setOnCloseRequest(event -> {
                task.cancel(true);
                thread.stop();
            });
            dialog.show();
            thread.start();
        }
    }
}
