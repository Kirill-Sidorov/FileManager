package com.sidorov.filemanager.model;

import com.sidorov.filemanager.cloud.CloudInstance;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.utility.LocalDriveManager;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class DriveManager {
    private static DriveManager instance = new DriveManager();

    private Set<DriveEntity> cloudDrives;

    private DriveManager() { init(); }

    private void init() {
        cloudDrives = new HashSet<DriveEntity>();
        File directory = new File("/tokens");
        if (directory.isDirectory() && directory.list().length != 0) {
            try {
                CloudInstance.getInstance().addGDrive();
                //cloudDrives = new DriveEntity()
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public static DriveManager getInstance() {
        return instance;
    }

    public List<DriveEntity> getDrives() {
        List<DriveEntity> listDrives = new ArrayList<DriveEntity>();
        listDrives.addAll(LocalDriveManager.getLocalDrives());
        listDrives.addAll(cloudDrives);
        return listDrives;
    }

    public long getDriveTotalSpace(DriveEntity drive) {
        return drive.getDisk().getDriveManager().getDriveTotalSpace(drive);
    }

    public long getDriveUnallocatedSpace(DriveEntity drive) {
        return drive.getDisk().getDriveManager().getDriveUnallocatedSpace(drive);
    }

}
