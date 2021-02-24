package com.sidorov.filemanager.cloud.dropbox;

import com.dropbox.core.v2.DbxClientV2;

public class DropboxHolder {
    private static DbxClientV2 dropbox;

    private DropboxHolder() {}

    public static void setDrive(DbxClientV2 drive) { dropbox = drive; }
    public static DbxClientV2 getDrive() { return dropbox; }
    public static boolean isConnectedDrive() { return dropbox != null; }
}
