package com.sidorov.filemanager.controller.dialog;

import com.sidorov.filemanager.controller.task.DownloadTask;
import com.sidorov.filemanager.utility.BundleHolder;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public final class DialogCreatorHelperUtility {

    private DialogCreatorHelperUtility() {}

    public static Dialog createDownloadDialog(final DownloadTask task, final String driveName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.NONE);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/img/dialog-icon-download.png"));
        alert.setGraphic(new ImageView("/img/dialog-icon-download.png"));
        alert.setTitle(BundleHolder.getBundle().getString("message.title.information.download"));
        alert.setHeaderText(String.format(BundleHolder.getBundle().getString("string.format.header.download_file"), driveName));

        ButtonType buttonTypeCancel = new ButtonType(BundleHolder.getBundle().getString("ui.button.cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeCancel);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(300);
        progressBar.progressProperty().bind(task.progressProperty());

        Label label = new Label();
        label.setPrefHeight(50);
        label.textProperty().bind(task.messageProperty());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(label, progressBar);
        alert.getDialogPane().setContent(vBox);

        return alert;
    }
}
