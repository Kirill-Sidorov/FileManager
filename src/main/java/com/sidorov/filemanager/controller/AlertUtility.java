package com.sidorov.filemanager.controller;

import com.sidorov.filemanager.utility.BundleHolder;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertUtility {

    private AlertUtility() {}

    public static void showErrorAlert(String text, ButtonType buttonType) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text, buttonType);
        alert.setHeaderText(BundleHolder.getBundle().getString("message.alert.error"));
        alert.setTitle(BundleHolder.getBundle().getString("message.alert.error"));
        alert.showAndWait();
    }

    public static void showWarningAlert(String text, ButtonType buttonType) {
        Alert alert = new Alert(Alert.AlertType.WARNING, text, buttonType);
        alert.setHeaderText(BundleHolder.getBundle().getString("message.alert.warning"));
        alert.setTitle(BundleHolder.getBundle().getString("message.alert.warning"));
        alert.showAndWait();
    }
}
