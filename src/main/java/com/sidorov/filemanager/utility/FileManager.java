package com.sidorov.filemanager.utility;

import com.sidorov.filemanager.model.entity.*;

import java.io.IOException;

public final class FileManager {

    private FileManager() {}

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

    public static StatusEntity getNextDirectory(final FileEntity file, final DriveEntity drive) {
        PathEntity pathEntity = drive.getDataManager().getNextDirectory(file.getId(), drive.getHumanReadablePath());
        return (pathEntity != null) ? new StatusEntity(pathEntity) : new StatusEntity(Status.FILE_NOT_FOUND_ERROR);
    }

    public static StatusEntity getPreviousDirectory(final DriveEntity drive) {
        PathEntity pathEntity = drive.getDataManager().getPreviousDirectory(drive.getCurrentPath(), drive.getHumanReadablePath());
        return (pathEntity != null) ? new StatusEntity(pathEntity) : new StatusEntity(Status.FILE_NOT_FOUND_ERROR);
    }
}
