package com.sidorov.filemanager.model.datagetter;

import com.sidorov.filemanager.controller.service.ProgressUpdater;
import com.sidorov.filemanager.model.entity.DriveSizeInfo;
import com.sidorov.filemanager.model.entity.FileEntity;
import com.sidorov.filemanager.utility.LocalDriveManager;

import java.util.List;

public class LocalDriveDataGetter implements DriveDataGettable {

    @Override
    public DriveSizeInfo getDriveSizeInfo(String driveName) {
        return LocalDriveManager.getFileSystemSizeInfo(driveName);
    }

    @Override
    public List<FileEntity> getListDirectoryFiles(String dirId, ProgressUpdater updater) {
        return LocalDriveManager.getListDirectoryFiles(dirId, updater);
    }
}
