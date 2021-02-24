package com.sidorov.filemanager.model.entity;

import com.sidorov.filemanager.controller.task.DownloadTask;
import com.sidorov.filemanager.model.datagetter.DriveDataGettable;
import com.sidorov.filemanager.model.datamanager.DriveDataManageable;

import java.util.List;
import java.util.Objects;

public class DriveEntity {
    private String name;
    private String currentPath;
    private String humanReadablePath;
    private DriveType driveType;

    public DriveEntity(String name, DriveType driveType) {
        this.name = name;
        this.driveType = driveType;
        this.currentPath = name;
        this.humanReadablePath = name;
    }

    public DriveEntity(String name, String currentPath, String humanReadablePath, DriveType driveType) {
        this.name = name;
        this.driveType = driveType;
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

    public DriveEntity clone() { return new DriveEntity(name, currentPath, humanReadablePath, driveType); }

    public DriveDataGettable getDataGetter() { return driveType.getDataGetter(); }
    public DriveDataManageable getDataManager() { return driveType.getDataManager(); }
    public DownloadTask getDownloadTask(List<FileEntity> files) { return driveType.getDownloadTask(files); }

    public String getName() { return name; }
    public String getCurrentPath() { return currentPath; }
    public String getHumanReadablePath() { return humanReadablePath; }

    public void setPaths(String currentPath, String humanReadablePath) {
        this.currentPath = currentPath;
        this.humanReadablePath = humanReadablePath;
    }
}
