package com.sidorov.filemanager.model.entity;

import com.sidorov.filemanager.model.ProxyDriveManageable;
import com.sidorov.filemanager.model.GoogleProxyDriveManager;
import com.sidorov.filemanager.model.LocalProxyDriveManager;

public enum Disk {
    LOCAL {
        @Override
        public ProxyDriveManageable getDriveManager() {
            return new LocalProxyDriveManager();
        }
    },
    GOOGLE {
        @Override
        public ProxyDriveManageable getDriveManager() { return new GoogleProxyDriveManager(); }
    },
    DROPBOX {
        @Override
        public ProxyDriveManageable getDriveManager() {
            return null;
        }
    };

    public abstract ProxyDriveManageable getDriveManager();
}
