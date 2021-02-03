package com.sidorov.filemanager.model;

import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;

public class GoogleProxyDriveManager implements ProxyDriveManageable {

    @Override
    public long getDriveTotalSpace(DriveEntity drive) {
        return 0;
    }

    @Override
    public long getDriveUnallocatedSpace(DriveEntity drive) {
        return 0;
    }

    @Override
    public long getNumberFilesInDirectory(String path) { return 0; }

    @Override
    public FileEntity getFileEntity(String path) {
        return null;
    }

    @Override
    public void setPathToIterableDirectory(String path) { }

    @Override
    public boolean hasNextFile() { return false; }

    @Override
    public FileEntity getNextFile() {
        return null;
    }

    @Override
    public void resetIterator() { }
}
