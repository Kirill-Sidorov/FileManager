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

public final class LocalDriveManager {

    private LocalDriveManager() {}

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
        long size = 0L;
        LocalDateTime modifiedDate = null;
        String typeName = null;
        try {
            name = path.getFileName().toString();
            size = Files.size(path);
            modifiedDate = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneOffset.systemDefault());
        } catch (IOException e) {
            throw new RuntimeException("Unable to create file information from path");
        }
        if (Files.isDirectory(path)) {
            typeName = BundleHolder.getBundle().getString("message.name.directory");
            size = -1L;
        } else {
            typeName = FilenameUtils.getExtension(path.toString());
        }
        return new FileEntity(path.toString(), name, modifiedDate, size, typeName);
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
        return new DriveEntity(name, DriveType.LOCAL);
    }

    public static Set<String> getRootDirectories() {
        return StreamSupport.stream(FileSystems.getDefault().getRootDirectories().spliterator(), false)
                .map(path -> path.toString())
                .collect(Collectors.toSet());
    }

    public static boolean isFileExist(Path path) { return path.toFile().exists(); }

    public static void executeFile(Path path) throws IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(path.toFile());
        }
    }

    public static Path getParentDirectory(Path path) { return path.getParent(); }
}
