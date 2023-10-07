package com.app.adherents.gestion_adherents.Class;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ResponsableLegal {
    private String nomResponsable;
    private String prenomResponsable;

    public ResponsableLegal(String nomResponsable, String prenomResponsable) {
        this.nomResponsable = nomResponsable;
        this.prenomResponsable = prenomResponsable;
    }

    public String getNomResponsable() {
        return nomResponsable;
    }

    public void setNomResponsable(String nomResponsable) {
        this.nomResponsable = nomResponsable;
    }

    public String getPrenomResponsable() {
        return prenomResponsable;
    }

    public void setPrenomResponsable(String prenomResponsable) {
        this.prenomResponsable = prenomResponsable;
    }

    @Override
    public String toString() {
        return "ResponsableLegal{" +
                "nomResponsable='" + nomResponsable + '\'' +
                ", prenomResponsable='" + prenomResponsable + '\'' +
                '}';
    }

    public static String[] afficherNomPrenomResponsable(String xmlFilePath, String balisePrincipale, String id) {
        try {
            // Configuration du parseur DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Chargement du fichier XML
            File xmlFile = new File(xmlFilePath);

            if (!xmlFile.exists()) {
                return new String[]{"Le fichier XML n'existe pas."};
            }

            // Analyse du fichier XML
            Document document = builder.parse(xmlFile);
            NodeList nodeList = document.getElementsByTagName(balisePrincipale);

            // Parcours des éléments à la recherche de l'ID spécifié
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String elementID = element.getAttribute("id");

                if (elementID.equals(id)) {
                    // Recherche des balises Nom_responsable et Prenom_responsable
                    NodeList nomResponsableNodes = element.getElementsByTagName("Nom_responsable");
                    NodeList prenomResponsableNodes = element.getElementsByTagName("Prenom_responsable");

                    if (nomResponsableNodes.getLength() > 0 && prenomResponsableNodes.getLength() > 0) {
                        Element nomResponsable = (Element) nomResponsableNodes.item(0);
                        Element prenomResponsable = (Element) prenomResponsableNodes.item(0);

                        String nom = nomResponsable.getTextContent();
                        String prenom = prenomResponsable.getTextContent();

                        return new String[]{nom, prenom};
                    } else {
                        throw new Exception("Les balises du responsable légal n'existent pas sous l'ID " + id + ".");
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return new String[]{"Erreur lors de la lecture du fichier XML : " + e.getMessage()};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Erreur : " + e.getMessage()};
        }
        return new String[]{xmlFilePath};
    }
}

