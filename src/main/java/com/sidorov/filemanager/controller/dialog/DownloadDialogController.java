package com.sidorov.filemanager.controller.dialog;

import com.sidorov.filemanager.controller.task.DownloadTask;
import com.sidorov.filemanager.model.entity.Error;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class DownloadDialogController {

    @FXML
    private TextArea informationTextArea;

    @FXML
    private ProgressBar fileUploadProgressBar;

    private Stage dialog;
    private DownloadTask task;

    public void setTask(Stage dialog, DownloadTask task) {
        this.dialog = dialog;
        this.task = task;
        fileUploadProgressBar.progressProperty().bind(task.progressProperty());
        informationTextArea.textProperty().bind(task.messageProperty());
        task.setOnSucceeded(event -> {
            Error error = task.getValue();
            if (error == Error.NO) {
                dialog.close();
            } else {

            }
        });
    }

}
