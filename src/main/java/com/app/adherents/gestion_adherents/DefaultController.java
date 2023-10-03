package com.app.adherents.gestion_adherents;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DefaultController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}