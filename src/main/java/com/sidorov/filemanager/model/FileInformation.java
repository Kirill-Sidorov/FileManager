package com.sidorov.filemanager.model;

import com.sidorov.filemanager.utility.BundleHolder;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FileInformation {

    private String name;
    private long size;
    private LocalDateTime date;
    private String fileTypeString;
    private FileType type;

    public FileInformation(Path path) {
        try {
            this.name = path.getFileName().toString();
            this.size = Files.size(path);
            this.date = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneOffset.systemDefault());
        } catch (IOException e) {
            throw new RuntimeException("Unable to create file information from path");
        }

        if (Files.isDirectory(path)) {
            this.type = FileType.DIRECTORY;
            this.fileTypeString = BundleHolder.getBundle().getString("message.name.directory");
        } else {
            this.type = FileType.FILE;
            this.fileTypeString = FilenameUtils.getExtension(path.toString());
        }
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getFileTypeString() { return fileTypeString; }

    public FileType getType() {
        return type;
    }

}
