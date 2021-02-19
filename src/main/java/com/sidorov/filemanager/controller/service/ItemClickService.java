package com.sidorov.filemanager.controller.service;

import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.ExecutionResult;
import com.sidorov.filemanager.model.entity.FileEntity;
import com.sidorov.filemanager.utility.FileManager;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ItemClickService extends Service<ExecutionResult> {

    private FileEntity file;
    private DriveEntity drive;

    public void setData(final FileEntity file, final DriveEntity drive) {
        this.file = file;
        this.drive = drive;
    }

    @Override
    protected Task<ExecutionResult> createTask() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Download", ButtonType.CANCEL);
        return new Task<ExecutionResult>() {
            @Override
            protected ExecutionResult call() throws Exception {
                ExecutionResult result;
                if (file.isDirectory()) {
                    result = FileManager.getNextDirectory(file, drive);
                } else {
                    result = FileManager.executeFile(file, drive);
                }
                return result;
            }
        };
    }
}
