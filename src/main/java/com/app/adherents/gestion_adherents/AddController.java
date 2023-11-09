package com.app.adherents.gestion_adherents;

import com.app.adherents.gestion_adherents.Class.Adherent;
import com.app.adherents.gestion_adherents.DataManip.AdherentXML;
import com.app.adherents.gestion_adherents.DataManip.JSONReader;
import com.app.adherents.gestion_adherents.DataManip.XMLFileManipulation;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AddController {
    @FXML
    private TextField nomTextField;
    @FXML
    private Button ValidateButton;
    @FXML
    private Button CancelButton;
    @FXML
    private TextField prenomTextField;
    @FXML
    private DatePicker dateNaissancePicker;
    @FXML
    private ComboBox<String> genreComboBox;
    @FXML
    private TextField nomNaissanceTextField;
    @FXML
    private TextField paysNaissanceTextField;
    @FXML
    private TextField villeNaissanceTextField;
    @FXML
    private TextField nationaliteTextField;
    @FXML
    private TextField adresseTextField;
    @FXML
    private TextField codePostalTextField;
    @FXML
    private TextField villeTextField;
    @FXML
    private TextField tel1TextField;
    @FXML
    private TextField tel2TextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private ComboBox<String> lateraliteComboBox;
    @FXML
    private ComboBox<String> pratiqueComboBox;
    @FXML
    private ComboBox<String> categorieComboBox;
    @FXML
    private TextField prenomResponsableTextField;
    @FXML
    private TextField nomResponsableTextField;
    @FXML
    private CheckBox armefleurer;
    @FXML
    private CheckBox armeepee;
    @FXML
    private CheckBox armesabre;

    public void initialize() {
        // Initialisation des ComboBox
        genreComboBox.getItems().addAll("Homme", "Femme");
        pratiqueComboBox.getItems().addAll("Compétition", "Loisir");
        lateraliteComboBox.getItems().addAll("Droite", "Gauche");
        String xmlFilePath = JSONReader.getJsonValue("categorie");
        int categorieCount = XMLFileManipulation.last_id(xmlFilePath, "categorie");
        for (int i = 1; i <= categorieCount; i++) {
            String id = String.valueOf(i);
            String anneeMin = XMLFileManipulation.afficherXML(xmlFilePath, "categorie", id, "annee_min");
            String anneeMax = XMLFileManipulation.afficherXML(xmlFilePath, "categorie", id, "annee_max");
            String nomCat = XMLFileManipulation.afficherXML(xmlFilePath, "categorie", id, "nom");
            nomCat = nomCat + " (" + anneeMin + " - " + anneeMax + ")";
            categorieComboBox.getItems().add(nomCat);
        }
    }


    @FXML
    private void save() {
        if (checkempty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
        } else if (checkall()) {
            String XMLPath_adherent = JSONReader.getJsonValue("adherent");
            if (!XMLFileManipulation.comparerBalisesXML(XMLPath_adherent, "adherent", "Adresse_email", emailTextField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur Email");
                alert.setHeaderText("");
                alert.setContentText("Email deja utilisé !");
                alert.showAndWait();
            } else if (!XMLFileManipulation.comparerBalisesXML(XMLPath_adherent, "adherent", "Numéro_de_telephone1", tel1TextField.getText()) || !XMLFileManipulation.comparerBalisesXML(XMLPath_adherent, "adherent", "Numéro_de_telephone2", tel2TextField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur Téléphone");
                alert.setHeaderText("");
                alert.setContentText("Numéro de téléhpone deja utilisé !");
                alert.showAndWait();
            } else if (!XMLFileManipulation.comparerBalisesXML(XMLPath_adherent, "adherent", "Numéro_de_telephone2", tel2TextField.getText()) || !XMLFileManipulation.comparerBalisesXML(XMLPath_adherent, "adherent", "Numéro_de_telephone1", tel1TextField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur Téléphone");
                alert.setHeaderText("");
                alert.setContentText("Numéro de téléhpone deja utilisé !");
                alert.showAndWait();
            } else {
                try {
                    //recuperation des champs
                    String nom = nomTextField.getText();
                    String prenom = prenomTextField.getText();
                    String dateNaissance = dateNaissancePicker.getValue().toString();
                    String genre = genreComboBox.getValue();
                    String nomNaissance = nomNaissanceTextField.getText();
                    String paysNaissance = paysNaissanceTextField.getText();
                    String villeNaissance = villeNaissanceTextField.getText();
                    String nationalite = nationaliteTextField.getText();
                    String adresse = adresseTextField.getText();
                    String codePostal = codePostalTextField.getText();
                    String ville = villeTextField.getText();
                    String tel1 = tel1TextField.getText();
                    String tel2 = tel2TextField.getText();
                    if (tel2.isEmpty()) {
                        tel2 = "null";
                    }
                    String email = emailTextField.getText();
                    String pratique = pratiqueComboBox.getValue();
                    String lateralite = lateraliteComboBox.getValue();
                    String categorie = categorieComboBox.getValue();
                    String prenomResponsable = prenomResponsableTextField.getText();
                    String nomResponsable = nomResponsableTextField.getText();
                    //recuperation des armes
                    List<String> armesSelectionnees = new ArrayList<>();

                    if (armefleurer.isSelected()) {
                        armesSelectionnees.add("Fleuret");
                    }
                    if (armeepee.isSelected()) {
                        armesSelectionnees.add("Epée");
                    }
                    if (armesabre.isSelected()) {
                        armesSelectionnees.add("Sabre");
                    }
                    //changer l'id du club en fonction du club selectionné
                    try {
                        AdherentXML.ajouterAdherent(JSONReader.getJsonValue("adherent"), nom, prenom, dateNaissance, genre, nomNaissance, paysNaissance, villeNaissance, nationalite, codePostal, adresse, ville, tel1, tel2, email, pratique, lateralite, DefaultController.getIdClubAdherent(), categorie, armesSelectionnees, nomResponsable, prenomResponsable);
                        //affichage d'un message de confirmation
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText("Adhérent ajouté");
                        alert.setContentText("L'adhérent a bien été ajouté");
                        alert.showAndWait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Stage stage = (Stage) ValidateButton.getScene().getWindow();
                        stage.close();
                        DefaultController.miseajour();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void clean() {
        //vider tous les champs
        nomTextField.setText("");
        prenomTextField.setText("");
        dateNaissancePicker.setValue(null);
        genreComboBox.setValue(null);
        nomNaissanceTextField.setText("");
        paysNaissanceTextField.setText("");
        villeNaissanceTextField.setText("");
        nationaliteTextField.setText("");
        adresseTextField.setText("");
        codePostalTextField.setText("");
        villeTextField.setText("");
        tel1TextField.setText("");
        tel2TextField.setText("");
        emailTextField.setText("");
        pratiqueComboBox.setValue(null);
        lateraliteComboBox.setValue(null);
        categorieComboBox.setValue(null);
        prenomResponsableTextField.setText("");
        nomResponsableTextField.setText("");
        armefleurer.setSelected(false);
        armeepee.setSelected(false);
        armesabre.setSelected(false);

        nomTextField.setStyle("");
        prenomTextField.setStyle("");
        dateNaissancePicker.setStyle("");
        nomNaissanceTextField.setStyle("");
        paysNaissanceTextField.setStyle("");
        villeNaissanceTextField.setStyle("");
        nationaliteTextField.setStyle("");
        adresseTextField.setStyle("");
        codePostalTextField.setStyle("");
        villeTextField.setStyle("");
        tel1TextField.setStyle("");
        tel2TextField.setStyle("");
        emailTextField.setStyle("");
        prenomResponsableTextField.setStyle("");
        nomResponsableTextField.setStyle("");

    }

    @FXML
    private void close(MouseEvent mouseEvent) {
        //fermer la fenetre
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void update() {
        if (checkempty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
        } else if (checkall()) {
            try {
                //recuperation des champs
                String nom = nomTextField.getText();
                String prenom = prenomTextField.getText();
                String dateNaissance = dateNaissancePicker.getValue().toString();
                String genre = genreComboBox.getValue();
                String nomNaissance = nomNaissanceTextField.getText();
                String paysNaissance = paysNaissanceTextField.getText();
                String villeNaissance = villeNaissanceTextField.getText();
                String nationalite = nationaliteTextField.getText();
                String adresse = adresseTextField.getText();
                String codePostal = codePostalTextField.getText();
                String ville = villeTextField.getText();
                String tel1 = tel1TextField.getText();
                String tel2 = tel2TextField.getText();
                if (tel2.isEmpty()) {
                    tel2 = "null";/*gerer ça*/
                }
                String email = emailTextField.getText();
                String pratique = pratiqueComboBox.getValue();
                String lateralite = lateraliteComboBox.getValue();
                String categorie = categorieComboBox.getValue();
                String prenomResponsable = prenomResponsableTextField.getText();
                String nomResponsable = nomResponsableTextField.getText();
                //recuperation des armes
                List<String> armesSelectionnees = new ArrayList<>();

                if (armefleurer.isSelected()) {
                    armesSelectionnees.add("Fleuret");
                }
                if (armeepee.isSelected()) {
                    armesSelectionnees.add("Epée");
                }
                if (armesabre.isSelected()) {
                    armesSelectionnees.add("Sabre");
                }
                //changer l'id du club en fonction du club selectionné
                try {
                    String chemin = JSONReader.getJsonValue("adherent");
                    String adherent_id = DefaultController.getIdAdherent();
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Nom", nom);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Prenom", prenom);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "DateNaissance", dateNaissance);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Genre", genre);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Nom_de_naissance", nomNaissance);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Pays_naissance", paysNaissance);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Ville_naissance", villeNaissance);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Nationalite", nationalite);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Adresse", adresse);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Code_postal", codePostal);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Ville", ville);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Numéro_de_telephone1", tel1);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Numéro_de_telephone2", tel2);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Adresse_email", email);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Pratique", pratique);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Latéralité", lateralite);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Catégorie", categorie);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Nom_responsable", nomResponsable);
                    XMLFileManipulation.modifierBalise(chemin, "adhérent", adherent_id, "Prenom_responsable", prenomResponsable);
                    XMLFileManipulation.modifierGroupeBalises(chemin, "adhérent", adherent_id, "Pratique_Escrime", armesSelectionnees);
                    //affichage d'un message de confirmation
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("");
                    alert.setContentText("L'adhérent a bien été modifié");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Stage stage = (Stage) ValidateButton.getScene().getWindow();
                    stage.close();
                    DefaultController defaultController = new DefaultController();
                    defaultController.refreshAdherents();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public boolean checkempty() {
        return nomTextField.getText().isEmpty() || prenomTextField.getText().isEmpty() || dateNaissancePicker.getValue() == null || genreComboBox.getValue() == null || nomNaissanceTextField.getText().isEmpty() || paysNaissanceTextField.getText().isEmpty() || villeNaissanceTextField.getText().isEmpty() || nationaliteTextField.getText().isEmpty() || adresseTextField.getText().isEmpty() || codePostalTextField.getText().isEmpty() || villeTextField.getText().isEmpty() || tel1TextField.getText().isEmpty() || emailTextField.getText().isEmpty() || pratiqueComboBox.getValue() == null || lateraliteComboBox.getValue() == null || categorieComboBox.getValue() == null || prenomResponsableTextField.getText().isEmpty() || nomResponsableTextField.getText().isEmpty() || (!armefleurer.isSelected() && !armeepee.isSelected() && !armesabre.isSelected());
    }

    public boolean checkall() {
        List<TextField> invalidFields = new ArrayList<>();

        if (!verifierEmail()) invalidFields.add(emailTextField);
        if (!verifnom()) invalidFields.add(nomTextField);
        if (!verifprenom()) invalidFields.add(prenomTextField);
        if (!verifnomnaissance()) invalidFields.add(nomNaissanceTextField);
        if (!verifpaynaissance()) invalidFields.add(paysNaissanceTextField);
        if (!verifvillenaissance()) invalidFields.add(villeNaissanceTextField);
        if (!verifnationalite()) invalidFields.add(nationaliteTextField);
        if (!verifadresse()) invalidFields.add(adresseTextField);
        if (!verifcodepostal()) invalidFields.add(codePostalTextField);
        if (!verifville()) invalidFields.add(villeTextField);
        if (!veriftel1()) invalidFields.add(tel1TextField);
        if (!veriftel2()) invalidFields.add(tel2TextField);
        if (!verifnomresponsalbe()) invalidFields.add(nomResponsableTextField);
        if (!verifprenomresponsable()) invalidFields.add(prenomResponsableTextField);

        if (invalidFields.isEmpty()) {
            return true;
        } else {
            for (TextField field : invalidFields) {
                field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText("Les éléments en rouge ne sont pas conformes !");
            alert.showAndWait();
            return false;
        }
    }


    private boolean verifChamp(String regex, TextField textField) {
        boolean isMatch = textField.getText().matches(regex);
        if (!isMatch) {
            textField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        }
        textField.setStyle("");
        return isMatch;
    }

    public void initDataAdherent(Adherent adherent) {
        if (!Objects.equals(adherent.getNumeroTelephone2(), "null")) {
            tel2TextField.setText(adherent.getNumeroTelephone2());
        } else {
            tel2TextField.setText("");
        }
        nomTextField.setText(adherent.getNom());
        prenomTextField.setText(adherent.getPrenom());
        dateNaissancePicker.setValue(adherent.getDateNaissance());
        genreComboBox.setValue(adherent.getGenre());
        nomNaissanceTextField.setText(adherent.getNomDeNaissance());
        paysNaissanceTextField.setText(adherent.getPaysNaissance());
        villeNaissanceTextField.setText(adherent.getVilleNaissance());
        nationaliteTextField.setText(adherent.getNationalite());
        adresseTextField.setText(adherent.getAdresse());
        codePostalTextField.setText(adherent.getCodePostal());
        villeTextField.setText(adherent.getVille());
        tel1TextField.setText(adherent.getNumeroTelephone1());
        emailTextField.setText(adherent.getAdresseEmail());
        pratiqueComboBox.setValue(adherent.getPratique());
        lateraliteComboBox.setValue(adherent.getLateralite());
        categorieComboBox.setValue(adherent.getCategorie());
        prenomResponsableTextField.setText(adherent.getResponsableLegal().getPrenomResponsable());
        nomResponsableTextField.setText(adherent.getResponsableLegal().getNomResponsable());
        List<String> armes = adherent.getPratiqueEscrimeArmes();
        for (String arme : armes) {
            if (arme.equals("Fleuret")) {
                armefleurer.setSelected(true);
            } else if (arme.equals("Epée")) {
                armeepee.setSelected(true);
            } else if (arme.equals("Sabre")) {
                armesabre.setSelected(true);
            }
        }
    }

    private boolean verifierEmail() {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return verifChamp(regex, emailTextField);
    }

    private boolean verifnom() {
        String regex = "^[a-zA-Z]+$";
        return verifChamp(regex, nomTextField);
    }

    private boolean verifprenom() {
        String regex = "^[a-zA-Z]+$";
        return verifChamp(regex, prenomTextField);
    }

    private boolean verifnomnaissance() {
        String regex = "^[a-zA-Z]+$";
        return verifChamp(regex, nomNaissanceTextField);
    }

    private boolean verifpaynaissance() {
        String regex = "^[a-zA-Z]+$";
        return verifChamp(regex, paysNaissanceTextField);
    }

    private boolean verifvillenaissance() {
        String regex = "^[a-zA-Z]+$";
        return verifChamp(regex, villeNaissanceTextField);
    }

    private boolean verifnationalite() {
        String regex = "^[a-zA-Z]+$";
        return verifChamp(regex, nationaliteTextField);
    }

    private boolean verifadresse() {
        String regex = "^[a-zA-Z0-9 ]+$";
        return verifChamp(regex, adresseTextField);
    }

    private boolean verifcodepostal() {
        String regex = "^[0-9]+$";
        return verifChamp(regex, codePostalTextField);
    }

    private boolean verifville() {
        String regex = "^[a-zA-Z]+$";
        return verifChamp(regex, villeTextField);
    }

    private boolean veriftel1() {
        String regex = "^[0-9]+$";
        boolean tel1Valid = verifChamp(regex, tel1TextField);
        return tel1Valid;
    }

    private boolean veriftel2() {
        String regex = "^[0-9]+$";
        if (tel2TextField.getText().isEmpty()) {
            return true;
        }
        boolean tel2Valid = verifChamp(regex, tel2TextField);
        return tel2Valid;
    }

    private boolean verifnomresponsalbe() {
        String regex = "^[a-zA-Z]+$";
        return verifChamp(regex, nomResponsableTextField);
    }

    private boolean verifprenomresponsable() {
        String regex = "^[a-zA-Z]+$";
        return verifChamp(regex, prenomResponsableTextField);
    }

}
