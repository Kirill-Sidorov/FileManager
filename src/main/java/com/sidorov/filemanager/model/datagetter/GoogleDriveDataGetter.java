package com.sidorov.filemanager.model.datagetter;

import com.google.api.services.drive.model.File;
import com.sidorov.filemanager.cloud.googledrive.GoogleDriveHolder;
import com.sidorov.filemanager.cloud.googledrive.GoogleDriveManager;
import com.sidorov.filemanager.model.entity.DriveSizeInfo;
import com.sidorov.filemanager.model.entity.FileEntity;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class GoogleDriveDataGetter implements DriveDataGettable {

    private List<File> dirFiles;
    private Iterator<File> fileIterator;

    @Override
    public DriveSizeInfo getDriveSizeInfo(String id) {
        return GoogleDriveManager.getDriveSizeInfo();
    }

    @Override
    public long getNumberFilesInDirectory(String id) {
        return dirFiles == null ? 0L : dirFiles.size();
    }

    @Override
    public void setPathToIterableDirectory(String id) {
        resetIterator();
        try {
            dirFiles = GoogleDriveManager.getListDirectoryFiles(id);
            fileIterator = dirFiles.iterator();
        } catch (IOException e) {
            dirFiles = null;
            fileIterator = null;
        }
    }

    @Override
    public boolean hasNextFile() { return fileIterator == null ? false : fileIterator.hasNext(); }

    @Override
    public FileEntity getNextFile() { return GoogleDriveManager.getFileEntity(fileIterator.next()); }

    @Override
    public void resetIterator() {
        dirFiles = null;
        fileIterator = null;
    }
}
