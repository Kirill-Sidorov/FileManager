package com.sidorov.filemanager.dialog;

import com.sidorov.filemanager.utility.BundleHolder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DriveAdderDialog {

    private Stage stage;

    public DriveAdderDialog() throws IOException {
        stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/driveAdderDialog.fxml"), BundleHolder.getBundle());
        stage.setScene(new Scene(root));
    }

    public void show() { stage.show(); }
    public void close() { stage.close(); }
}
