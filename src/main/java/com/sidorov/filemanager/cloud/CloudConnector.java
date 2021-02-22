package com.sidorov.filemanager.cloud;

import com.sidorov.filemanager.cloud.googledrive.GDriveAuthorizationUtility;
import com.sidorov.filemanager.model.MappedDriveManager;
import com.sidorov.filemanager.model.entity.DriveType;
import com.sidorov.filemanager.model.entity.Error;
import javafx.concurrent.Task;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CloudConnector extends Task<Map<DriveType, Error>>  {

    @Override
    protected Map<DriveType, Error> call() throws Exception {
        Map<DriveType, Error>  drivesErrors = new HashMap<DriveType, Error>();
        File directory = new File("tokens");
        if (directory.isDirectory() && directory.list().length != 0) {
            Error error = GDriveAuthorizationUtility.createDrive();
            drivesErrors.put(DriveType.GOOGLE, error);
        }
        return drivesErrors;
    }
}
