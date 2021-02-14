package com.sidorov.filemanager.controller.service;

import com.sidorov.filemanager.controller.utility.FileManagerControllerUtility;
import com.sidorov.filemanager.model.entity.DriveEntity;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class PreviousDirectoryClickService extends Service<Boolean> {
    private DriveEntity drive;

    public void setDrive(final DriveEntity drive) {
        this.drive = drive;
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return FileManagerControllerUtility.goPreviousDirectory(drive);
            }
        };
    }
}
