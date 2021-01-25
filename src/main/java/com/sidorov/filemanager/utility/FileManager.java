package com.sidorov.filemanager.utility;

import com.sidorov.filemanager.model.FileInformation;

import java.awt.*;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public static List<FileInformation> getFilesInformationByDirectoryPath(Path path) throws IOException {
        return Files.list(path).map(FileInformation::new).collect(Collectors.toList());
    }

    public static List<String> getFileSystemRootDirectories() {
        return StreamSupport.stream(FileSystems.getDefault().getRootDirectories().spliterator(), false)
                .map(path -> path.toString())
                .collect(Collectors.toList());
    }
}
