package com.sidorov.filemanager.model.entity;

import com.sidorov.filemanager.model.fileadapter.AdapterFileManageable;
import com.sidorov.filemanager.model.fileadapter.GoogleAdapterFileManager;
import com.sidorov.filemanager.model.fileadapter.LocalAdapterFileManager;
import com.sidorov.filemanager.model.driveadapter.AdapterDriveManageable;
import com.sidorov.filemanager.model.driveadapter.GoogleAdapterDriveManager;
import com.sidorov.filemanager.model.driveadapter.LocalAdapterDriveManager;

public enum Drive {
    LOCAL {
        @Override
        public AdapterDriveManageable getDriveManager() {
            return new LocalAdapterDriveManager();
        }

        @Override
        public AdapterFileManageable getFileManager() { return new LocalAdapterFileManager(); }

        @Override
        public boolean isCloudDrive() { return false; }
    },
    GOOGLE {
        @Override
        public AdapterDriveManageable getDriveManager() { return new GoogleAdapterDriveManager(); }

        @Override
        public AdapterFileManageable getFileManager() { return new GoogleAdapterFileManager(); }

        @Override
        public boolean isCloudDrive() { return true; }
    },
    DROPBOX {
        @Override
        public AdapterDriveManageable getDriveManager() {
            return null;
        }

        @Override
        public AdapterFileManageable getFileManager() { return null; }

        @Override
        public boolean isCloudDrive() { return true; }
    };

    public abstract AdapterDriveManageable getDriveManager();
    public abstract AdapterFileManageable getFileManager();
    public abstract boolean isCloudDrive();
}
