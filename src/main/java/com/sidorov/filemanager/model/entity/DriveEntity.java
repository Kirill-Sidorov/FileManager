package com.sidorov.filemanager.model.entity;

import com.sidorov.filemanager.model.datagetter.DriveDataGettable;
import com.sidorov.filemanager.model.datamanager.DriveDataManageable;

import java.util.Objects;

public class DriveEntity {
    private String name;
    private String currentPath;
    private Drive drive;
    private DriveDataManageable dataManager;

    public DriveEntity(String name, Drive drive) {
        this.name = name;
        this.drive = drive;
        this.currentPath = name;
    }

    public DriveEntity(String name, String currentPath, Drive drive) {
        this.name = name;
        this.drive = drive;
        this.currentPath = currentPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriveEntity that = (DriveEntity) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public DriveEntity clone() { return new DriveEntity(name, currentPath, drive); }

    public DriveDataGettable getDataGetter() { return drive.getDataGetter(); }
    public DriveDataManageable getDataManager() {
        if (dataManager == null) {
            dataManager = drive.getDataManager();
        }
        return dataManager;
    }
    public boolean isCloudDrive() { return drive.isCloudDrive(); }

    public String getName() { return name; }
    public String getCurrentPath() { return currentPath; }

    public void setCurrentPath(String currentPath) { this.currentPath = currentPath; }
}
