package com.sidorov.filemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class ApplicationRunner extends Application {

    public void start(Stage stage) throws  Exception {
        Locale locale = new Locale("ru", "RU");
        ResourceBundle bundle = ResourceBundle.getBundle("bundle.strings", locale);
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScene.fxml"), bundle);
        Scene scene = new Scene(root);

        stage.setTitle(bundle.getString("stage.name"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
