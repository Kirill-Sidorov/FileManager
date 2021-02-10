package com.sidorov.filemanager.model.entity;

public class FileExecutionResult {
    private boolean isFileExecute;
    private boolean isError;
    private String newDrivePath;

    public FileExecutionResult(boolean isFileExecute, boolean isError) {
        this.isFileExecute = isFileExecute;
        this.isError = isError;
    }

    public FileExecutionResult(boolean isFileExecute, boolean isError, String newDrivePath) {
        this(isFileExecute, isError);
        this.newDrivePath = newDrivePath;
    }

    public boolean isFileExecute() {
        return isFileExecute;
    }

    public boolean isError() {
        return isError;
    }

    public String getNewDrivePath() {
        return newDrivePath;
    }
}
