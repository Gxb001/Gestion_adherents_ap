package com.app.adherents.gestion_adherents;

import com.app.adherents.gestion_adherents.Class.Adherent;
import com.app.adherents.gestion_adherents.Class.Club;
import com.app.adherents.gestion_adherents.DataManip.CSVExporter;
import com.app.adherents.gestion_adherents.DataManip.JSONReader;
import com.app.adherents.gestion_adherents.DataManip.XMLFileManipulation;
import com.app.adherents.gestion_adherents.DataManip.XMLListing;
import javafx.animation.PauseTransition;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @FXML
    private ComboBox<String> lstclubs;
    private static int id_adherent;

    public void initialize() {
        String XMLPath_adherent = JSONReader.getJsonValue("adherent");
        String XMLPath_club = JSONReader.getJsonValue("club");
        try {
            List<Adherent> adherents = XMLListing.listerAdherents(XMLPath_adherent);
            adherentObservableList.addAll(adherents);
            //prendre l'id du club
            //parcourir la liste des adhérents
            //retirer chaque adhérents de la liste dont l'id du club est différent de l'id du club
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            int Nb_balise = XMLFileManipulation.last_id(XMLPath_club, "club");
            List<Club> clubs = new ArrayList<>();
            for (int i = 1; i < Nb_balise; i++) {
                String nom = XMLFileManipulation.afficherXML(XMLPath_club, "club", String.valueOf(i), "nom");
                String adresse = XMLFileManipulation.afficherXML(XMLPath_club, "club", String.valueOf(i), "adresse");
                String contact = XMLFileManipulation.afficherXML(XMLPath_club, "club", String.valueOf(i), "contact");
                String tel = XMLFileManipulation.afficherXML(XMLPath_club, "club", String.valueOf(i), "tel");
                String mail = XMLFileManipulation.afficherXML(XMLPath_club, "club", String.valueOf(i), "mail");
                String site = XMLFileManipulation.afficherXML(XMLPath_club, "club", String.valueOf(i), "site");
                //constructeur
                Club club = new Club(i, nom, adresse, contact, tel, mail, site);
                try {
                    clubs.add(club);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            for (Club club : clubs) {
                lstclubs.getItems().add(club.getNom() + " : " + club.getId());
            }//liste des clubs dans le combobox mtn faut trier la tableview
        } catch (Exception e) {
            e.printStackTrace();
        }

        nomadherent.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomadherent.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        genreadherent.setCellValueFactory(new PropertyValueFactory<>("genre"));
        emailadherent.setCellValueFactory(new PropertyValueFactory<>("adresseEmail"));
        categadherent.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        representantadherentnom.setCellValueFactory(cellData -> {
            Adherent adherent = cellData.getValue();
            String nomResponsable = "";
            try {
                if (adherent.getResponsableLegal() != null) {
                    nomResponsable = adherent.getResponsableLegal().getNomResponsable();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ReadOnlyStringWrapper(nomResponsable);
        });

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
                } else
                    return adherent.getResponsableLegal().getPrenomResponsable().toLowerCase().indexOf(lowerCaseFilter) > -1;
            });
        });
        SortedList<Adherent> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(adherenttable.comparatorProperty());
        adherenttable.setItems(sortedData);
    }

    public void refreshAdherents() {
        adherentObservableList.clear();
        String XMLPath_adherent = JSONReader.getJsonValue("adherent");
        try {
            // Chargez les adhérents à partir de votre fichier XML
            List<Adherent> adherents = XMLListing.listerAdherents(XMLPath_adherent);
            adherentObservableList.addAll(adherents);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mise à jour réussie");
            alert.setHeaderText(null);
            alert.setContentText("La liste des adhérents a été mise à jour avec succès !");
            PauseTransition delai = new PauseTransition(javafx.util.Duration.seconds(2));
            delai.setOnFinished(event -> alert.close());
            alert.show();
            delai.play();
        }
    }

    public void exporter() {
        if (!adherentObservableList.isEmpty()) {
            try {
                CSVExporter.exporterAdherentsCSV();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La liste des adhérents est vide !");
            alert.showAndWait();
        }
    }

    public void getAddView(MouseEvent mouseEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/com/app/adherents/gestion_adherents/addAdherents.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        stage.show();
    }

    public void supprimmerAdherent() {
        Adherent adherent = adherenttable.getSelectionModel().getSelectedItem();
        if (adherent != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Suppression");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Voulez-vous vraiment supprimer l'adhérent " + adherent.getNom() + " " + adherent.getPrenom() + " ?");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.get() == ButtonType.OK) {
                adherentObservableList.remove(adherent);
                String XMLPath_adherent = JSONReader.getJsonValue("adherent");
                try {
                    XMLFileManipulation.deleteAdherent(XMLPath_adherent, String.valueOf(adherent.getId()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert erreur = new Alert(Alert.AlertType.ERROR);
                erreur.setTitle("Erreur");
                erreur.setHeaderText(null);
                erreur.setContentText("Une erreur est survenue lors de la suppression de l'adhérent " + adherent.getNom() + " " + adherent.getPrenom() + " !");
                erreur.showAndWait();
            }
        } else {
            Alert errselect = new Alert(Alert.AlertType.ERROR);
            errselect.setTitle("Adhérent non selectionné");
            errselect.setHeaderText(null);
            errselect.setContentText("Merci de selectionner un adhérent à supprimer !");
            PauseTransition delai = new PauseTransition(javafx.util.Duration.seconds(2));
            delai.setOnFinished(event -> errselect.close());
            errselect.show();
            delai.play();

        }
    }

    public static String getIdAdherent() {
        return String.valueOf(id_adherent);
    }

    public void editAdherent() {
        Adherent adherent = adherenttable.getSelectionModel().getSelectedItem();
        if (adherent != null) {
            id_adherent = adherent.getId();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/adherents/gestion_adherents/editAdherents.fxml"));
                Parent parent = loader.load();
                AddController addController = loader.getController();
                addController.initDataAdherent(adherent);

                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert errselect = new Alert(Alert.AlertType.ERROR);
            errselect.setTitle("Adhérent non selectionné");
            errselect.setHeaderText(null);
            errselect.setContentText("Merci de selectionner un adhérent à modifier !");
            PauseTransition delai = new PauseTransition(javafx.util.Duration.seconds(2));
            delai.setOnFinished(event -> errselect.close());
            errselect.show();
            delai.play();

        }
    }
}
