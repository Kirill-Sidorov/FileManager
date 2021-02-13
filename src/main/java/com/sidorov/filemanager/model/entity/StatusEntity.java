package com.sidorov.filemanager.model.entity;

public class StatusEntity {
    private Status status;
    private String fileId;

    public StatusEntity(Status status) {
        this.status = status;
    }

    public StatusEntity(String fileId) {
        this.fileId = fileId;
        this.status = Status.OK;
    }

    public Status getStatus() { return status; }
    public String getFileId() { return fileId; }
}
