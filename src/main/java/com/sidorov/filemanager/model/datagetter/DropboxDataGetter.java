package com.sidorov.filemanager.model.datagetter;

import com.sidorov.filemanager.controller.service.ProgressUpdater;
import com.sidorov.filemanager.model.entity.DriveSizeInfo;
import com.sidorov.filemanager.model.entity.FileEntity;

import java.util.List;

public class DropboxDataGetter implements DriveDataGettable {
    @Override
    public DriveSizeInfo getDriveSizeInfo(String driveName) {
        return null;
    }

    @Override
    public List<FileEntity> getListDirectoryFiles(String dirId, ProgressUpdater updater) {
        return null;
    }
}
