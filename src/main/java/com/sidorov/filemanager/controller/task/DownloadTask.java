package com.sidorov.filemanager.controller.task;

import com.sidorov.filemanager.model.entity.Error;
import com.sidorov.filemanager.model.entity.FileEntity;
import javafx.concurrent.Task;

import java.util.List;

public abstract class DownloadTask extends Task<Error> {

    protected final List<FileEntity> files;

    public DownloadTask(List<FileEntity> files) { this.files = files; }
}
