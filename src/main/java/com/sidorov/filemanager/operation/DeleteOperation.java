package com.sidorov.filemanager.operation;

import java.io.File;
import java.util.List;

public class DeleteOperation extends FileOperation {

    public DeleteOperation(List<File> files, File destination) {
        super(files, destination);
    }

    @Override
    protected Void call() throws Exception {
        return null;
    }
}
