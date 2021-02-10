package com.sidorov.filemanager.model.entity;

import java.util.List;

public class TableData {
    private List<FileEntity> files;
    private DriveSizeInfo driveSizeInfo;

    public TableData(List<FileEntity> files, DriveSizeInfo driveSizeInfo) {
        this.files = files;
        this.driveSizeInfo = driveSizeInfo;
    }

    public List<FileEntity> getFiles() {
        return files;
    }

    public long getTotalSpace() {
        return driveSizeInfo.getTotalSpace();
    }

    public long getUnallocatedSpace() {
        return driveSizeInfo.getUnallocatedSpace();
    }
}
