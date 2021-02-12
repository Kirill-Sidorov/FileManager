package com.sidorov.filemanager.model.entity;

public class StatusEntity {
    private Status status;
    private String path;

    public StatusEntity(Status status) {
        this.status = status;
    }

    public StatusEntity(String path) {
        this.path = path;
        this.status = Status.OK;
    }

    public Status getStatus() { return status; }
    public String getPath() { return path; }
}
