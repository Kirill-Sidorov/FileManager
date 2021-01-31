package com.sidorov.filemanager;

import com.sidorov.filemanager.utility.BundleHolder;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Locale;

public class ApplicationRunner extends Application {

    public void start(Stage stage) throws  Exception {
        BundleHolder.setLocale(new Locale("ru", "RU"));
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScene.fxml"), BundleHolder.getBundle());
        Scene scene = new Scene(root);
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        stage.setTitle(BundleHolder.getBundle().getString("stage.name"));
        stage.getIcons().add(new Image("/img/app-icon.png"));
        stage.setScene(scene);
        showFadeTransition(stage);
        stage.show();
    }

    private static void showFadeTransition(Stage stage) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), stage.getScene().getRoot());
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
