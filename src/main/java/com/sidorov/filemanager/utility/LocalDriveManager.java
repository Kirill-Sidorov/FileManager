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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class LocalDriveManager {

    private LocalDriveManager() {}

    public static void runFile(Path path) throws IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(path.toFile());
        }
    }

    public static long getFileSystemTotalSpace(Path path) {
        long totalSpace;
        try {
            totalSpace = Files.getFileStore(path).getTotalSpace();
        } catch (IOException e) {
            totalSpace = 0L;
        }
        return totalSpace;
    }

    public static long getFileSystemUnallocatedSpace(Path path) {
        long unallocatedSpace;
        try {
            unallocatedSpace = Files.getFileStore(path).getUnallocatedSpace();
        } catch (IOException e) {
            unallocatedSpace = 0L;
        }
        return unallocatedSpace;
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

    public static long getNumberFilesInDirectory(Path path) {
        long numberFiles;
        try {
            numberFiles = Files.list(path).count();
        } catch (IOException e) {
            numberFiles = 0L;
        }
        return numberFiles;
    }

    public static List<DriveEntity> getLocalDrives() {
        List<DriveEntity> drives = new ArrayList<DriveEntity>();
        for (Path path : FileSystems.getDefault().getRootDirectories()) {
            drives.add(new DriveEntity(path.toString(), Disk.LOCAL));
        }
        return drives;
    }

    public static List<String> getRootDirectories() {
        return StreamSupport.stream(FileSystems.getDefault().getRootDirectories().spliterator(), false)
                .map(path -> path.toString())
                .collect(Collectors.toList());
    }
}
