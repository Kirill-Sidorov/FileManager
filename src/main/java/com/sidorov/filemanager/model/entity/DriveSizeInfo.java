package com.sidorov.filemanager.model.entity;

public class DriveSizeInfo {

    private long totalSpace;
    private long unallocatedSpace;

    public DriveSizeInfo(long totalSpace, long unallocatedSpace) {
        this.totalSpace = totalSpace;
        this.unallocatedSpace = unallocatedSpace;
    }

    public long getTotalSpace() {
        return totalSpace;
    }

    public long getUnallocatedSpace() {
        return unallocatedSpace;
    }
}
