package com.sidorov.filemanager.controller;

import com.sidorov.filemanager.model.FileManager;
import com.sidorov.filemanager.model.entity.Status;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;
import com.sidorov.filemanager.model.entity.StatusEntity;
import javafx.scene.control.ButtonType;

public class FileControllerUtility {

    private FileControllerUtility() {}

    public static boolean performAction(final FileEntity file, DriveEntity drive) {
        boolean isNeedUpdateTable = false;
        StatusEntity status;
        if (file.getSize() == -1L) {
            status = FileManager.getDirectoryPath(file, drive);
            if (status.getStatus() == Status.OK) {
                drive.setCurrentPath(status.getPath());
                isNeedUpdateTable = true;
            }
        } else if (drive.getDrive().isCloudDrive()) {
            status = new StatusEntity(Status.OK);
        } else {
            status = FileManager.executeFile(file, drive);
        }

        if (status.getStatus() != Status.OK) {
            AlertUtility.showErrorAlert(status.getStatus().getMessage(), ButtonType.OK);
        }
        return isNeedUpdateTable;
    }

    public static boolean goPreviousDirectory(DriveEntity drive) {
        StatusEntity status = FileManager.goPreviousDirectory(drive);
        if (status.getStatus() == Status.OK) {
            drive.setCurrentPath(status.getPath());
            return true;
        }
        return false;
    }
}
