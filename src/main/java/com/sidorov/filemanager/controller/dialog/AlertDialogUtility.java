package com.sidorov.filemanager.controller.dialog;

import com.sidorov.filemanager.utility.BundleHolder;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public final class AlertDialogUtility {

    private AlertDialogUtility() {}

    public static void showErrorAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        setAlertErrorIcon(alert);
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

    public static void showErrorWithTextArea(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        setAlertErrorIcon(alert);
        alert.setTitle(BundleHolder.getBundle().getString("message.title.error"));
        alert.setHeaderText(BundleHolder.getBundle().getString("message.title.error"));
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setText(text);
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    private static void setAlertErrorIcon(Alert alert) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/img/alert-icon-error.png"));
        alert.setGraphic(new ImageView("/img/alert-icon-error.png"));
    }
}
