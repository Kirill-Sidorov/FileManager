package com.sidorov.filemanager.drive.local;

import com.sidorov.filemanager.controller.service.ProgressUpdater;
import com.sidorov.filemanager.model.entity.*;
import com.sidorov.filemanager.model.entity.DriveEntity;
import com.sidorov.filemanager.model.entity.DriveType;
import com.sidorov.filemanager.model.result.*;
import com.sidorov.filemanager.model.result.Error;
import com.sidorov.filemanager.utility.BundleHolder;
import org.apache.commons.io.FilenameUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class LocalDriveManager {

    private LocalDriveManager() {}

    public static DataResult getListDirectoryFiles(final String path, final ProgressUpdater updater) {
        Error error;
        List<FileEntity> files = new ArrayList<>();
        File dir = new File(path);
        File[] listFiles = dir.listFiles();
        if (listFiles != null) {
            int workDone = 1;
            long max = listFiles.length;
            for (File file : listFiles) {
                files.add(getFileEntity(file.toPath()));
                updater.update(workDone++, max);
            }
            error = Error.NO;
        } else {
            error = Error.NOT_ALL_FILES_WERE_RETRIEVED;
        }
        long[] sizeInfo = getFileSystemSizeInfo(path);
        return new DataResult(files, sizeInfo[0], sizeInfo[1], error);
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

    public static DriveEntity getDriveEntityByName(final String name) {
        return new DriveEntity(name, DriveType.LOCAL);
    }

    public static Set<String> getRootDirectories() {
        return StreamSupport.stream(FileSystems.getDefault().getRootDirectories().spliterator(), false)
                .map(Path::toString)
                .collect(Collectors.toSet());
    }

    public static boolean isFileExist(final String path) { return Paths.get(path).toFile().exists(); }

    public static Result executeFile(final String path) {
        Result result;
        if (Desktop.isDesktopSupported()) {
            try {
                File file = Paths.get(path).toFile();
                if (file.exists()) {
                    Desktop.getDesktop().open(file);
                    result = new Result(Status.OK);
                } else {
                    result = new Result(Error.FILE_NOT_FOUND_ERROR);
                }
            } catch (IOException e) {
                result = new Result(Error.FILE_NOT_RUN_ERROR);
            }
        } else {
            result = new Result(Error.FILE_NOT_RUN_ERROR);
        }
        return result;
    }

    public static PathResult getParentDirectory(final String path) {
        PathResult result;
        Path dirPath = Paths.get(path);
        if (dirPath.toFile().exists()) {
            Path parentDir = dirPath.getParent();
            if (parentDir != null) {
                result = new PathResult(parentDir.toString(), parentDir.toString());
            } else {
                result = new PathResult(path, path);
            }
        } else {
            result = new PathResult(Error.FILE_NOT_FOUND_ERROR);
        }
        return result;
    }

    private static FileEntity getFileEntity(final Path path) {
        String name = null;
        long size = 0L;
        LocalDateTime modifiedDate = null;
        String typeName = null;
        try {
            name = path.getFileName().toString();
            size = Files.size(path);
            modifiedDate = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneOffset.systemDefault());
        } catch (IOException e) {
            size = 0L;
        }
        if (Files.isDirectory(path)) {
            typeName = BundleHolder.getBundle().getString("message.name.directory");
            size = -1L;
        } else {
            typeName = FilenameUtils.getExtension(path.toString());
        }
        return new FileEntity(path.toString(), name, modifiedDate, size, typeName);
    }

    private static long[] getFileSystemSizeInfo(final String path) {
        long totalSpace;
        long unallocatedSpace;
        Path fsPath = Paths.get(path);
        try {
            totalSpace = Files.getFileStore(fsPath).getTotalSpace();
            unallocatedSpace = Files.getFileStore(fsPath).getUnallocatedSpace();
        } catch (IOException e) {
            totalSpace = 0L;
            unallocatedSpace = 0L;
        }
        return new long[]{totalSpace, unallocatedSpace};
    }
}
