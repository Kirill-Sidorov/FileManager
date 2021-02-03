package com.sidorov.filemanager.model;

import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;
import com.sidorov.filemanager.utility.LocalDriveManager;

import java.io.File;
import java.nio.file.Paths;

public class LocalProxyDriveManager implements ProxyDriveManageable {

    private int currentPosition;
    private File[] dirFiles;

    @Override
    public long getDriveTotalSpace(DriveEntity drive) {
        return LocalDriveManager.getFileSystemTotalSpace(Paths.get(drive.getName()));
    }

    @Override
    public long getDriveUnallocatedSpace(DriveEntity drive) {
        return LocalDriveManager.getFileSystemUnallocatedSpace(Paths.get(drive.getName()));
    }

    @Override
    public long getNumberFilesInDirectory(String path) {
        return LocalDriveManager.getNumberFilesInDirectory(Paths.get(path));
    }

    @Override
    public FileEntity getFileEntity(String path) {
        return LocalDriveManager.getFileEntity(Paths.get(path));
    }

    @Override
    public void setPathToIterableDirectory(String path) {
        resetIterator();
        File dir = new File(path);
        if (dir.isDirectory()) {
            dirFiles = dir.listFiles();
        }
    }

    @Override
    public boolean hasNextFile() {
        if (dirFiles != null) {
            return currentPosition < dirFiles.length;
        }
        return false;
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
