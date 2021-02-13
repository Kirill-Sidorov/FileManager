package com.sidorov.filemanager.model;

import com.sidorov.filemanager.cloud.googledrive.GDriveAuthorizationUtility;
import com.sidorov.filemanager.cloud.googledrive.GoogleDriveHolder;
import com.sidorov.filemanager.cloud.googledrive.GoogleDriveManager;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.utility.LocalDriveManager;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class MappedDriveManager {
    private static MappedDriveManager instance = new MappedDriveManager();

    private Map<String, DriveEntity> cloudDrives;
    private String GDriveName;

    private MappedDriveManager() { init(); }

    private void init() {
        cloudDrives = new Hashtable<String, DriveEntity>();
        initGoogleDrive();
    }

    private void initGoogleDrive() {
        File directory = new File("tokens");
        if (directory.isDirectory() && directory.list().length != 0) {
            try {
                GDriveAuthorizationUtility.createDrive();
                DriveEntity drive = GoogleDriveManager.getDriveEntity();
                GDriveName = drive.getName();
                cloudDrives.put(drive.getName(), drive);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
    }

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
        DriveEntity drive = GoogleDriveManager.getDriveEntity();
        cloudDrives.put(drive.getName(), drive);
    }

    public void removeGoogleDrive() {
        cloudDrives.remove(GDriveName);
        GoogleDriveHolder.setDrive(null);
        File directory = new File("tokens");
        for (File token : directory.listFiles()) {
            token.delete();
        }
    }

}
