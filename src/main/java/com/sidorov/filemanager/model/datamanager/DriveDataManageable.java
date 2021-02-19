package com.sidorov.filemanager.model.datamanager;

import com.sidorov.filemanager.model.entity.PathEntity;
import com.sidorov.filemanager.model.entity.Status;

public interface DriveDataManageable {
    boolean isFileExist(final String id);
    Status executeFile(final String id);
    PathEntity getNextDirectory(final String id, final String readablePath);
    PathEntity getPreviousDirectory(final String id, final String readablePath);
}
