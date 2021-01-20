package com.sidorov.filemanager;

import com.sidorov.filemanager.utility.BundleHolder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Locale;

public class ApplicationRunner extends Application {

    public void start(Stage stage) throws  Exception {
        BundleHolder.setLocale(new Locale("ru", "RU"));
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScene.fxml"), BundleHolder.getBundle());
        Scene scene = new Scene(root);

        stage.setTitle(BundleHolder.getBundle().getString("stage.name"));
        stage.getIcons().add(new Image("/img/app-icon.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
