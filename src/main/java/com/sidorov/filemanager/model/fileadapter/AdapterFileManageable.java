package com.sidorov.filemanager.model.fileadapter;

import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;

import java.io.IOException;

public interface AdapterFileManageable {
    boolean isFileExist(final FileEntity file);
    String getDirectoryPath(final FileEntity file);
    void executeFile(final FileEntity file) throws IOException;
    String goPreviousDirectory(final DriveEntity drive);
}
