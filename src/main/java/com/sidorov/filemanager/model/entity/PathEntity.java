package com.sidorov.filemanager.model.entity;

public class PathEntity {
    private String pathId;
    private String pathHumanReadable;

    public PathEntity(String pathId, String pathHumanReadable) {
        this.pathId = pathId;
        this.pathHumanReadable = pathHumanReadable;
    }

    public String getPathId() { return pathId; }
    public String getPathHumanReadable() { return pathHumanReadable; }
}
