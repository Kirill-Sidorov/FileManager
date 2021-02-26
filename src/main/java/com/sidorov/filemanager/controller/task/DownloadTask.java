package com.sidorov.filemanager.controller.task;

import com.sidorov.filemanager.model.result.Error;
import com.sidorov.filemanager.model.entity.FileEntity;
import com.sidorov.filemanager.utility.BundleHolder;
import javafx.concurrent.Task;

import java.util.List;

public abstract class DownloadTask extends Task<Error> {

    protected final String format = BundleHolder.getBundle().getString("string.format.download_file");
    protected final List<FileEntity> files;

    public DownloadTask(List<FileEntity> files) { this.files = files; }
}
