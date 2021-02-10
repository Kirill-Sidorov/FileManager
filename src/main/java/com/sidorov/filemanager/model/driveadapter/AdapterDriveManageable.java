package com.sidorov.filemanager.model.driveadapter;

import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.DriveSizeInfo;
import com.sidorov.filemanager.model.entity.FileEntity;

public interface AdapterDriveManageable {

    DriveSizeInfo getDriveSizeInfo(final DriveEntity drive);
    long getNumberFilesInDirectory(String path);
    FileEntity getFileEntity(String path); // параметр ?
    void setPathToIterableDirectory(String path);
    boolean hasNextFile();
    FileEntity getNextFile();
    void resetIterator();
}
