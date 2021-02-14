package com.sidorov.filemanager.model.datamanager;
import com.sidorov.filemanager.model.entity.PathEntity;

import java.io.IOException;

public interface DriveDataManageable {
    boolean isFileExist(final String id);
    void executeFile(final String id) throws IOException;
    PathEntity getNextDirectory(final String id, final String readablePath);
    PathEntity getPreviousDirectory(final String id, final String readablePath);
}
