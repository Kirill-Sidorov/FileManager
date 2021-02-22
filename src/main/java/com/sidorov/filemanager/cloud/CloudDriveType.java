package com.sidorov.filemanager.cloud;

import com.sidorov.filemanager.cloud.googledrive.GDriveAuthorizationUtility;
import com.sidorov.filemanager.model.MappedDriveManager;
import com.sidorov.filemanager.model.entity.Error;
import com.sidorov.filemanager.utility.BundleHolder;

public enum  CloudDriveType {
    GOOGLE {
        @Override
        public Error createDrive() { return GDriveAuthorizationUtility.createDrive(); }

        @Override
        public void addToMappedDrives() { MappedDriveManager.getInstance().addGoogleDrive(); }

        @Override
        public String getTokenDirectoryPath() { return "tokens_google"; }

        @Override
        public String getName() { return BundleHolder.getBundle().getString("message.name.google_drive"); }
    },
    DROPBOX {
        @Override
        public Error createDrive() { return Error.NO; }

        @Override
        public void addToMappedDrives() { MappedDriveManager.getInstance().addDropboxDrive(); }

        @Override
        public String getTokenDirectoryPath() { return "tokens_dropbox"; }

        @Override
        public String getName() { return BundleHolder.getBundle().getString("message.name.dropbox_drive"); }
    };
    public abstract Error createDrive();
    public abstract void addToMappedDrives();
    public abstract String getTokenDirectoryPath();
    public abstract String getName();
}
