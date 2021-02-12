package com.sidorov.filemanager.cloud.googledrive;


import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.sidorov.filemanager.model.entity.Drive;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.DriveSizeInfo;
import com.sidorov.filemanager.model.entity.FileEntity;
import com.sidorov.filemanager.utility.BundleHolder;

import java.io.IOException;
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
                totalSpace = about.getStorageQuota().getLimit();
                unallocatedSpace = about.getStorageQuota().getUsageInDrive();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new DriveSizeInfo(totalSpace, unallocatedSpace);
    }

    public static DriveEntity getDriveEntity() {
        if (GoogleDriveHolder.isConnectedDrive()) {
            String name;
            try {
                About about = GoogleDriveHolder.getDrive().about().get().setFields("user").execute();
                name = about.getUser().getEmailAddress();
            } catch (IOException e) {
                name = "";
            }
            return new DriveEntity(name, "root", Drive.GOOGLE);
        }
        return null;
    }

    public static List<File> getListDirectoryFiles(String dirId) throws IOException {
        List<File> files = new ArrayList<>();
        String pageToken = null;
        do {
            FileList result = GoogleDriveHolder.getDrive().files().list()
                                .setQ(String.format("'%s' in parents", dirId))
                                .setFields("nextPageToken, files(id, name, size, modifiedTime, mimeType, fileExtension)")
                                .execute();
            files.addAll(result.getFiles());
            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        return files;
    }

    public static FileEntity getFileEntity(final File file) {
        long size = 0L;
        String typeName = "";
        Instant instant = Instant.ofEpochMilli(file.getModifiedTime().getValue());
        LocalDateTime lastDate = LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
        if (file.getSize() != null) { size = file.getSize(); }
        if (file.getFileExtension() != null) { typeName = file.getFileExtension(); }
        if (file.getMimeType().equals(FOLDER_MIME_TYPE)) {
            typeName = BundleHolder.getBundle().getString("message.name.directory");
            size = -1L;
        }
        return new FileEntity(file.getId(), file.getName(), lastDate, size, typeName);
    }

    public static String getParentDirectory(String dirId) throws IOException {
        File file = GoogleDriveHolder.getDrive().files().get(dirId).setFields("parents").execute();
        if (file.getParents() != null) {
            return file.getParents().get(0);
        }
        return "";
    }
}
