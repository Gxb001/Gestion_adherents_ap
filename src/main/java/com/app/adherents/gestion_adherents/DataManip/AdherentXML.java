package com.app.adherents.gestion_adherents.DataManip;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class AdherentXML {

    public static void ajouterAdherent(
            String xmlFilePath,
            String nom,
            String prenom,
            String dateNaissance,
            String genre,
            String nomNaissance,
            String paysNaissance,
            String villeNaissance,
            String nationalite,
            String codePostal,
            String adresse,
            String ville,
            String numeroTelephone1,
            String numeroTelephone2,
            String adresseEmail,
            String pratique,
            String lateralite,
            int idClub,
            String categorie,
            List<String> armes,
            String nomResponsable,
            String prenomResponsable) {

        try {
            int prochainId = XMLFileManipulation.last_id(xmlFilePath, "adhérent") + 1;

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(new File(xmlFilePath));

            //Créer un nouvel élément adhérent
            Element nouvelAdherent = document.createElement("adhérent");
            nouvelAdherent.setAttribute("id", Integer.toString(prochainId));

            nouvelAdherent.appendChild(createElement(document, "Nom", nom));
            nouvelAdherent.appendChild(createElement(document, "Prenom", prenom));
            nouvelAdherent.appendChild(createElement(document, "DateNaissance", dateNaissance));
            nouvelAdherent.appendChild(createElement(document, "Genre", genre));
            nouvelAdherent.appendChild(createElement(document, "Nom_de_naissance", nomNaissance));
            nouvelAdherent.appendChild(createElement(document, "Pays_naissance", paysNaissance));
            nouvelAdherent.appendChild(createElement(document, "Ville_naissance", villeNaissance));
            nouvelAdherent.appendChild(createElement(document, "Nationalite", nationalite));
            nouvelAdherent.appendChild(createElement(document, "Code_postal", codePostal));
            nouvelAdherent.appendChild(createElement(document, "Adresse", adresse));
            nouvelAdherent.appendChild(createElement(document, "Ville", ville));
            nouvelAdherent.appendChild(createElement(document, "Numéro_de_telephone1", numeroTelephone1));
            nouvelAdherent.appendChild(createElement(document, "Numéro_de_telephone2", numeroTelephone2));
            nouvelAdherent.appendChild(createElement(document, "Adresse_email", adresseEmail));
            nouvelAdherent.appendChild(createElement(document, "Pratique", pratique));
            nouvelAdherent.appendChild(createElement(document, "Latéralité", lateralite));
            nouvelAdherent.appendChild(createElement(document, "id_du_club", Integer.toString(idClub)));
            nouvelAdherent.appendChild(createElement(document, "Catégorie", categorie));

            // Créer et ajouter les éléments Pratique_Escrime et ses sous-éléments
            Element pratiqueEscrime = document.createElement("Pratique_Escrime");
            for (String arme : armes) {
                pratiqueEscrime.appendChild(createElement(document, "Arme", arme));
            }
            nouvelAdherent.appendChild(pratiqueEscrime);

            // Créer et ajouter les éléments Responsable_Légal et ses sous-éléments
            Element responsableLegal = document.createElement("Responsable_Légal");
            responsableLegal.appendChild(createElement(document, "Nom_responsable", nomResponsable));
            responsableLegal.appendChild(createElement(document, "Prenom_responsable", prenomResponsable));
            nouvelAdherent.appendChild(responsableLegal);

            //Ajouter le nouvel adhérent à la racine des adhérents
            document.getDocumentElement().appendChild(nouvelAdherent);

            //Sauvegarder les modifications dans le fichier XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);
            FormatXML.formaterXML(xmlFilePath);
            System.out.println("Adhérent ajouté avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Element createElement(Document document, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.appendChild(document.createTextNode(textContent));
        return element;
    }

    public static void main(String[] args) {
        /*String xmlFilePath = JSONReader.getJsonValue("adherent");
        for (int i = 1; i <= 10; i++) {
            ajouterAdherent(
                    xmlFilePath,
                    "Nom" + i,
                    "Prenom" + i,
                    "200" + (i < 10 ? "0" : "") + i + "-01-01",
                    (i % 2 == 0) ? "Homme" : "Femme",
                    "Nom" + i,
                    "Pays" + i,
                    "Ville" + i,
                    "Nationalite" + i,
                    "7500" + i,
                    "Adresse" + i,
                    "Ville" + i,
                    "0123456789",
                    "9876543210",
                    "adherent" + i + "@example.com",
                    (i % 2 == 0) ? "Loisir" : "Compétition",
                    (i % 2 == 0) ? "Droite" : "Gauche",
                    i % 2 + 1,
                    "Catégorie" + i,
                    List.of("Épée", "Fleuret", "Sabre"),
                    "NomResponsable" + i,
                    "PrenomResponsable" + i
            );
        }*/
        //System.out.println(XMLFileManipulation.afficherXML(xmlFilePath, "adhérent", "5", "Nom"));
    }
}
