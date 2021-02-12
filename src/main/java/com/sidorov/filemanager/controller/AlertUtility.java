package com.sidorov.filemanager.controller;

import com.sidorov.filemanager.utility.BundleHolder;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;

public class AlertUtility {

    private AlertUtility() {}

    public static void showErrorAlert(String text, ButtonType buttonType) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text, buttonType);
        alert.setContentText(text);
        alert.setHeaderText(BundleHolder.getBundle().getString("message.alert.error"));
        alert.setTitle(BundleHolder.getBundle().getString("message.alert.error"));
        alert.showAndWait();
    }

    public static void showWarningAlert(String text, ButtonType buttonType) {
        Alert alert = new Alert(Alert.AlertType.WARNING, text, buttonType);
        alert.setHeaderText(text);
        alert.setTitle(BundleHolder.getBundle().getString("message.alert.warning"));
        alert.showAndWait();
    }
}
