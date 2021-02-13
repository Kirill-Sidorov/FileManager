package com.sidorov.filemanager.model.datamanager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalDriveDataManager implements DriveDataManageable {
    @Override
    public boolean isFileExist(String id) { return com.sidorov.filemanager.utility.LocalDriveManager.isFileExist(Paths.get(id)); }

    @Override
    public void executeFile(String id) throws IOException { com.sidorov.filemanager.utility.LocalDriveManager.executeFile(Paths.get(id)); }

    @Override
    public String getParentDirectory(String id) {
        Path path = com.sidorov.filemanager.utility.LocalDriveManager.getParentDirectory(Paths.get(id));
        return path != null ? path.toString() : "";
    }
}
