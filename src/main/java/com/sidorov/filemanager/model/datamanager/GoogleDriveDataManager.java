package com.sidorov.filemanager.model.datamanager;

import com.sidorov.filemanager.cloud.googledrive.GoogleDriveManager;

import java.io.IOException;

public class GoogleDriveDataManager implements DriveDataManageable {
    @Override
    public boolean isFileExist(String id) { return GoogleDriveManager.isFileExist(id); }

    @Override
    public void executeFile(String id) throws IOException { }

    @Override
    public String getParentDirectory(String id) { return GoogleDriveManager.getParentDirectory(id); }
}
