package com.sidorov.filemanager.controller.service;

import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.ExecutionResult;
import com.sidorov.filemanager.utility.FileManager;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class PreviousDirectoryClickService extends Service<ExecutionResult> {

    private DriveEntity drive;

    public void setDrive(final DriveEntity drive) { this.drive = drive; }

    @Override
    protected Task<ExecutionResult> createTask() {
        return new Task<ExecutionResult>() {
            @Override
            protected ExecutionResult call() throws Exception {
                return FileManager.getPreviousDirectory(drive);
            }
        };
    }
}
