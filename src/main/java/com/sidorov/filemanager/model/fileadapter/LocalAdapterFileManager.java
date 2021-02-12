package com.sidorov.filemanager.model.fileadapter;

import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalAdapterFileManager implements AdapterFileManageable {
    @Override
    public boolean isFileExist(FileEntity file) {
        return Paths.get(file.getId()).toFile().exists();
    }

    @Override
    public String getDirectoryPath(FileEntity file) {
        Path path = Paths.get(file.getId());
        if (path.toFile().exists()) {
            return path.toString();
        }
        return "";
    }
    // перенести в утилиту?
    @Override
    public void executeFile(FileEntity file) throws IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(Paths.get(file.getId()).toFile());
        }
    }

    @Override
    public String goPreviousDirectory(DriveEntity drive) {
        Path path = Paths.get(drive.getCurrentPath()).getParent();
        if (path != null) {
            return path.toString();
        }
        return "";
    }
}
