package com.sidorov.filemanager.controller;

import com.sidorov.filemanager.model.ProxyDriveManageable;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;

import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

public class TableUpdateTask extends Task<List<FileEntity>> {

    private DriveEntity drive;

    public TableUpdateTask(DriveEntity drive) {
        this.drive = drive;
    }

    @Override
    protected List<FileEntity> call() throws Exception {
        List<FileEntity> files = new ArrayList<FileEntity>();
        String dirPath = drive.getCurrentPath();
        ProxyDriveManageable driveManager = drive.getDisk().getDriveManager();
        driveManager.setPathToIterableDirectory(dirPath);
        final long numberFiles = driveManager.getNumberFilesInDirectory(dirPath);
        while(driveManager.hasNextFile()) {
            files.add(driveManager.getNextFile());
            updateProgress(files.size() == 0 ? 1 : files.size() - 1, numberFiles);
        }
        updateProgress(files.size(), numberFiles);
        driveManager = null;
        return files;
    }
}
