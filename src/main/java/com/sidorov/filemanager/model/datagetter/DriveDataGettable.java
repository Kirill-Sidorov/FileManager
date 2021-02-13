package com.sidorov.filemanager.model.datagetter;

import com.sidorov.filemanager.model.entity.DriveSizeInfo;
import com.sidorov.filemanager.model.entity.FileEntity;

import java.io.IOException;

public interface DriveDataGettable {
    DriveSizeInfo getDriveSizeInfo(final String driveName);
    long getNumberFilesInDirectory(final String id);
    void setPathToIterableDirectory(final String id);
    boolean hasNextFile();
    FileEntity getNextFile();
    void resetIterator();
}
