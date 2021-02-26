package com.sidorov.filemanager.controller.dialog;

import com.sidorov.filemanager.utility.BundleHolder;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public final class ConfirmationDialogUtility {

    private ConfirmationDialogUtility() {}

    public static ButtonType showDownloadConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType cancelButton = new ButtonType(BundleHolder.getBundle().getString("ui.button.cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(ButtonType.OK, cancelButton);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/img/dialog-icon-confirmation.png"));
        alert.setGraphic(new ImageView("/img/dialog-icon-confirmation.png"));
        alert.setTitle(BundleHolder.getBundle().getString("message.title.confirmation"));
        alert.setHeaderText(BundleHolder.getBundle().getString("message.confirmation.download_file"));
        return alert.showAndWait().get();
    }
}
