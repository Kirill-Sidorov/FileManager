package com.sidorov.filemanager.model.entity;

import java.time.LocalDateTime;

public class FileEntity {
    private String id;
    private String name;
    private LocalDateTime lastDate;
    private long size;
    private String type;

    public FileEntity(String name, LocalDateTime lastDate, long size, String type) {
        this.id = name;
        this.name = name;
        this.lastDate = lastDate;
        this.size = size;
        this.type = type;
    }

    public FileEntity(String id, String name, LocalDateTime lastDate, long size, String type) {
        this(name, lastDate, size, type);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getLastDate() {
        return lastDate;
    }

    public long getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}
