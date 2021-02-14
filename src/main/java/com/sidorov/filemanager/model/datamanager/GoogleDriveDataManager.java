package com.sidorov.filemanager.model.datamanager;

import com.sidorov.filemanager.cloud.googledrive.GoogleDriveManager;
import com.sidorov.filemanager.model.entity.PathEntity;

import java.io.IOException;

public class GoogleDriveDataManager implements DriveDataManageable {
    @Override
    public boolean isFileExist(String id) { return GoogleDriveManager.isFileExist(id); }

    @Override
    public void executeFile(String id) throws IOException { }

    @Override
    public PathEntity getNextDirectory(String id, String readablePath) {
        PathEntity pathEntity = null;
        String dirName = GoogleDriveManager.getNextDirectoryName(id);
        if (dirName != null) {
            String newReadablePath = readablePath + "\\" + dirName;
            pathEntity = new PathEntity(id, newReadablePath);
        }
        return pathEntity;
    }

    @Override
    public PathEntity getPreviousDirectory(String id, String readablePath) {
        PathEntity pathEntity = null;
        String dirId = GoogleDriveManager.getParentDirectoryId(id);
        if (dirId != null) {
            if (dirId.length() != 0) {
                int index = readablePath.lastIndexOf("\\");
                String newReadablePath = (index == -1) ? readablePath : readablePath.substring(0, index);
                pathEntity = new PathEntity(dirId, newReadablePath);
            } else {
                pathEntity = new PathEntity(id, readablePath);
            }
        }
        return pathEntity;
    }
}
