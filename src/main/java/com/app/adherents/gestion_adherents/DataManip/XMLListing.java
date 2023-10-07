package com.app.adherents.gestion_adherents.DataManip;

import com.app.adherents.gestion_adherents.Class.Adherent;
import com.app.adherents.gestion_adherents.Class.ResponsableLegal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLListing {
    public static List<Adherent> listerAdherents(String xmlFilePath) {
        List<Adherent> adherents = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File xmlFile = new File(xmlFilePath);

            if (!xmlFile.exists()) {
                // Gérer le cas où le fichier XML n'existe pas
                return adherents;
            }

            Document document = builder.parse(xmlFile);
            NodeList nodeList = document.getElementsByTagName("adhérent");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                int id = Integer.parseInt(element.getAttribute("id"));
                String nom = getElementTextContent(element, "Nom");
                String prenom = getElementTextContent(element, "Prenom");
                String dateNaissance = getElementTextContent(element, "DateNaissance");
                String genre = getElementTextContent(element, "Genre");
                String nomDeNaissance = getElementTextContent(element, "Nom_de_naissance");
                String paysNaissance = getElementTextContent(element, "Pays_naissance");
                String villeNaissance = getElementTextContent(element, "Ville_naissance");
                String nationalite = getElementTextContent(element, "Nationalite");
                String codePostal = getElementTextContent(element, "Code_postal");
                String adresse = getElementTextContent(element, "Adresse");
                String ville = getElementTextContent(element, "Ville");
                String numeroTelephone1 = getElementTextContent(element, "Numéro_de_telephone1");
                String numeroTelephone2 = getElementTextContent(element, "Numéro_de_telephone2");
                String adresseEmail = getElementTextContent(element, "Adresse_email");
                String pratique = getElementTextContent(element, "Pratique");
                String lateralite = getElementTextContent(element, "Latéralité");
                String categorie = getElementTextContent(element, "Catégorie");
                String idClub = getElementTextContent(element, "id_du_club");

                // Récupérez la liste des armes pratiquées
                List<String> pratiqueEscrimeArmes = new ArrayList<>();
                NodeList armesNodeList = element.getElementsByTagName("Arme");
                for (int j = 0; j < armesNodeList.getLength(); j++) {
                    String arme = armesNodeList.item(j).getTextContent();
                    pratiqueEscrimeArmes.add(arme);
                }

                // Récupérez les détails du responsable légal
                Element responsableLegalElement = (Element) element.getElementsByTagName("Responsable_Légal").item(0);
                String responsableNom = getElementTextContent(responsableLegalElement, "Nom_responsable");
                String responsablePrenom = getElementTextContent(responsableLegalElement, "Prenom_responsable");

                // Créez un objet Adherent avec le constructeur
                Adherent adherent = new Adherent(id, nom, prenom, dateNaissance, genre, nomDeNaissance, paysNaissance, villeNaissance, nationalite,
                        codePostal, adresse, ville, numeroTelephone1, numeroTelephone2, adresseEmail, pratique, lateralite, idClub, categorie,
                        pratiqueEscrimeArmes, new ResponsableLegal(responsableNom, responsablePrenom));

                // Ajoutez l'adhérent à la liste
                adherents.add(adherent);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adherents;
    }

    private static String getElementTextContent(Element parentElement, String childElementName) {
        NodeList nodeList = parentElement.getElementsByTagName(childElementName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
}
