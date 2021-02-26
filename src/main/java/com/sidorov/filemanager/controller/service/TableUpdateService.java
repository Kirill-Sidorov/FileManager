package com.sidorov.filemanager.controller.service;

import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.result.DataResult;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class TableUpdateService extends Service<DataResult> {

    private DriveEntity drive;

    public void setDrive(final DriveEntity drive) {
        this.drive = drive;
    }

    @Override
    protected Task<DataResult> createTask() {
        return new Task<DataResult>() {
            @Override
            protected DataResult call() throws Exception {
                DataResult dataResult = drive.getDataManager().getListFiles(drive.getCurrentPath(), (this::updateProgress));
                updateProgress(0,1);
                return dataResult;
            }
        };
    }
}
