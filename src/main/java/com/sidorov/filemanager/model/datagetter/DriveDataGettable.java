package com.sidorov.filemanager.model.datagetter;

import com.sidorov.filemanager.controller.service.ProgressUpdater;
import com.sidorov.filemanager.model.entity.DriveSizeInfo;
import com.sidorov.filemanager.model.entity.FileEntity;

import java.util.List;

public interface DriveDataGettable {
    DriveSizeInfo getDriveSizeInfo(final String driveName);
    List<FileEntity> getListDirectoryFiles(final String dirId, ProgressUpdater updater);
}
