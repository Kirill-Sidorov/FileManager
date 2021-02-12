package com.sidorov.filemanager.model.fileadapter;

import com.sidorov.filemanager.cloud.googledrive.GoogleDriveHolder;
import com.sidorov.filemanager.cloud.googledrive.GoogleDriveManager;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;

import java.io.IOException;

public class GoogleAdapterFileManager implements AdapterFileManageable {
    @Override
    public boolean isFileExist(FileEntity file) {
        return GoogleDriveHolder.isConnectedDrive();
    }

    @Override
    public String getDirectoryPath(FileEntity file) {
        if (GoogleDriveHolder.isConnectedDrive()) {
            return file.getId();
        }
        return "";
    }

    @Override
    public void executeFile(FileEntity file) throws IOException {
    }

    @Override
    public String goPreviousDirectory(DriveEntity drive) {
        String path;
        try {
            path = GoogleDriveManager.getParentDirectory(drive.getCurrentPath());
        } catch (IOException e) {
            path = "";
        }
        return path;
    }
}
