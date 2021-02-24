package com.sidorov.filemanager.model.entity;

import com.sidorov.filemanager.controller.task.DownloadTask;
import com.sidorov.filemanager.controller.task.GoogleDownloadTask;
import com.sidorov.filemanager.model.datagetter.DriveDataGettable;
import com.sidorov.filemanager.model.datagetter.DropboxDataGetter;
import com.sidorov.filemanager.model.datagetter.GoogleDriveDataGetter;
import com.sidorov.filemanager.model.datagetter.LocalDriveDataGetter;
import com.sidorov.filemanager.model.datamanager.DriveDataManageable;
import com.sidorov.filemanager.model.datamanager.GoogleDriveDataManager;
import com.sidorov.filemanager.model.datamanager.LocalDriveDataManager;

import java.util.List;

public enum DriveType {
    LOCAL {
        {
            this.driveDataGetter = new LocalDriveDataGetter();
            this.driveDataManager = new LocalDriveDataManager();
        }
        @Override
        public DriveDataGettable getDataGetter() {
            return driveDataGetter;
        }

        @Override
        public DriveDataManageable getDataManager() { return driveDataManager; }

        @Override
        public DownloadTask getDownloadTask(List<FileEntity> files) { return null; }
    },
    GOOGLE {
        {
            this.driveDataGetter = new GoogleDriveDataGetter();
            this.driveDataManager = new GoogleDriveDataManager();
        }
        @Override
        public DriveDataGettable getDataGetter() { return driveDataGetter; }

        @Override
        public DriveDataManageable getDataManager() { return driveDataManager; }

        @Override
        public DownloadTask getDownloadTask(List<FileEntity> files) { return new GoogleDownloadTask(files); }
    },
    DROPBOX {
        {
            this.driveDataGetter = new DropboxDataGetter();
            this.driveDataManager = null;
        }
        @Override
        public DriveDataGettable getDataGetter() {
            return driveDataGetter;
        }

        @Override
        public DriveDataManageable getDataManager() { return driveDataManager; }

        @Override
        public DownloadTask getDownloadTask(List<FileEntity> files) { return null; }
    };
    protected DriveDataGettable driveDataGetter;
    protected DriveDataManageable driveDataManager;

    public abstract DriveDataGettable getDataGetter();
    public abstract DriveDataManageable getDataManager();
    public abstract DownloadTask getDownloadTask(List<FileEntity> files);
}
