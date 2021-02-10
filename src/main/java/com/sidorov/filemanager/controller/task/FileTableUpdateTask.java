package com.sidorov.filemanager.controller.task;

import com.sidorov.filemanager.model.driveadapter.AdapterDriveManageable;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.DriveSizeInfo;
import com.sidorov.filemanager.model.entity.FileEntity;

import com.sidorov.filemanager.model.entity.TableData;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

public class FileTableUpdateTask extends Task<TableData> {

    private final DriveEntity drive;

    public FileTableUpdateTask(final DriveEntity drive) {
        this.drive = drive;
    }

    @Override
    protected TableData call() throws Exception {
        List<FileEntity> files = new ArrayList<FileEntity>();
        String dirPath = drive.getCurrentPath();
        AdapterDriveManageable driveManager = drive.getDrive().getDriveManager();
        driveManager.setPathToIterableDirectory(dirPath);
        DriveSizeInfo sizeInfo = driveManager.getDriveSizeInfo(drive);
        final long numberFiles = driveManager.getNumberFilesInDirectory(dirPath);
        while(driveManager.hasNextFile()) {
            files.add(driveManager.getNextFile());
            updateProgress(files.size() == 0 ? 1 : files.size() - 1, numberFiles);
        }
        updateProgress(files.size(), numberFiles);
        driveManager = null;
        return new TableData(files, sizeInfo);
    }
}
