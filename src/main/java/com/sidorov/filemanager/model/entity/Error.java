package com.sidorov.filemanager.model.entity;

import com.sidorov.filemanager.utility.BundleHolder;

public enum Error {
    NO {
        @Override
        public String getMessage() { return ""; }
    },
    FILE_NOT_FOUND_ERROR {
        @Override
        public String getMessage() { return BundleHolder.getBundle().getString("message.error.file_or_directory_does_not_exist"); }
    },
    FILE_NOT_RUN_ERROR {
        @Override
        public String getMessage() { return BundleHolder.getBundle().getString("message.error.failed_to_run_file"); }
    },
    FILE_NOT_UPLOAD_ERROR {
        @Override
        public String getMessage() { return BundleHolder.getBundle().getString("message.error.file_not_upload"); }
    },
    CLOUD_DRIVE_NOT_CONNECTED {
        @Override
        public String getMessage() { return BundleHolder.getBundle().getString("message.error.cloud_drive_not_connected"); }
    },
    CLOUD_DRIVE_DISABLED {
        @Override
        public String getMessage() { return BundleHolder.getBundle().getString("message.error.cloud_drive_disabled"); }
    },
    NO_CREDENTIALS {
        @Override
        public String getMessage() { return BundleHolder.getBundle().getString("message.error.no_credentials"); }
    };
    public abstract String getMessage();
}
