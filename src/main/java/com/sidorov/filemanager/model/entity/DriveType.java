package com.sidorov.filemanager.model.entity;

import com.sidorov.filemanager.controller.task.DownloadTask;
import com.sidorov.filemanager.controller.task.GoogleDownloadTask;
import com.sidorov.filemanager.model.datagetter.DriveDataGettable;
import com.sidorov.filemanager.model.datagetter.GoogleDriveDataGetter;
import com.sidorov.filemanager.model.datagetter.LocalDriveDataGetter;
import com.sidorov.filemanager.model.datamanager.DriveDataManageable;
import com.sidorov.filemanager.model.datamanager.GoogleDriveDataManager;
import com.sidorov.filemanager.model.datamanager.LocalDriveDataManager;

import java.util.List;

public enum DriveType {
    LOCAL {
        @Override
        public DriveDataGettable getDataGetter() {
            return new LocalDriveDataGetter();
        }

        @Override
        public DriveDataManageable getDataManager() { return new LocalDriveDataManager(); }

        @Override
        public DownloadTask getDownloadTask(List<FileEntity> files) { return null; }
    },
    GOOGLE {
        @Override
        public DriveDataGettable getDataGetter() { return new GoogleDriveDataGetter(); }

        @Override
        public DriveDataManageable getDataManager() { return new GoogleDriveDataManager(); }

        @Override
        public DownloadTask getDownloadTask(List<FileEntity> files) { return new GoogleDownloadTask(files); }
    },
    DROPBOX {
        @Override
        public DriveDataGettable getDataGetter() {
            return null;
        }

        @Override
        public DriveDataManageable getDataManager() { return null; }

        @Override
        public DownloadTask getDownloadTask(List<FileEntity> files) { return null; }
    };

    public abstract DriveDataGettable getDataGetter();
    public abstract DriveDataManageable getDataManager();
    public abstract DownloadTask getDownloadTask(List<FileEntity> files);
}
