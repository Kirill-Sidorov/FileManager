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
    };
    public abstract String getMessage();
}