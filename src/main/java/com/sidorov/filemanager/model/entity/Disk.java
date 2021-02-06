package com.sidorov.filemanager.model.entity;

import com.sidorov.filemanager.model.AdapterDriveManageable;
import com.sidorov.filemanager.model.GoogleAdapterDriveManager;
import com.sidorov.filemanager.model.LocalAdapterDriveManager;

public enum Disk {
    LOCAL {
        @Override
        public AdapterDriveManageable getDriveManager() {
            return new LocalAdapterDriveManager();
        }
    },
    GOOGLE {
        @Override
        public AdapterDriveManageable getDriveManager() { return new GoogleAdapterDriveManager(); }
    },
    DROPBOX {
        @Override
        public AdapterDriveManageable getDriveManager() {
            return null;
        }
    };

    public abstract AdapterDriveManageable getDriveManager();
}
