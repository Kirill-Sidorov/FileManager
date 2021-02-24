package com.sidorov.filemanager.model.datagetter;

import com.google.api.services.drive.model.File;
import com.sidorov.filemanager.cloud.googledrive.GoogleDriveManager;
import com.sidorov.filemanager.controller.service.ProgressUpdater;
import com.sidorov.filemanager.model.entity.DriveSizeInfo;
import com.sidorov.filemanager.model.entity.FileEntity;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class GoogleDriveDataGetter implements DriveDataGettable {

    @Override
    public DriveSizeInfo getDriveSizeInfo(String driveName) {
        return GoogleDriveManager.getDriveSizeInfo();
    }

    @Override
    public List<FileEntity> getListDirectoryFiles(String dirId, ProgressUpdater updater) {
        List<FileEntity> list = null;
        try {
            list = GoogleDriveManager.getListDirectoryFiles(dirId, updater);
        } catch (IOException e) {
            list = null;
        } catch (InterruptedException e) {
            list = null;
        }
        return list;
    }
}
