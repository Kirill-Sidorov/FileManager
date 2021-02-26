package com.sidorov.filemanager.drive.adapter;

import com.sidorov.filemanager.controller.service.ProgressUpdater;
import com.sidorov.filemanager.drive.googledrive.GoogleDriveManager;
import com.sidorov.filemanager.model.result.DataResult;
import com.sidorov.filemanager.model.result.PathResult;
import com.sidorov.filemanager.model.result.Result;
import com.sidorov.filemanager.model.result.Status;

public class GoogleDriveAdapterManager implements AdapterManageable {
    @Override
    public Result executeFile(String id) { return new Result(Status.NEED_UPDATE_TABLE); }

    @Override
    public PathResult getNextDirectory(String dirId, String readablePath) { return GoogleDriveManager.getNextDirectory(dirId, readablePath); }

    @Override
    public PathResult getPreviousDirectory(String dirId, String readablePath) { return GoogleDriveManager.getParentDirectory(dirId, readablePath); }

    @Override
    public DataResult getListFiles(String dirId, ProgressUpdater updater) { return GoogleDriveManager.getListDirectoryFiles(dirId, updater); }
}
