package com.sidorov.filemanager.controller.task;

import com.sidorov.filemanager.cloud.googledrive.GDriveAuthorizationUtility;
import com.sidorov.filemanager.cloud.googledrive.GoogleDriveHolder;
import com.sidorov.filemanager.model.DriveManager;
import javafx.concurrent.Task;

public class GoogleDriveAdderTask extends Task<Void> {

    @Override
    protected Void call() throws Exception {
        GDriveAuthorizationUtility.createDrive();
        if (GoogleDriveHolder.isConnectedDrive()) {
            DriveManager.getInstance().addGoogleDrive();
        }
        return null;
    }
}
