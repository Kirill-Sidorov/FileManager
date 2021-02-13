package com.sidorov.filemanager.utility;

import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;
import com.sidorov.filemanager.model.entity.Status;
import com.sidorov.filemanager.model.entity.StatusEntity;

import java.io.IOException;

public final class FileManager {

    private FileManager() {}

    public static boolean isDirectoryExist(final FileEntity file, final DriveEntity drive) {
        return drive.getDataManager().isFileExist(file.getId());
    }

    public static StatusEntity executeFile(final FileEntity file, final DriveEntity drive) {
        StatusEntity status = new StatusEntity(Status.OK);
        if (drive.getDataManager().isFileExist(file.getId())) {
            try {
                drive.getDataManager().executeFile(file.getId());
            } catch (IOException e) {
                status = new StatusEntity(Status.FILE_NOT_RUN_ERROR);
            }
        } else {
            status = new StatusEntity(Status.FILE_NOT_FOUND_ERROR);
        }
        return status;
    }

    public static StatusEntity goPreviousDirectory(final DriveEntity drive) {
        String path = drive.getDataManager().getParentDirectory(drive.getCurrentPath());
        return (path.length() != 0) ? new StatusEntity(path) : new StatusEntity(Status.FILE_NOT_FOUND_ERROR);
    }
}
