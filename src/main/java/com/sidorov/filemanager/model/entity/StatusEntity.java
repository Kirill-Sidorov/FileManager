package com.sidorov.filemanager.model.entity;

public class StatusEntity {
    private Status status;
    private PathEntity pathEntity;

    public StatusEntity(Status status) {
        this.status = status;
    }

    public StatusEntity(PathEntity pathEntity) {
        this.pathEntity = pathEntity;
        this.status = Status.OK;
    }

    public Status getStatus() { return status; }
    public String getPathId() { return pathEntity.getPathId() != null ? pathEntity.getPathId() : ""; }
    public String getPathHumanReadable() {
        return pathEntity.getPathHumanReadable() != null ? pathEntity.getPathHumanReadable() : "";
    }
}
