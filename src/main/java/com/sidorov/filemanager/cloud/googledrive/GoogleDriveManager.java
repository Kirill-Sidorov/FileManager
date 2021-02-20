package com.sidorov.filemanager.cloud.googledrive;


import com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListener;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.sidorov.filemanager.model.entity.*;
import com.sidorov.filemanager.utility.BundleHolder;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public final class GoogleDriveManager {

    private static final String FOLDER_MIME_TYPE = "application/vnd.google-apps.folder";

    public static DriveSizeInfo getDriveSizeInfo() {
        long totalSpace = 0L;
        long unallocatedSpace = 0L;
        if (GoogleDriveHolder.isConnectedDrive()) {
            try {
                About about = GoogleDriveHolder.getDrive().about().get().setFields("storageQuota(limit, usageInDrive)").execute();
                totalSpace = (about.getStorageQuota().getLimit() != null) ? about.getStorageQuota().getLimit() : 0L;
                unallocatedSpace = (about.getStorageQuota().getUsageInDrive() != null) ? about.getStorageQuota().getUsageInDrive() : 0L;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new DriveSizeInfo(totalSpace, unallocatedSpace);
    }

    public static DriveEntity getDriveEntity() {
        if (GoogleDriveHolder.isConnectedDrive()) {
            String name;
            String rootId;
            String humanReadablePath;
            try {
                About about = GoogleDriveHolder.getDrive().about().get().setFields("user").execute();
                File file = GoogleDriveHolder.getDrive().files().get("root").setFields("id, name").execute();
                name = (about.getUser().getEmailAddress() != null) ? about.getUser().getEmailAddress() : "";
                rootId = (file.getId() != null) ? file.getId() : "";
                humanReadablePath = (file.getName() != null) ? file.getName() : "";
            } catch (IOException e) {
                name = "";
                rootId = "";
                humanReadablePath = "";
            }
            return new DriveEntity(name, rootId, humanReadablePath, DriveType.GOOGLE);
        }
        return null;
    }

    public static List<File> getListDirectoryFiles(final String dirId) throws IOException {
        List<File> files = new ArrayList<>();
        if (GoogleDriveHolder.isConnectedDrive()) {
            String pageToken = null;
            do {
                FileList result = GoogleDriveHolder.getDrive().files().list()
                        .setQ(String.format("'%s' in parents", dirId))
                        .setFields("nextPageToken, files(id, name, size, modifiedTime, mimeType, fileExtension)")
                        .execute();
                files.addAll(result.getFiles());
                pageToken = result.getNextPageToken();
            } while (pageToken != null);
        }
        return files;
    }

    public static FileEntity getFileEntity(final File file) {
        String id;
        String name;
        long size;
        String typeName;
        Instant instant = Instant.ofEpochMilli(file.getModifiedTime().getValue());
        LocalDateTime lastDate = LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());

        id = (file.getId() != null) ? file.getId() : "";
        name = (file.getName() != null) ? file.getName() : "";
        size = (file.getSize() != null) ? file.getSize() : 0L;
        typeName = (file.getFileExtension() != null) ? file.getFileExtension() : "";

        if (file.getMimeType().equals(FOLDER_MIME_TYPE)) {
            typeName = BundleHolder.getBundle().getString("message.name.directory");
            size = -1L;
        }
        return new FileEntity(id, name, lastDate, size, typeName);
    }

    public static String getParentDirectoryId(final String id) {
        String parentId = null;
        try {
            if (GoogleDriveHolder.isConnectedDrive()) {
                File file = GoogleDriveHolder.getDrive().files().get(id).setFields("parents").execute();
                parentId = (file.getParents() != null) ? file.getParents().get(0) : "";
            }
        } catch (IOException e) {
            parentId = null;
        }
        return parentId;
    }

    public static void uploadFile(final String id, final MediaHttpDownloaderProgressListener listener) throws IOException {
        FileOutputStream outputStream = new FileOutputStream("newFile2");
        Drive.Files.Get request = GoogleDriveHolder.getDrive().files().get(id);
        request.getMediaHttpDownloader().setProgressListener(listener);
        request.executeMediaAndDownloadTo(outputStream);
        outputStream.close();
    }

    public static String getNextDirectoryName(final String id) {
        String dirName = null;
        try {
            if (GoogleDriveHolder.isConnectedDrive()) {
                File file = GoogleDriveHolder.getDrive().files().get(id).setFields("name").execute();
                dirName = (file.getName() != null) ? file.getName() : "";
            }
        } catch (IOException e) {
            dirName = null;
        }
        return dirName;
    }

    public static boolean isFileExist(final String id) {
        File file = null;
        try {
            if (GoogleDriveHolder.isConnectedDrive()) {
                file = GoogleDriveHolder.getDrive().files().get(id).execute();
            }
        } catch (IOException e) {
            file = null;
        }
        return file != null;
    }
}
