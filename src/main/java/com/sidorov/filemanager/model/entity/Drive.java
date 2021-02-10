package com.sidorov.filemanager.model.entity;

import com.sidorov.filemanager.model.fileadapter.AdapterFileManageable;
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
    },
    GOOGLE {
        @Override
        public AdapterDriveManageable getDriveManager() { return new GoogleAdapterDriveManager(); }

        @Override
        public AdapterFileManageable getFileManager() { return null; }
    },
    DROPBOX {
        @Override
        public AdapterDriveManageable getDriveManager() {
            return null;
        }

        @Override
        public AdapterFileManageable getFileManager() { return null; }
    };

    public abstract AdapterDriveManageable getDriveManager();
    public abstract AdapterFileManageable getFileManager();
}
