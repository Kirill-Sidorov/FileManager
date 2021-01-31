package com.sidorov.filemanager.utility;

import com.sidorov.filemanager.model.FileEntity;
import com.sidorov.filemanager.model.LocalFile;
import org.apache.commons.io.FilenameUtils;

import java.awt.*;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FileManager {

    private FileManager() {}

    public static void runFile(Path path) throws IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(path.toFile());
        }
    }

    public static long getFileSystemTotalSpace(Path path) throws IOException {
        return Files.getFileStore(path).getTotalSpace();
    }

    public static long getFileSystemUnallocatedSpace(Path path) throws IOException {
        return Files.getFileStore(path).getUnallocatedSpace();
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
        return new LocalFile(name, lastDate, size, type);
    }

    public static long getNumberFilesInDirectory(Path path) throws IOException {
        return Files.list(path).count();
    }

    public static List<String> getFileSystemRootDirectories() {
        return StreamSupport.stream(FileSystems.getDefault().getRootDirectories().spliterator(), false)
                .map(path -> path.toString())
                .collect(Collectors.toList());
    }
}
