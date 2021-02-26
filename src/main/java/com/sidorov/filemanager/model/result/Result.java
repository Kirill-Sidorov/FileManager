package com.sidorov.filemanager.model.result;

public class Result {
    protected Status status;
    protected Error error;

    public Result() {
        this.status = Status.OK;
        this.error = Error.NO;
    }

    public Result(Status status) {
        this.status = status;
        this.error = Error.NO;
    }

    public Result(Error error) {
        this.status = Status.ERROR;
        this.error = error;
    }

    public Status getStatus() { return status; }
    public Error getError() { return error; }
}
