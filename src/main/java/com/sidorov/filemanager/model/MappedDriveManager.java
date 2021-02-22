package com.sidorov.filemanager.model;

import com.sidorov.filemanager.cloud.CloudDriveType;
import com.sidorov.filemanager.cloud.googledrive.GoogleDriveHolder;
import com.sidorov.filemanager.cloud.googledrive.GoogleDriveManager;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.utility.LocalDriveManager;

import java.io.File;
import java.util.*;

public class MappedDriveManager {
    private static MappedDriveManager instance = new MappedDriveManager();

    private Map<String, DriveEntity> cloudDrives = new Hashtable<String, DriveEntity>();
    private String GDriveName;

    public static MappedDriveManager getInstance() {
        return instance;
    }

    public DriveEntity getDriveByName(String name) {
        DriveEntity drive = cloudDrives.get(name);
        if (drive == null) {
            drive = LocalDriveManager.getDriveEntityByName(name);
        } else {
            drive = drive.clone();
        }
        return drive;
    }

    public Set<String> getDrives() {
        Set<String> drives = LocalDriveManager.getRootDirectories();
        drives.addAll(cloudDrives.keySet());
        return drives;
    }

    public void addGoogleDrive() {
        if (GoogleDriveHolder.isConnectedDrive() && GDriveName == null) {
            DriveEntity drive = GoogleDriveManager.getDriveEntity();
            GDriveName = drive.getName();
            cloudDrives.put(drive.getName(), drive);
        }
    }

    public void removeGoogleDrive() {
        if (GDriveName != null) {
            cloudDrives.remove(GDriveName);
            GDriveName = null;
            GoogleDriveHolder.setDrive(null);
            File directory = new File(CloudDriveType.GOOGLE.getTokenDirectoryPath());
            for (File token : directory.listFiles()) {
                token.delete();
            }
        }
    }

    public void addDropboxDrive() { }

    public void removeDropboxDrive() { }

}
