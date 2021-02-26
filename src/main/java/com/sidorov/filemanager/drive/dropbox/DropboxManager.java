package com.sidorov.filemanager.drive.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.google.api.services.drive.model.File;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.FileEntity;

import java.util.ArrayList;
import java.util.List;

public final class DropboxManager {

    private DropboxManager() {}

    public static DriveEntity getDriveEntity() {
        return null;
    }

    public static List<Metadata> getListDirectoryFiles(final String dirId) throws DbxException {
        List<Metadata> files = new ArrayList<>();
        if (DropboxHolder.isConnectedDrive()) {
            ListFolderResult result = DropboxHolder.getDrive().files().listFolder(dirId);
            do {
                for (Metadata metadata : result.getEntries()) {
                    files.add(metadata);
                }
                result = DropboxHolder.getDrive().files().listFolderContinue(result.getCursor());
            } while (result.getHasMore());
        }
        return files;
    }

    public static FileEntity getFileEntity(final File file) {
        return null;
    }
}
