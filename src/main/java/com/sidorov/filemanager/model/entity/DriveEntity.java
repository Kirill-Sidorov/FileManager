package com.sidorov.filemanager.model.entity;

import com.sidorov.filemanager.model.datagetter.DriveDataGettable;
import com.sidorov.filemanager.model.datamanager.DriveDataManageable;

import java.util.Objects;

public class DriveEntity {
    private String name;
    private String currentPath;
    private String humanReadablePath;
    private Drive drive;
    private DriveDataManageable dataManager;

    public DriveEntity(String name, Drive drive) {
        this.name = name;
        this.drive = drive;
        this.currentPath = name;
        this.humanReadablePath = name;
    }

    public DriveEntity(String name, String currentPath, String humanReadablePath, Drive drive) {
        this.name = name;
        this.drive = drive;
        this.currentPath = currentPath;
        this.humanReadablePath = humanReadablePath;
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

    public DriveEntity clone() { return new DriveEntity(name, currentPath, humanReadablePath, drive); }

    public DriveDataGettable getDataGetter() { return drive.getDataGetter(); }
    public DriveDataManageable getDataManager() {
        if (dataManager == null) {
            dataManager = drive.getDataManager();
        }
        return dataManager;
    }

    public String getName() { return name; }
    public String getCurrentPath() { return currentPath; }
    public String getHumanReadablePath() { return humanReadablePath; }

    public void setPaths(String currentPath, String humanReadablePath) {
        this.currentPath = currentPath;
        this.humanReadablePath = humanReadablePath;
    }
}
