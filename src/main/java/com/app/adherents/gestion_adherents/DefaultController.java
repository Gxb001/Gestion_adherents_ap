package com.app.adherents.gestion_adherents;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class DefaultController {
    static ObservableList<Adherent> adherentObservableList = FXCollections.observableArrayList();
    @FXML
    private TableView<Adherent> adherenttable;
    @FXML
    private TableColumn<Adherent, String> nomadherent;
    @FXML
    private TableColumn<Adherent, String> prenomadherent;
    @FXML
    private TableColumn<Adherent, String> genreadherent;
    @FXML
    private TableColumn<Adherent, String> emailadherent;
    @FXML
    private TableColumn<Adherent, String> categadherent;
    @FXML
    private TableColumn<Adherent, String> representantadherentnom;
    @FXML
    private TableColumn<Adherent, String> representantadherentprenom;
    @FXML
    private TextField keywordstextfield;

    public void initialize() {
        try {
            // Chargez les adhérents à partir de votre fichier XML
            List<Adherent> adherents = XMLListing.listerAdherents("D:\\Users\\Gabriel\\Desktop\\Nouveau dossier\\Cours\\BTS Seconde Année\\AP\\Mission_1\\Gestion_adherents\\src\\main\\java\\com\\app\\adherents\\gestion_adherents\\adherent.xml");
            adherentObservableList.addAll(adherents);
        } catch (Exception e) {
            e.printStackTrace();
            // Gérez l'exception ici (affichage d'un message d'erreur par exemple)
        }

        // Assurez-vous que les colonnes sont associées aux propriétés appropriées de la classe Adherent
        nomadherent.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomadherent.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        genreadherent.setCellValueFactory(new PropertyValueFactory<>("genre"));
        emailadherent.setCellValueFactory(new PropertyValueFactory<>("adresseEmail"));
        categadherent.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        // Associez la colonne "representantadherentnom" à la propriété "nomResponsable" de la classe Adherent
        representantadherentnom.setCellValueFactory(cellData -> {
            Adherent adherent = cellData.getValue();
            String nomResponsable = "";
            try {
                if (adherent.getResponsableLegal() != null) {
                    nomResponsable = adherent.getResponsableLegal().getNomResponsable();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Gérez l'exception ici (affichage d'un message d'erreur par exemple)
            }
            return new ReadOnlyStringWrapper(nomResponsable);
        });

        // Associez la colonne "representantadherentprenom" à la propriété "prenomResponsable" de la classe Adherent
        representantadherentprenom.setCellValueFactory(cellData -> {
            Adherent adherent = cellData.getValue();
            String prenomResponsable = "";
            try {
                if (adherent.getResponsableLegal() != null) {
                    prenomResponsable = adherent.getResponsableLegal().getPrenomResponsable();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Gérez l'exception ici (affichage d'un message d'erreur par exemple)
            }
            return new ReadOnlyStringWrapper(prenomResponsable);
        });

        // Associez la liste observable à la TableView
        adherenttable.setItems(adherentObservableList);
        FilteredList<Adherent> filteredData = new FilteredList<>(adherentObservableList, p -> true);
        keywordstextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(adherent -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (adherent.getNom().toLowerCase().indexOf(lowerCaseFilter) > -1) {
                    return true;
                } else if (adherent.getPrenom().toLowerCase().indexOf(lowerCaseFilter) > -1) {
                    return true;
                } else if (adherent.getResponsableLegal().getNomResponsable().toLowerCase().indexOf(lowerCaseFilter) > -1) {
                    return true;
                } else return adherent.getResponsableLegal().getPrenomResponsable().toLowerCase().indexOf(lowerCaseFilter) > -1;
            });
        });
        SortedList<Adherent> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(adherenttable.comparatorProperty());

        adherenttable.setItems(sortedData);

    }
}
