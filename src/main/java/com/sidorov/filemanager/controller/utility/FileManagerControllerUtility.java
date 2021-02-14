package com.sidorov.filemanager.controller.utility;

import com.sidorov.filemanager.utility.FileManager;
import com.sidorov.filemanager.model.entity.Status;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;
import com.sidorov.filemanager.model.entity.StatusEntity;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;

public class FileManagerControllerUtility {

    private FileManagerControllerUtility() {}

    public static boolean performAction(final FileEntity file, DriveEntity drive) {
        boolean isNeedUpdateTable = false;
        StatusEntity status;
        if (file.isDirectory()) {
            status = FileManager.getNextDirectory(file, drive);
            if (status.getStatus() == Status.OK) {
                isNeedUpdateTable = true;
                drive.setPaths(status.getPathId(), status.getPathHumanReadable());
            }
        } else if (drive.isCloudDrive()) {
            status = new StatusEntity(Status.OK);
        } else {
            status = FileManager.executeFile(file, drive);
        }

        if (status.getStatus() != Status.OK) {
            Platform.runLater(() -> AlertUtility.showErrorAlert(status.getStatus().getMessage(), ButtonType.OK));
        }
        return isNeedUpdateTable;
    }

    public static boolean goPreviousDirectory(DriveEntity drive) {
        boolean isNeedUpdateTable = false;
        StatusEntity status = FileManager.getPreviousDirectory(drive);
        if (status.getStatus() == Status.OK) {
            isNeedUpdateTable = true;
            drive.setPaths(status.getPathId(), status.getPathHumanReadable());
        } else {
            Platform.runLater(() -> AlertUtility.showErrorAlert(status.getStatus().getMessage(), ButtonType.OK));
        }
        return isNeedUpdateTable;
    }
}
