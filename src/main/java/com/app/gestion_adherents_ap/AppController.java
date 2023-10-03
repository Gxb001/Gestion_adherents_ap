package com.app.gestion_adherents_ap;

import com.xml.gestion_adherents_ap.Adherent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class HelloController {
    @FXML
    private ListView<Adherent> listAdherents;

    public void initialize() {
        String xmlFilePath = "C:\\Users\\Gabriel\\IdeaProjects\\Gestion_adherents_ap\\src\\main\\java\\com\\xml\\gestion_adherents_ap\\club.xml"; // Remplacez par le chemin correct

        List<Adherent> adherents = XMLFileManipulation.listerAdherents(xmlFilePath);

        ObservableList<Adherent> observableList = FXCollections.observableArrayList(adherents);
        listAdherents.setItems(observableList);
    }

    // ...
}