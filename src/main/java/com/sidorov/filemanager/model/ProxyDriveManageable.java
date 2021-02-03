package com.sidorov.filemanager.model;

import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;

public interface ProxyDriveManageable {

    long getDriveTotalSpace(DriveEntity drive);
    long getDriveUnallocatedSpace(DriveEntity drive);
    long getNumberFilesInDirectory(String path);
    FileEntity getFileEntity(String path); // параметр ?
    void setPathToIterableDirectory(String path);
    boolean hasNextFile();
    FileEntity getNextFile();
    void resetIterator();
}
