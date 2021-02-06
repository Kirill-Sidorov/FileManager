package com.sidorov.filemanager.utility;

import com.sidorov.filemanager.model.entity.*;
import org.apache.commons.io.FilenameUtils;

import java.awt.*;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class LocalDriveManager {

    private LocalDriveManager() {}

    public static void runFile(Path path) throws IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(path.toFile());
        }
    }

    public static DriveSizeInfo getFileSystemSizeInfo(Path path) {
        long totalSpace;
        long unallocatedSpace;
        try {
            totalSpace = Files.getFileStore(path).getTotalSpace();
            unallocatedSpace = Files.getFileStore(path).getUnallocatedSpace();
        } catch (IOException e) {
            totalSpace = 0L;
            unallocatedSpace = 0L;
        }
        return new DriveSizeInfo(totalSpace, unallocatedSpace);
    }

    public static FileEntity getFileEntity(Path path) {
        String name = null;
        long size = -1L;
        LocalDateTime lastDate = null;
        String type = null;
        try {
            name = path.getFileName().toString();
            size = Files.size(path);
            lastDate = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneOffset.systemDefault());
        } catch (IOException e) {
            throw new RuntimeException("Unable to create file information from path");
        }
        if (Files.isDirectory(path)) {
            type = BundleHolder.getBundle().getString("message.name.directory");
            size = -1L;
        } else {
            type = FilenameUtils.getExtension(path.toString());
        }
        return new FileEntity(name, lastDate, size, type);
    }

    /*
    public static long getNumberFilesInDirectory(Path path) {
        long numberFiles;
        try {
            numberFiles = Files.list(path).count();
        } catch (IOException e) {
            numberFiles = 0L;
        }
        return numberFiles;
    }
    */

    public static DriveEntity getDriveEntityByName(String name) {
        return new DriveEntity(name, Disk.LOCAL);
    }

    public static Set<String> getRootDirectories() {
        return StreamSupport.stream(FileSystems.getDefault().getRootDirectories().spliterator(), false)
                .map(path -> path.toString())
                .collect(Collectors.toSet());
    }
}
