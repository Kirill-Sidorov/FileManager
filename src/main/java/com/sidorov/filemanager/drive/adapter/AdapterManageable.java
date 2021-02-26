package com.sidorov.filemanager.drive.adapter;

import com.sidorov.filemanager.controller.service.ProgressUpdater;
import com.sidorov.filemanager.model.result.DataResult;
import com.sidorov.filemanager.model.result.PathResult;
import com.sidorov.filemanager.model.result.Result;

public interface AdapterManageable {
    Result executeFile(final String id);
    PathResult getNextDirectory(final String dirId, final String readablePath);
    PathResult getPreviousDirectory(final String dirId, final String readablePath);
    DataResult getListFiles(final String dirId, final ProgressUpdater updater);
}
