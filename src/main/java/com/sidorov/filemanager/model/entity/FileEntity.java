package com.sidorov.filemanager.model.entity;

import java.time.LocalDateTime;

public class FileEntity {
    private String name;
    private LocalDateTime lastDate;
    private long size;
    private String type;

    public FileEntity(String name, LocalDateTime lastDate, long size, String type) {
        this.name = name;
        this.lastDate = lastDate;
        this.size = size;
        this.type = type;
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
