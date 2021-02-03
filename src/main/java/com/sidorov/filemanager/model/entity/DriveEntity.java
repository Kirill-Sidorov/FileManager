package com.sidorov.filemanager.model.entity;

import java.util.Objects;

public class DriveEntity {
    private String name;
    private String currentPath;
    private Disk disk;

    public DriveEntity(String name, Disk disk) {
        this.name = name;
        this.disk = disk;
        this.currentPath = name;
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

    public String getName() { return name; }
    public String getCurrentPath() { return currentPath; }
    public Disk getDisk() { return disk; }

    public void setCurrentPath(String currentPath) { this.currentPath = currentPath; }
}
