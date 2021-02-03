package com.sidorov.filemanager.cloud;

import com.google.api.services.drive.Drive;
import com.sidorov.filemanager.cloud.googledrive.GDriveAuthorizationUtility;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class CloudInstance {
    private static CloudInstance instance = new CloudInstance();
    private Drive googleDrive;

    private CloudInstance() {}

    public static CloudInstance getInstance() {
        return instance;
    }

    public void addGDrive() throws IOException, GeneralSecurityException {
        if (googleDrive == null) {
            googleDrive = GDriveAuthorizationUtility.createDrive();
        }
    }

    public Drive getGDrive() {
        return googleDrive;
    }

    public boolean isConnectedGDrive() {
        return googleDrive == null ? false : true;
    }

}
