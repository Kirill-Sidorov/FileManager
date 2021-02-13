package com.sidorov.filemanager.model.datamanager;

import com.sidorov.filemanager.cloud.googledrive.GoogleDriveHolder;
import com.sidorov.filemanager.cloud.googledrive.GoogleDriveManager;
import javafx.concurrent.Task;

import java.io.IOException;

public class GoogleDriveDataManager implements DriveDataManageable {
    // временно
    @Override
    public boolean isFileExist(String id) { return GoogleDriveHolder.isConnectedDrive(); }

    @Override
    public void executeFile(String id) throws IOException { }

    @Override
    public String getParentDirectory(String id) {
        return GoogleDriveManager.getParentDirectory(id);
    }
}
