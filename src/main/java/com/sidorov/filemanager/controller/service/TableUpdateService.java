package com.sidorov.filemanager.controller.service;

import com.sidorov.filemanager.model.datagetter.DriveDataGettable;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.DriveSizeInfo;
import com.sidorov.filemanager.model.entity.FileEntity;
import com.sidorov.filemanager.model.entity.TableData;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

public class TableUpdateService extends Service<TableData> {

    private DriveEntity drive;

    public void setDrive(final DriveEntity drive) {
        this.drive = drive;
    }

    @Override
    protected Task<TableData> createTask() {
        return new Task<TableData>() {
            @Override
            protected TableData call() throws Exception {
                List<FileEntity> files = new ArrayList<FileEntity>();
                String dirPath = drive.getCurrentPath();
                DriveDataGettable dataGetter = drive.getDataGetter();
                dataGetter.setPathToIterableDirectory(dirPath);
                DriveSizeInfo sizeInfo = dataGetter.getDriveSizeInfo(drive.getName());
                final long numberFiles = dataGetter.getNumberFilesInDirectory(dirPath);
                while(dataGetter.hasNextFile()) {
                    files.add(dataGetter.getNextFile());
                    updateProgress(files.size() == 0 ? 1 : files.size() - 1, numberFiles);
                }
                updateProgress(files.size(), numberFiles);
                dataGetter = null;
                return new TableData(files, sizeInfo);
            }
        };
    }
}
