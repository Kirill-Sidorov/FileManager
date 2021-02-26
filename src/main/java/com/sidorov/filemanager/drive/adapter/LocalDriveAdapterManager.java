package com.sidorov.filemanager.drive.adapter;

import com.sidorov.filemanager.controller.service.ProgressUpdater;
import com.sidorov.filemanager.drive.local.LocalDriveManager;
import com.sidorov.filemanager.model.result.Error;
import com.sidorov.filemanager.model.result.DataResult;
import com.sidorov.filemanager.model.result.PathResult;
import com.sidorov.filemanager.model.result.Result;

public class LocalDriveAdapterManager implements AdapterManageable {
    @Override
    public Result executeFile(String id) { return LocalDriveManager.executeFile(id); }

    @Override
    public PathResult getNextDirectory(String dirId, String readablePath) {
        return (LocalDriveManager.isFileExist(dirId)) ? new PathResult(dirId, dirId) : new PathResult(Error.FILE_NOT_FOUND_ERROR);
    }

    @Override
    public PathResult getPreviousDirectory(String dirId, String readablePath) { return LocalDriveManager.getParentDirectory(dirId); }

    @Override
    public DataResult getListFiles(String dirId, ProgressUpdater updater) { return LocalDriveManager.getListDirectoryFiles(dirId, updater); }
}
