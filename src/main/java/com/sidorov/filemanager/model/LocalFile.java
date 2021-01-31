package com.sidorov.filemanager.model;

import java.time.LocalDateTime;

public class LocalFile extends FileEntity {
    public LocalFile(String name, LocalDateTime lastDate, long size, String type) {
        setName(name);
        setLastDate(lastDate);
        setSize(size);
        setType(type);
    }
}
