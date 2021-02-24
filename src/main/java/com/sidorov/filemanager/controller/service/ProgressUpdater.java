package com.sidorov.filemanager.controller.service;

@FunctionalInterface
public interface ProgressUpdater {
    void update(long workDone, long max);
}
