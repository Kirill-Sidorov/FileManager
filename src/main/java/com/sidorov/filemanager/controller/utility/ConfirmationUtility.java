package com.sidorov.filemanager.controller.utility;

import com.sidorov.filemanager.utility.BundleHolder;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public final class ConfirmationUtility {

    private ConfirmationUtility() {}

    public static ButtonType showDownloadConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(BundleHolder.getBundle().getString("message.confirmation.download_file"));
        return alert.showAndWait().get();
    }
}
