package com.app.adherents.gestion_adherents.DataManip;

import com.app.adherents.gestion_adherents.Class.Adherent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/*TODO probleme d'encodage*/
/*TODO gestion id_club & clubs*/
/*TODO gestion taille des entrées text*/
/*TODO verif type entrée ex email tel etc...*/
/*TODO verif age & catégorie*/
/*TODO modif maj min nom prenom etc...*/
/*TODO reset formulaire liste*/
/*TODO RELIRE CONSIGNES*/
public class CSVExporter {
    public static void exporterAdherentsCSV() throws IOException {
        File selectedFile = choisirEmplacementFichierCSV();

        if (selectedFile != null) {

            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(selectedFile), StandardCharsets.UTF_8))) {

                List<String> headers = getHeaders();
                writer.println(String.join(";", headers));

                List<Adherent> adherents = XMLListing.listerAdherents(JSONReader.getJsonValue("adherent"));


                for (Adherent adherent : adherents) {
                    List<String> values = getAdherentData(adherent);
                    writer.println(String.join(";", values));
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    private static File choisirEmplacementFichierCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter en CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier CSV", "*.csv"));
        return fileChooser.showSaveDialog(new Stage());
    }

    private static List<String> getHeaders() {
        return Arrays.asList(
                "Nom",
                "Prenom",
                "Date de Naissance",
                "Genre",
                "Nom de Naissance",
                "Pays de Naissance",
                "Ville de Naissance",
                "Nationalite",
                "Adresse",
                "Code Postal",
                "Ville",
                "Tel1",
                "Tel2",
                "Email",
                "Pratique",
                "Lateralite",
                "Categorie",
                "Prenom du Responsable",
                "Nom du Responsable"
        );
    }

    private static List<String> getAdherentData(Adherent adherent) {
        return Arrays.asList(
                "\"" + adherent.getNom() + "\"",
                "\"" + adherent.getPrenom() + "\"",
                "\"" + adherent.getDateNaissance() + "\"",
                "\"" + adherent.getGenre() + "\"",
                "\"" + adherent.getNomDeNaissance() + "\"",
                "\"" + adherent.getPaysNaissance() + "\"",
                "\"" + adherent.getVilleNaissance() + "\"",
                "\"" + adherent.getNationalite() + "\"",
                "\"" + adherent.getAdresse() + "\"",
                "\"" + adherent.getCodePostal() + "\"",
                "\"" + adherent.getVille() + "\"",
                "\"" + adherent.getNumeroTelephone1() + "\"",
                "\"" + adherent.getNumeroTelephone2() + "\"",
                "\"" + adherent.getAdresseEmail() + "\"",
                "\"" + adherent.getPratique() + "\"",
                "\"" + adherent.getLateralite() + "\"",
                "\"" + adherent.getCategorie() + "\"",
                "\"" + adherent.getResponsableLegal().getPrenomResponsable() + "\"",
                "\"" + adherent.getResponsableLegal().getNomResponsable() + "\""
        );
    }
}
