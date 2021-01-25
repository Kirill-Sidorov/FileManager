package com.sidorov.filemanager.model;

import com.sidorov.filemanager.utility.BundleHolder;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FileInformation {

    private Path path;
    private String name;
    private long size;
    private LocalDateTime date;
    private String type;

    public FileInformation(Path path) {
        try {
            this.path = path;
            this.name = path.getFileName().toString();
            this.size = Files.size(path);
            this.date = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneOffset.systemDefault());
        } catch (IOException e) {
            throw new RuntimeException("Unable to create file information from path");
        }

        if (Files.isDirectory(path)) {
            this.type = BundleHolder.getBundle().getString("message.name.directory");
            this.size = -1L;
        } else {
            this.type = FilenameUtils.getExtension(path.toString());
        }
    }

    public Path getPath() { return path; }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getType() {
        return type;
    }
}
