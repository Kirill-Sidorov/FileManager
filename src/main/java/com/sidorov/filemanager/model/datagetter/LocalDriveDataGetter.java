package com.sidorov.filemanager.model.datagetter;

import com.sidorov.filemanager.model.entity.DriveSizeInfo;
import com.sidorov.filemanager.model.entity.FileEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalDriveDataGetter implements DriveDataGettable {

    private int currentPosition;
    private File[] dirFiles;

    @Override
    public DriveSizeInfo getDriveSizeInfo(String driveName) { return com.sidorov.filemanager.utility.LocalDriveManager.getFileSystemSizeInfo(Paths.get(driveName)); }

    @Override
    public long getNumberFilesInDirectory(String id) {
        return dirFiles == null ? 0L : dirFiles.length;
    }

    @Override
    public void setPathToIterableDirectory(String id) {
        resetIterator();
        File dir = new File(id);
        dirFiles = dir.listFiles();
    }

    @Override
    public boolean hasNextFile() { return dirFiles == null ? false : currentPosition < dirFiles.length; }

    @Override
    public FileEntity getNextFile() { return com.sidorov.filemanager.utility.LocalDriveManager.getFileEntity(dirFiles[currentPosition++].toPath()); }

    @Override
    public void resetIterator() {
        currentPosition = 0;
        dirFiles = null;
    }
}
