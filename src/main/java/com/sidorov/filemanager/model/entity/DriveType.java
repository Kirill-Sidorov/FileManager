package com.sidorov.filemanager.model.entity;

import com.sidorov.filemanager.controller.task.DownloadTask;
import com.sidorov.filemanager.controller.task.GoogleDownloadTask;
import com.sidorov.filemanager.drive.adapter.AdapterManageable;
import com.sidorov.filemanager.drive.adapter.GoogleDriveAdapterManager;
import com.sidorov.filemanager.drive.adapter.LocalDriveAdapterManager;

import java.util.List;

public enum DriveType {
    LOCAL {
        { this.driveDataManager = new LocalDriveAdapterManager(); }
        @Override
        public AdapterManageable getDataManager() { return driveDataManager; }

        @Override
        public DownloadTask getDownloadTask(List<FileEntity> files) { return null; }
    },
    GOOGLE {
        { this.driveDataManager = new GoogleDriveAdapterManager(); }
        @Override
        public AdapterManageable getDataManager() { return driveDataManager; }

        @Override
        public DownloadTask getDownloadTask(List<FileEntity> files) { return new GoogleDownloadTask(files); }
    },
    DROPBOX {
        { this.driveDataManager = null; }
        @Override
        public AdapterManageable getDataManager() { return driveDataManager; }

        @Override
        public DownloadTask getDownloadTask(List<FileEntity> files) { return null; }
    };
    protected AdapterManageable driveDataManager;

    public abstract AdapterManageable getDataManager();
    public abstract DownloadTask getDownloadTask(List<FileEntity> files);
}
