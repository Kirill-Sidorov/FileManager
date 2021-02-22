package com.sidorov.filemanager.controller.utility;

import com.sidorov.filemanager.utility.BundleHolder;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public final class AlertUtility {

    private AlertUtility() {}

    public static void showErrorAlert(String text, ButtonType buttonType) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text, buttonType);
        alert.setHeaderText(text);
        alert.setTitle(BundleHolder.getBundle().getString("message.title.error"));
        alert.showAndWait();
    }

    public static void showWarningAlert(String text, ButtonType buttonType) {
        Alert alert = new Alert(Alert.AlertType.WARNING, text, buttonType);
        alert.setHeaderText(text);
        alert.setTitle(BundleHolder.getBundle().getString("message.title.warning"));
        alert.showAndWait();
    }
}
