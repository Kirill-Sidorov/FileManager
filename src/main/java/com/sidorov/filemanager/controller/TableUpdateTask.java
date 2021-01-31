package com.sidorov.filemanager.controller;

import com.sidorov.filemanager.model.FileEntity;
import com.sidorov.filemanager.utility.FileManager;
import javafx.beans.property.ObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TableUpdateTask extends Task<List<FileEntity>> {

    private Path path;

    public TableUpdateTask(Path path) {
        this.path = path;
    }

    @Override
    protected List<FileEntity> call() throws Exception {
        List<FileEntity> files = new ArrayList<FileEntity>();
        final long numberFiles = FileManager.getNumberFilesInDirectory(path);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path path : stream) {
                files.add(FileManager.getFileEntity(path));
                updateProgress(files.size() == 0 ? 1 : files.size() - 1, numberFiles);
            }
            updateProgress(files.size(), numberFiles);
        }
        return files;
    }
}
