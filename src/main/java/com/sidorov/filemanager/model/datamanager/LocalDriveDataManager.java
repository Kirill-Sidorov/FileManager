package com.sidorov.filemanager.model.datamanager;

import com.sidorov.filemanager.model.entity.PathEntity;
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
    public PathEntity getNextDirectory(String id, String readablePath) {
        return (LocalDriveManager.isFileExist(Paths.get(id))) ? new PathEntity(id, id) : null;
    }

    @Override
    public PathEntity getPreviousDirectory(String id, String readablePath) {
        Path pathId = Paths.get(id);
        PathEntity pathEntity = null;
        if (LocalDriveManager.isFileExist(pathId)) {
            Path path = LocalDriveManager.getParentDirectory(pathId);
            if (path != null) {
                String newId = path.toString();
                pathEntity = new PathEntity(newId, newId);
            } else {
                pathEntity = new PathEntity(id, readablePath);
            }
        }
        return pathEntity;
    }
}
