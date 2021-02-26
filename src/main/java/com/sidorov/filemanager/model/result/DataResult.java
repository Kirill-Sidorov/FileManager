package com.sidorov.filemanager.model.result;

import com.sidorov.filemanager.model.entity.FileEntity;

import java.util.List;

public class DataResult extends Result {

    private List<FileEntity> files;
    private long totalSpace;
    private long unallocatedSpace;

    public DataResult(List<FileEntity> files, long totalSpace, long unallocatedSpace, Error error) {
        super(error);
        this.files = files;
        this.totalSpace = totalSpace;
        this.unallocatedSpace = unallocatedSpace;
    }

    public List<FileEntity> getFiles() { return files; }
    public long getTotalSpace() { return totalSpace; }
    public long getUnallocatedSpace() { return unallocatedSpace; }
}
