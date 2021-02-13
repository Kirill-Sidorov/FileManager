package com.sidorov.filemanager.model.datamanager;

import com.sidorov.filemanager.utility.LocalDriveManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalDriveDataManager implements DriveDataManageable {
    @Override
    public boolean isFileExist(String id) { return LocalDriveManager.isFileExist(Paths.get(id)); }

    @Override
    public void executeFile(String id) throws IOException { LocalDriveManager.executeFile(Paths.get(id)); }

    @Override
    public String getParentDirectory(String id) {
        Path path = LocalDriveManager.getParentDirectory(Paths.get(id));
        return path != null ? path.toString() : "";
    }
}
