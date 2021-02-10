package com.sidorov.filemanager.model.driveadapter;

import com.google.api.services.drive.model.File;
import com.sidorov.filemanager.cloud.googledrive.GoogleDriveManager;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.DriveSizeInfo;
import com.sidorov.filemanager.model.entity.FileEntity;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class GoogleAdapterDriveManager implements AdapterDriveManageable {

    private List<File> dirFiles;
    private Iterator<File> fileIterator;

    @Override
    public DriveSizeInfo getDriveSizeInfo(final DriveEntity drive) {
        return GoogleDriveManager.getDriveSizeInfo();
    }

    @Override
    public long getNumberFilesInDirectory(String path) {
        return dirFiles == null ? 0L : dirFiles.size();
    }

    @Override
    public FileEntity getFileEntity(String path) {
        return null;
    }

    @Override
    public void setPathToIterableDirectory(String path) {
        resetIterator();
        try {
            dirFiles = GoogleDriveManager.getListDirectoryFiles(path);
            fileIterator = dirFiles.iterator();
        } catch (IOException e) {
            dirFiles = null;
            fileIterator = null;
        }
    }

    @Override
    public boolean hasNextFile() {
        return fileIterator == null ? false : fileIterator.hasNext();
    }

    @Override
    public FileEntity getNextFile() {
        return GoogleDriveManager.getFileEntity(fileIterator.next());
    }

    @Override
    public void resetIterator() {
        dirFiles = null;
        fileIterator = null;
    }
}
