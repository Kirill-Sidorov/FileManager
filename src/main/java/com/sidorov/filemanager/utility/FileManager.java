package com.sidorov.filemanager.utility;

import com.sidorov.filemanager.model.entity.*;
import com.sidorov.filemanager.model.entity.Error;

public final class FileManager {

    private FileManager() {}

    public static ExecutionResult executeFile(final FileEntity file, final DriveEntity drive) {
        ExecutionResult executionResult;
        if (drive.getDataManager().isFileExist(file.getId())) {
            Status status = drive.getDataManager().executeFile(file.getId());
            if (status == Status.ERROR) {
                executionResult = new ExecutionResult(Error.FILE_NOT_RUN_ERROR);
            } else if (status == Status.NEED_DOWNLOAD_FILE) {
                executionResult = new ExecutionResult(status);
            } else {
                executionResult = new ExecutionResult(Status.OK);
            }
        } else {
            executionResult = new ExecutionResult(Error.FILE_NOT_FOUND_ERROR);
        }
        return executionResult;
    }

    public static ExecutionResult getNextDirectory(final FileEntity file, final DriveEntity drive) {
        PathEntity pathEntity = drive.getDataManager().getNextDirectory(file.getId(), drive.getHumanReadablePath());
        return (pathEntity != null) ? new ExecutionResult(pathEntity) : new ExecutionResult(Error.FILE_NOT_FOUND_ERROR);
    }

    public static ExecutionResult getPreviousDirectory(final DriveEntity drive) {
        PathEntity pathEntity = drive.getDataManager().getPreviousDirectory(drive.getCurrentPath(), drive.getHumanReadablePath());
        return (pathEntity != null) ? new ExecutionResult(pathEntity) : new ExecutionResult(Error.FILE_NOT_FOUND_ERROR);
    }
}
