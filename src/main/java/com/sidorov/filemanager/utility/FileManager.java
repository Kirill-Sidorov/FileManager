package com.sidorov.filemanager.utility;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

public class FileManager {

    private FileManager() {}

    public static void runFile(Path path) {
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            try {
                desktop.open(path.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
