package com.sidorov.filemanager.controller.utility;

import com.sidorov.filemanager.utility.FileManager;
import com.sidorov.filemanager.model.entity.Status;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;
import com.sidorov.filemanager.model.entity.StatusEntity;
import javafx.scene.control.ButtonType;

public class FileManagerControllerUtility {

    private FileManagerControllerUtility() {}

    public static boolean performAction(final FileEntity file, DriveEntity drive) {
        boolean isNeedUpdateTable = false;
        StatusEntity status;
        if (file.isDirectory()) {
            if (FileManager.isDirectoryExist(file, drive)) {
                drive.setCurrentPath(file.getId());
                status = new StatusEntity(Status.OK);
                isNeedUpdateTable = true;
            } else {
                status = new StatusEntity(Status.FILE_NOT_FOUND_ERROR);
            }
        } else if (drive.isCloudDrive()) {
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
            drive.setCurrentPath(status.getFileId());
            return true;
        }
        return false;
    }



}
