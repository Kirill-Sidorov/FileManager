package com.sidorov.filemanager.model.entity;

public class ExecutionResult {
    private Status status;
    private Error error;
    private PathEntity pathEntity;

    public ExecutionResult(Status status) {
        this.status = status;
        this.error = Error.NO;
    }

    public ExecutionResult(Error error) {
        this.status = Status.ERROR;
        this.error = error;
    }

    public ExecutionResult(PathEntity pathEntity) {
        this.pathEntity = pathEntity;
        this.status = Status.NEED_UPDATE_TABLE;
        this.error = Error.NO;
    }

    public Status getStatus() { return status; }
    public Error getError() { return error; }
    public String getPathId() { return pathEntity.getPathId() != null ? pathEntity.getPathId() : ""; }
    public String getPathHumanReadable() {
        return pathEntity.getPathHumanReadable() != null ? pathEntity.getPathHumanReadable() : "";
    }
}
