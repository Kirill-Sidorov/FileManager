package com.sidorov.filemanager.model.datamanager;

import java.io.IOException;

public interface DriveDataManageable {
    boolean isFileExist(final String id);
    void executeFile(final String id) throws IOException;
    String getParentDirectory(final String id);
}
