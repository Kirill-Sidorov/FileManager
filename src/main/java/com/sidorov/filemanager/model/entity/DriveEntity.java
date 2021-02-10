package com.sidorov.filemanager.model.entity;

import java.util.Objects;

public class DriveEntity {
    private String name;
    private String currentPath;
    private Drive drive;

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

    public String getName() { return name; }
    public String getCurrentPath() { return currentPath; }
    public Drive getDrive() { return drive; }

    public void setCurrentPath(String currentPath) { this.currentPath = currentPath; }
}
