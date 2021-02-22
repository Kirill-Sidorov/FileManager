package com.sidorov.filemanager.controller.task;

import com.sidorov.filemanager.cloud.googledrive.GoogleDriveManager;
import com.sidorov.filemanager.model.entity.Error;
import com.sidorov.filemanager.model.entity.FileEntity;

import java.util.List;

public class GoogleDownloadTask extends DownloadTask {

    public GoogleDownloadTask(List<FileEntity> files) { super(files); }

    @Override
    protected Error call() throws Exception {
        Error error = Error.NO;
        int listSize = files.size();
        int i = 1;
        for (FileEntity file : files) {
            updateMessage(String.format(format, file.getName(), i++, listSize));
            error = GoogleDriveManager.uploadFile(file, downloader -> updateProgress(downloader.getProgress(), 1));
        }
        return error;
    }
}
