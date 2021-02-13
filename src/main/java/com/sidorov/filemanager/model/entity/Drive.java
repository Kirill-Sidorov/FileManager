package com.sidorov.filemanager.model.entity;

import com.sidorov.filemanager.model.datagetter.DriveDataGettable;
import com.sidorov.filemanager.model.datagetter.GoogleDriveDataGetter;
import com.sidorov.filemanager.model.datagetter.LocalDriveDataGetter;
import com.sidorov.filemanager.model.datamanager.DriveDataManageable;
import com.sidorov.filemanager.model.datamanager.GoogleDriveDataManager;
import com.sidorov.filemanager.model.datamanager.LocalDriveDataManager;

public enum Drive {
    LOCAL {
        @Override
        public DriveDataGettable getDataGetter() {
            return new LocalDriveDataGetter();
        }

        @Override
        public DriveDataManageable getDataManager() { return new LocalDriveDataManager(); }

        @Override
        public boolean isCloudDrive() { return false; }
    },
    GOOGLE {
        @Override
        public DriveDataGettable getDataGetter() { return new GoogleDriveDataGetter(); }

        @Override
        public DriveDataManageable getDataManager() { return new GoogleDriveDataManager(); }

        @Override
        public boolean isCloudDrive() { return true; }
    },
    DROPBOX {
        @Override
        public DriveDataGettable getDataGetter() {
            return null;
        }

        @Override
        public DriveDataManageable getDataManager() { return null; }

        @Override
        public boolean isCloudDrive() { return true; }
    };

    public abstract DriveDataGettable getDataGetter();
    public abstract DriveDataManageable getDataManager();
    // Возможно убрать?
    public abstract boolean isCloudDrive();
}
