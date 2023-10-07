package com.app.adherents.gestion_adherents;

import com.app.adherents.gestion_adherents.DataManip.JSONReader;
import com.app.adherents.gestion_adherents.DataManip.XMLFileManipulation;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    private Button closebtn;

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
        //verification des champs remplis
        if (nomTextField.getText().isEmpty() || prenomTextField.getText().isEmpty() || dateNaissancePicker.getValue() == null || genreComboBox.getValue() == null || nomNaissanceTextField.getText().isEmpty() || paysNaissanceTextField.getText().isEmpty() || villeNaissanceTextField.getText().isEmpty() || nationaliteTextField.getText().isEmpty() || adresseTextField.getText().isEmpty() || codePostalTextField.getText().isEmpty() || villeTextField.getText().isEmpty() || tel1TextField.getText().isEmpty() || emailTextField.getText().isEmpty() || pratiqueComboBox.getValue() == null || lateraliteComboBox.getValue() == null || categorieComboBox.getValue() == null || prenomResponsableTextField.getText().isEmpty() || nomResponsableTextField.getText().isEmpty()) {
            //affichage d'un message d'erreur
            System.out.println("Veuillez remplir tous les champs");
        } else {
            String XMLPath_adherent = JSONReader.getJsonValue("adherent");
            if (!XMLFileManipulation.comparerBalisesXML(XMLPath_adherent, "adherent", "Adresse_email", emailTextField.getText())) {
                //faire pop up d'erreur
            } else if (!XMLFileManipulation.comparerBalisesXML(XMLPath_adherent, "adherent", "Numéro_de_telephone1", tel1TextField.getText())) {
                //faire pop up d'erreur
            } else if (!XMLFileManipulation.comparerBalisesXML(XMLPath_adherent, "adherent", "Numéro_de_telephone2", tel2TextField.getText())) {
                //faire pop up d'erreur
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
                    String email = emailTextField.getText();
                    String pratique = pratiqueComboBox.getValue();
                    String lateralite = lateraliteComboBox.getValue();
                    String categorie = categorieComboBox.getValue();
                    String prenomResponsable = prenomResponsableTextField.getText();
                    String nomResponsable = nomResponsableTextField.getText();
                    //faire le code pour ajouter un adhérent
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
    }

    public void close(MouseEvent mouseEvent) {
        //fermer la fenetre
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
