package com.sidorov.filemanager.model.result;

public class PathResult extends Result {

    private String pathId;
    private String pathHumanReadable;

    public PathResult(Error error) { super(error); }

    public PathResult(String pathId, String pathHumanReadable) {
        this.status = Status.NEED_UPDATE_TABLE;
        this.error = Error.NO;
        this.pathId = pathId;
        this.pathHumanReadable = pathHumanReadable;
    }

    public String getPathId() { return pathId; }
    public String getPathHumanReadable() { return pathHumanReadable; }
}
