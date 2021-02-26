package com.sidorov.filemanager.controller.service;

import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.result.PathResult;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class PreviousDirectoryClickService extends Service<PathResult> {

    private DriveEntity drive;

    public void setDrive(final DriveEntity drive) { this.drive = drive; }

    @Override
    protected Task<PathResult> createTask() {
        return new Task<PathResult>() {
            @Override
            protected PathResult call() throws Exception {
                return drive.getDataManager().getPreviousDirectory(drive.getCurrentPath(), drive.getHumanReadablePath());
            }
        };
    }
}
