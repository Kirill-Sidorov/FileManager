package com.sidorov.filemanager.drive.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;
import com.sidorov.filemanager.model.result.Error;

public final class DropboxAuthorizationUtility {

    private static final String ACCESS_TOKEN = "<ACCESS TOKEN>";

    private DropboxAuthorizationUtility() {}

    private static Error testConnection(final DbxClientV2 client) {
        Error error = Error.NO;
        try {
            FullAccount account = client.users().getCurrentAccount();
        } catch (DbxException e) {
            error = Error.CLOUD_DRIVE_NOT_CONNECTED;
        }
        return error;
    }

    public static Error createDrive() {
        if (DropboxHolder.isConnectedDrive()) {
            return Error.NO;
        }
        DbxRequestConfig config = DbxRequestConfig.newBuilder("FileManager_KirillSidorov/1.0").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        Error error = testConnection(client);
        if (error == Error.NO) {
            DropboxHolder.setDrive(client);
        }
        return error;
    }
}
