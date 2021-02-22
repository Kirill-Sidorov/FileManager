package com.sidorov.filemanager.controller.utility;

import com.sidorov.filemanager.utility.BundleHolder;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public final class InformationUtility {
    private InformationUtility() {}

    public static void showInformation(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/img/alert-icon-information.png"));
        alert.setGraphic(new ImageView("/img/alert-icon-information.png"));
        alert.setHeaderText(text);
        alert.setTitle(BundleHolder.getBundle().getString("message.title.information"));
        alert.showAndWait();
    }
}
