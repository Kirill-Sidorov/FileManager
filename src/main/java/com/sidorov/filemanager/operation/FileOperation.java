package com.sidorov.filemanager.operation;

import com.sidorov.filemanager.dialog.ProgressDialog;
import javafx.concurrent.Task;

import java.io.File;
import java.util.List;

public abstract class FileOperation extends Task<Void> {

    private ProgressDialog progressDialog;

    private final List<File> files;
    private final File destination;

    public FileOperation(List<File> files, File destination) {
        this.files = files;
        this.destination = destination;
    }
}
