package com.sidorov.filemanager.model;

import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;
import com.sidorov.filemanager.model.entity.Status;
import com.sidorov.filemanager.model.entity.StatusEntity;
import com.sidorov.filemanager.model.fileadapter.AdapterFileManageable;

import java.io.IOException;

public final class FileManager {

    private FileManager() {}

    public static StatusEntity getDirectoryPath(final FileEntity file, final DriveEntity drive) {
        String path = drive.getDrive().getFileManager().getDirectoryPath(file);
        return (path.length() != 0) ? new StatusEntity(path) : new StatusEntity(Status.FILE_NOT_FOUND_ERROR);
    }

    public static StatusEntity executeFile(final FileEntity file, final DriveEntity drive) {
        StatusEntity status = new StatusEntity(Status.OK);
        AdapterFileManageable fileManager = drive.getDrive().getFileManager();
        if (fileManager.isFileExist(file)) {
            try {
                drive.getDrive().getFileManager().executeFile(file);
            } catch (IOException e) {
                status = new StatusEntity(Status.FILE_NOT_RUN_ERROR);
            }
        } else {
            status = new StatusEntity(Status.FILE_NOT_FOUND_ERROR);
        }
        return status;
    }

    public static StatusEntity goPreviousDirectory(final DriveEntity drive) {
        String path = drive.getDrive().getFileManager().goPreviousDirectory(drive);
        return (path.length() != 0) ? new StatusEntity(path) : new StatusEntity(Status.FILE_NOT_FOUND_ERROR);
    }
}
