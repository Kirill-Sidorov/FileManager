package com.sidorov.filemanager.controller.service;

import com.sidorov.filemanager.controller.utility.FileManagerControllerUtility;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ItemClickService extends Service<Boolean> {

    private FileEntity file;
    private DriveEntity drive;

    public void setDrive(final FileEntity file, final DriveEntity drive) {
        this.file = file;
        this.drive = drive;
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return FileManagerControllerUtility.performAction(file, drive);
            }
        };
    }
}
