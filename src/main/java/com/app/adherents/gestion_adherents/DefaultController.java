package com.app.adherents.gestion_adherents;

import com.app.adherents.gestion_adherents.Class.Adherent;
import com.app.adherents.gestion_adherents.Class.Club;
import com.app.adherents.gestion_adherents.DataManip.CSVExporter;
import com.app.adherents.gestion_adherents.DataManip.JSONReader;
import com.app.adherents.gestion_adherents.DataManip.XMLFileManipulation;
import com.app.adherents.gestion_adherents.DataManip.XMLListing;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DefaultController {
    static ObservableList<Adherent> adherentObservableList = FXCollections.observableArrayList();
    private static int id_adherent;
    private static String IdClubAdherent;
    private static String selectedClub;
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

    public static int getIdClubAdherent() {
        return Integer.parseInt(IdClubAdherent);
    }

    public static void miseajour() {
        adherentObservableList.clear();
        String XMLPath_adherent = JSONReader.getJsonValue("adherent");
        try {
            // Chargez les adhérents à partir de votre fichier XML
            List<Adherent> adherents = XMLListing.listerAdherents(XMLPath_adherent);
            if (selectedClub != null) {
                if (selectedClub.equals("Tous les clubs")) {
                    adherentObservableList.addAll(adherents);
                } else {
                    String clubId = selectedClub.split(" : ")[1];
                    IdClubAdherent = clubId;
                    for (Adherent adherent : adherents) {
                        if (Objects.equals(adherent.getIdClub(), clubId)) {
                            adherentObservableList.add(adherent);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getIdAdherent() {
        return String.valueOf(id_adherent);
    }

    public void initialize() {
        String XMLPath_adherent = JSONReader.getJsonValue("adherent");
        String XMLPath_club = JSONReader.getJsonValue("club");
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
            }
            lstclubs.getItems().add("Tous les clubs");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lstclubs.setOnAction(event -> {
                    List<Adherent> adherents = XMLListing.listerAdherents(XMLPath_adherent);
                    adherentObservableList.clear();
                    selectedClub = lstclubs.getValue();
                    if (selectedClub != null) {
                        if (selectedClub.equals("Tous les clubs")) {
                            adherentObservableList.addAll(adherents);
                        } else {
                            String clubId = selectedClub.split(" : ")[1];
                            IdClubAdherent = clubId;
                            for (Adherent adherent : adherents) {
                                if (Objects.equals(adherent.getIdClub(), clubId)) {
                                    adherentObservableList.add(adherent);
                                }
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                if (adherent.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (adherent.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (adherent.getResponsableLegal().getNomResponsable().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else
                    return adherent.getResponsableLegal().getPrenomResponsable().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Adherent> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(adherenttable.comparatorProperty());
        adherenttable.setItems(sortedData);
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

    public void refreshAdherents() {
        miseajour();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mise à jour réussie");
        alert.setHeaderText(null);
        alert.setContentText("La liste des adhérents a été mise à jour avec succès !");
        PauseTransition delai = new PauseTransition(javafx.util.Duration.seconds(2));
        delai.setOnFinished(event -> alert.close());
        alert.show();
        delai.play();
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

    public void getAddView(MouseEvent mouseEvent) throws IOException {
        try {
            if (selectedClub.equals("Tous les clubs")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Merci de selectionner un club !");
                alert.showAndWait();
            } else {
                Parent parent = FXMLLoader.load(getClass().getResource("/com/app/adherents/gestion_adherents/addAdherents.fxml"));
                ScrollPane scrollPane = new ScrollPane();
                scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

                scrollPane.setContent(parent);

                Scene scene = new Scene(scrollPane, 610, 400);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
                stage.show();

            }
        } catch (Exception RuntimeException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Merci de selectionner un club !");
            alert.showAndWait();
        }

    }

    public void pdfexport() throws FileNotFoundException {
        Adherent adherent = adherenttable.getSelectionModel().getSelectedItem();
        if (adherent != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
            File selectedFile = fileChooser.showSaveDialog(null);
            if (selectedFile != null) {
                try {
                    PdfDocument pdfDoc = new PdfDocument(new PdfWriter(selectedFile));
                    Document document = new Document(pdfDoc);
                    document.setMargins(36, 36, 36, 36);
                    Image logo = new Image(ImageDataFactory.create("src/main/java/com/app/adherents/gestion_adherents/medias/ligue.jpg"));
                    logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    document.add(logo);

                    document.add(new Paragraph("Nom : " + adherent.getNom()));
                    document.add(new Paragraph("Prénom : " + adherent.getPrenom()));
                    document.add(new Paragraph("Inscrit le " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

                    document.add(new Paragraph("Récapitulatif de la fiche adhérent").setFontSize(16).setBold().setTextAlignment(TextAlignment.CENTER).setMarginTop(20));
                    document.add(new Paragraph("Genre : " + adherent.getGenre())
                            .setFontSize(12));
                    document.add(new Paragraph("Date de naissance : " + adherent.getDateNaissance())
                            .setFontSize(12));
                    document.add(new Paragraph("Adresse Email : " + adherent.getAdresseEmail())
                            .setFontSize(12));
                    document.add(new Paragraph("Catégorie : " + adherent.getCategorie())
                            .setFontSize(12));
                    document.add(new Paragraph("Informations du responsable légal")
                            .setFontSize(14)
                            .setBold()
                            .setMarginTop(20));
                    document.add(new Paragraph("Nom du responsable : " + adherent.getResponsableLegal().getNomResponsable())
                            .setFontSize(12));
                    document.add(new Paragraph("Prénom du responsable : " + adherent.getResponsableLegal().getPrenomResponsable())
                            .setFontSize(12));
                    document.add(new Paragraph("Contacts")
                            .setFontSize(14)
                            .setBold()
                            .setMarginTop(20));
                    document.add(new Paragraph("Téléphone 1 : " + adherent.getNumeroTelephone1()))
                            .setFontSize(12);
                    String numero2 = adherent.getNumeroTelephone2();
                    if (numero2 == null) {
                        document.add(new Paragraph("Téléphone 2 : " + numero2).setFontSize(12));
                    } else {
                        document.add(new Paragraph("Téléphone 2 : Numéro non renseigné").setFontSize(12));
                    }
                    document.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert errselect = new Alert(Alert.AlertType.ERROR);
            errselect.setTitle("Adhérent non selectionné");
            errselect.setHeaderText(null);
            errselect.setContentText("Merci de selectionner un adhérent à exporter !");
            PauseTransition delai = new PauseTransition(javafx.util.Duration.seconds(2));
            delai.setOnFinished(event -> errselect.close());
            errselect.show();
            delai.play();
        }
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

                ScrollPane scrollPane = new ScrollPane();
                scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

                scrollPane.setContent(parent);

                Scene scene = new Scene(scrollPane, 610, 400);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
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
