package com.sidorov.filemanager.drive.googledrive;

import com.google.api.services.drive.Drive;

public class GoogleDriveHolder {

    private static Drive googleDrive;

    private GoogleDriveHolder() {}

    public static void setDrive(Drive drive) { googleDrive = drive; }
    public static Drive getDrive() { return googleDrive; }
    public static boolean isConnectedDrive() { return googleDrive != null; }
}
