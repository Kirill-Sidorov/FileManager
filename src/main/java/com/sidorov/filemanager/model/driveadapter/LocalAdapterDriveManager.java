package com.sidorov.filemanager.model.driveadapter;

import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.DriveSizeInfo;
import com.sidorov.filemanager.model.entity.FileEntity;
import com.sidorov.filemanager.local.LocalDriveManager;

import java.io.File;
import java.nio.file.Paths;

public class LocalAdapterDriveManager implements AdapterDriveManageable {

    private int currentPosition;
    private File[] dirFiles;

    @Override
    public DriveSizeInfo getDriveSizeInfo(final DriveEntity drive) {
        return LocalDriveManager.getFileSystemSizeInfo(Paths.get(drive.getName()));
    }

    @Override
    public long getNumberFilesInDirectory(String path) {
        return dirFiles == null ? 0L : dirFiles.length;
    }

    @Override
    public FileEntity getFileEntity(String path) {
        return LocalDriveManager.getFileEntity(Paths.get(path));
    }

    @Override
    public void setPathToIterableDirectory(String path) {
        resetIterator();
        File dir = new File(path);
        dirFiles = dir.listFiles();
    }

    @Override
    public boolean hasNextFile() {
        return dirFiles == null ? false : currentPosition < dirFiles.length;
    }

    @Override
    public FileEntity getNextFile() {
        return LocalDriveManager.getFileEntity(dirFiles[currentPosition++].toPath());
    }

    @Override
    public void resetIterator() {
        currentPosition = 0;
        dirFiles = null;
    }
}
