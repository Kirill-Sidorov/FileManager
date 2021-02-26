package com.sidorov.filemanager.controller.service;

import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;
import com.sidorov.filemanager.model.result.Result;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ItemClickService extends Service<Result> {

    private FileEntity file;
    private DriveEntity drive;

    public void setData(final FileEntity file, final DriveEntity drive) {
        this.file = file;
        this.drive = drive;
    }

    @Override
    protected Task<Result> createTask() {
        return new Task<Result>() {
            @Override
            protected Result call() throws Exception {
                if (file.isDirectory()) {
                    return drive.getDataManager().getNextDirectory(file.getId(), drive.getHumanReadablePath());
                } else {
                    return drive.getDataManager().executeFile(file.getId());
                }
            }
        };
    }
}
