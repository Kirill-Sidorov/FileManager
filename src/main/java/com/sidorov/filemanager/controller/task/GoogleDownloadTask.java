package com.sidorov.filemanager.controller.task;

import com.sidorov.filemanager.cloud.googledrive.GoogleDriveHolder;
import com.sidorov.filemanager.cloud.googledrive.GoogleDriveManager;
import com.sidorov.filemanager.model.entity.Error;
import com.sidorov.filemanager.model.entity.FileEntity;

import java.io.IOException;
import java.util.List;

public class GoogleDownloadTask extends DownloadTask {

    String format = "%d/%d";

    public GoogleDownloadTask(List<FileEntity> files) { super(files); }

    @Override
    protected Error call() throws Exception {
        Error error = Error.NO;
        if (GoogleDriveHolder.isConnectedDrive()) {
            int listSize = files.size();
            int i = 0;
            updateMessage(String.format(format, ++i, listSize));
            for (FileEntity file : files) {
                try {
                    GoogleDriveManager.uploadFile(file.getId(), downloader -> {
                        switch (downloader.getDownloadState()) {
                            case MEDIA_IN_PROGRESS -> updateProgress(downloader.getProgress(), 1);
                            case MEDIA_COMPLETE -> updateProgress(1, 1);
                        }
                    });
                } catch (IOException e) {
                    error = Error.FILE_NOT_UPLOAD_ERROR;
                }
                updateMessage(String.format(format, ++i, listSize));
            }
        }
        return error;
    }
}
