package com.app.adherents.gestion_adherents;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseApp extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BaseApp.class.getResource("app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Gestion-Adherents");
        stage.setScene(scene);
        Image icon = new Image("file:D:\\Users\\Gabriel\\Desktop\\Gestion_adherents_ap\\src\\main\\java\\com\\app\\adherents\\gestion_adherents\\medias\\escrime.ico");
        stage.getIcons().add(icon);
        stage.setMaxWidth(900);
        stage.setMaxHeight(620);
        stage.setMinWidth(900);
        stage.setMinHeight(620);
        stage.show();
    }
}