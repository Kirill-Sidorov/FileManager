package com.sidorov.filemanager.model.entity;

import java.time.LocalDateTime;

public class FileEntity {
    private String id;
    private String name;
    private LocalDateTime modifiedDate;
    private long size;
    private String typeName;

    public FileEntity(String name, LocalDateTime modifiedDate, long size, String typeName) {
        this.id = name;
        this.name = name;
        this.modifiedDate = modifiedDate;
        this.size = size;
        this.typeName = typeName;
    }

    public FileEntity(String id, String name, LocalDateTime modifiedDate, long size, String typeName) {
        this(name, modifiedDate, size, typeName);
        this.id = id;
    }

    public String getId() { return id; }

    public String getName() {
        return name;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public long getSize() {
        return size;
    }

    public String getTypeName() {
        return typeName;
    }

}
