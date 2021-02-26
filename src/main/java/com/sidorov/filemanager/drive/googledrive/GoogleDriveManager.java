package com.sidorov.filemanager.drive.googledrive;

import com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListener;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.sidorov.filemanager.cloud.CloudDriveType;
import com.sidorov.filemanager.controller.service.ProgressUpdater;
import com.sidorov.filemanager.model.entity.*;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.DriveType;
import com.sidorov.filemanager.model.result.Error;
import com.sidorov.filemanager.model.result.DataResult;
import com.sidorov.filemanager.model.result.PathResult;
import com.sidorov.filemanager.utility.BundleHolder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public final class GoogleDriveManager {

    private static final String FOLDER_MIME_TYPE = "application/vnd.google-apps.folder";
    private static final String DOWNLOAD_DIRECTORY = "C:\\Users\\user\\Downloads\\";

    private GoogleDriveManager() {}

    public static DriveEntity getDriveEntity() {
        if (GoogleDriveHolder.isConnectedDrive()) {
            String name = CloudDriveType.GOOGLE.getName();
            String rootId;
            String humanReadablePath;
            try {
                //About about = GoogleDriveHolder.getDrive().about().get().setFields("user").execute();
                File file = GoogleDriveHolder.getDrive().files().get("root").setFields("id, name").execute();
                //name = (about.getUser().getEmailAddress() != null) ? about.getUser().getEmailAddress() : "";
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

    public static DataResult getListDirectoryFiles(final String dirId, ProgressUpdater updater) {
        Error error;
        List<FileEntity> files = new ArrayList<>();
        if (GoogleDriveHolder.isConnectedDrive()) {
            String pageToken = null;
            error = Error.NO;
            do {
                try {
                    FileList result = GoogleDriveHolder.getDrive().files().list()
                            .setQ(String.format("'%s' in parents", dirId))
                            .setFields("nextPageToken, files(id, name, size, modifiedTime, mimeType, fileExtension)")
                            .execute();
                    int workDone = 1;
                    int max = result.getFiles().size();
                    for (File file : result.getFiles()) {
                        files.add(getFileEntity(file));
                        updater.update(workDone++, max);
                    }
                    pageToken = result.getNextPageToken();
                } catch (IOException e) {
                    error = Error.NOT_ALL_FILES_WERE_RETRIEVED;
                }
            } while (pageToken != null);
        } else {
            error = Error.CLOUD_DRIVE_NOT_CONNECTED;
        }
        long[] sizeInfo = getDriveSizeInfo();
        return new DataResult(files, sizeInfo[0], sizeInfo[1], error);
    }

    public static PathResult getParentDirectory(final String id, final String readablePath) {
        PathResult pathResult;
        try {
            if (GoogleDriveHolder.isConnectedDrive()) {
                File file = GoogleDriveHolder.getDrive().files().get(id).setFields("parents").execute();
                if (file.getParents() != null) {
                    String parentId = file.getParents().get(0);
                    if (parentId.length() != 0){
                        int index = readablePath.lastIndexOf("\\");
                        String newReadablePath = (index == -1) ? readablePath : readablePath.substring(0, index);
                        pathResult = new PathResult(id, newReadablePath);
                    } else {
                        pathResult = new PathResult(id, readablePath);
                    }
                } else {
                    pathResult = new PathResult(Error.FILE_NOT_FOUND_ERROR);
                }
            } else {
                pathResult = new PathResult(Error.CLOUD_DRIVE_NOT_CONNECTED);
            }
        } catch (IOException e) {
            pathResult = new PathResult(Error.FAILED_GET_DIRECTORY_FILES);
        }
        return pathResult;
    }

    public static Error uploadFile(final FileEntity file, final MediaHttpDownloaderProgressListener listener) {
        Error error = Error.NO;
        if (GoogleDriveHolder.isConnectedDrive()) {
            try (FileOutputStream outputStream = new FileOutputStream(DOWNLOAD_DIRECTORY + file.getName())) {
                Drive.Files.Get request = GoogleDriveHolder.getDrive().files().get(file.getId());
                request.getMediaHttpDownloader().setProgressListener(listener);
                request.executeMediaAndDownloadTo(outputStream);
            } catch (IOException e) {
                error = Error.FILE_NOT_UPLOAD_ERROR;
            }
        } else {
            error = Error.CLOUD_DRIVE_NOT_CONNECTED;
        }
        return error;
    }

    public static PathResult getNextDirectory(final String dirId, final String readablePath) {
        PathResult pathResult;
        try {
            if (GoogleDriveHolder.isConnectedDrive()) {
                File file = GoogleDriveHolder.getDrive().files().get(dirId).setFields("name").execute();
                if (file.getName() != null) {
                    pathResult = new PathResult(dirId, readablePath + "\\" + file.getName());
                } else {
                    pathResult = new PathResult(dirId, readablePath);
                }
            } else {
                pathResult = new PathResult(Error.CLOUD_DRIVE_NOT_CONNECTED);
            }
        } catch (IOException e) {
            pathResult = new PathResult(Error.FAILED_GET_DIRECTORY_FILES);
        }
        return pathResult;
    }

    public static boolean isFileExist(final String id) {
        File file;
        try {
            if (GoogleDriveHolder.isConnectedDrive()) {
                file = GoogleDriveHolder.getDrive().files().get(id).execute();
            } else {
                file = null;
            }
        } catch (IOException e) {
            file = null;
        }
        return file != null;
    }

    private static FileEntity getFileEntity(final File file) {
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

    private static long[] getDriveSizeInfo() {
        long totalSpace = 0L;
        long unallocatedSpace = 0L;
        if (GoogleDriveHolder.isConnectedDrive()) {
            try {
                About about = GoogleDriveHolder.getDrive().about().get().setFields("storageQuota(limit, usageInDrive)").execute();
                totalSpace = (about.getStorageQuota().getLimit() != null) ? about.getStorageQuota().getLimit() : 0L;
                unallocatedSpace = (about.getStorageQuota().getUsageInDrive() != null) ? about.getStorageQuota().getUsageInDrive() : 0L;
            } catch (IOException e) {
                totalSpace = 0L;
                unallocatedSpace = 0L;
            }
        }
        return new long[]{totalSpace, unallocatedSpace};
    }
}
