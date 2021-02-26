package com.sidorov.filemanager.cloud;

import com.sidorov.filemanager.model.result.Error;
import javafx.concurrent.Task;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CloudConnector extends Task<Map<CloudDriveType, Error>>  {

    @Override
    protected Map<CloudDriveType, Error> call() throws Exception {
        Map<CloudDriveType, Error> drivesErrors = new HashMap<>();
        for (CloudDriveType cloudDrive : CloudDriveType.values()) {
            File directory = new File(cloudDrive.getTokenDirectoryPath());
            if (directory.isDirectory() && directory.list().length != 0) {
                Error error = cloudDrive.createDrive();
                drivesErrors.put(cloudDrive, error);
            }
        }
        return drivesErrors;
    }
}
