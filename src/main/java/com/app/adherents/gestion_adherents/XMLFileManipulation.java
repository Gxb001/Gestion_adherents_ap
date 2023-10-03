package com.app.adherents.gestion_adherents;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XMLFileManipulation {

    // Fonction pour afficher les éléments d'une balise de l'ID spécifié
    public static String afficherXML(String xmlFilePath, String balisePrincipale, String id, String baliseAAfficher) {
        try {
            // Configuration du parseur DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Chargement du fichier XML
            File xmlFile = new File(xmlFilePath);

            if (!xmlFile.exists()) {
                return "Le fichier XML n'existe pas.";
            }

            // Analyse du fichier XML
            Document document = builder.parse(xmlFile);
            NodeList nodeList = document.getElementsByTagName(balisePrincipale);

            // Parcours des éléments à la recherche de l'ID spécifié
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String elementID = element.getAttribute("id");

                if (elementID.equals(id)) {
                    // Recherche de la balise à afficher
                    NodeList childNodes = element.getElementsByTagName(baliseAAfficher);

                    if (childNodes.getLength() > 0) {
                        Element balise = (Element) childNodes.item(0);
                        String result = balise.getTextContent();
                        journaliser("Affichage de la balise " + baliseAAfficher + " avec l'ID " + id);
                        return result;
                    } else {
                        return "La balise " + baliseAAfficher + " n'existe pas sous l'ID " + id + ".";
                    }
                }
            }

            // Si l'ID spécifié n'est pas trouvé
            return "L'ID " + id + " n'existe pas dans la balise principale " + balisePrincipale + ".";
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            journaliser("Erreur lors de la lecture du fichier XML : " + e.getMessage());
            return "Erreur lors de la lecture du fichier XML";
        }
    }

    public static boolean comparerBalisesXML(String xmlFilePath, String balisePrincipale, String baliseAAfficher, String valeurAComparer) {
        try {
            // Configuration du parseur DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Chargement du fichier XML
            File xmlFile = new File(xmlFilePath);

            if (!xmlFile.exists()) {
                return false; // Le fichier XML n'existe pas.
            }

            // Analyse du fichier XML
            Document document = builder.parse(xmlFile);
            NodeList nodeList = document.getElementsByTagName(balisePrincipale);

            // Parcours des éléments à la recherche de la valeur de la balise demandée
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                NodeList childNodes = element.getElementsByTagName(baliseAAfficher);

                if (childNodes.getLength() > 0) {
                    Element balise = (Element) childNodes.item(0);
                    String valeurBalise = balise.getTextContent().trim(); // Supprime les espaces inutiles
                    // Comparaison insensible à la casse et avec espace
                    if (valeurBalise.equalsIgnoreCase(valeurAComparer.trim())) {
                        return false; // Correspondance trouvée, renvoie false.
                    }
                }
            }

            // Si aucune correspondance n'est trouvée
            return true; // Aucune correspondance trouvée, renvoie true.
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            journaliser("Erreur lors de la lecture du fichier XML : " + e.getMessage());
            return false; // Erreur lors de la lecture du fichier XML.
        }
    }



    // Fonction pour modifier la valeur d'une balise spécifiée
    public static boolean modifierValeurBalise(String xmlFilePath, String balisePrincipale, String id, String baliseAModifier, String nouvelleValeur) {
        try {
            // Appel de la fonction pour afficher la valeur actuelle
            String valeurActuelle = afficherXML(xmlFilePath, balisePrincipale, id, baliseAModifier);

            if (valeurActuelle.equals("Le fichier XML n'existe pas.") || valeurActuelle.equals("Erreur lors de la lecture du fichier XML")) {
                journaliser("Erreur lors de la lecture du fichier XML");
                return false; // Échec de la lecture du fichier XML
            } else if (valeurActuelle.equals(nouvelleValeur)) {
                journaliser("La nouvelle valeur est identique à l'ancienne.");
                System.out.println("La nouvelle valeur est identique à l'ancienne.");
                return false; // La nouvelle valeur est identique à l'ancienne
            }

            // Configuration du parseur DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Chargement du fichier XML
            File xmlFile = new File(xmlFilePath);

            if (!xmlFile.exists()) {
                journaliser("Le fichier XML n'existe pas.");
                return false; // Le fichier XML n'existe pas
            }

            // Analyse du fichier XML
            Document document = builder.parse(xmlFile);
            NodeList nodeList = document.getElementsByTagName(balisePrincipale);

            // Parcours des éléments à la recherche de l'ID spécifié
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String elementID = element.getAttribute("id");

                if (elementID.equals(id)) {
                    // Recherche de la balise à modifier
                    NodeList childNodes = element.getElementsByTagName(baliseAModifier);

                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node childNode = childNodes.item(j);

                        if (childNode.getNodeType() == Node.ELEMENT_NODE && childNode.getNodeName().equals(baliseAModifier)) {
                            childNode.setTextContent(nouvelleValeur);

                            // Enregistrement des modifications dans le fichier XML
                            TransformerFactory transformerFactory = TransformerFactory.newInstance();
                            Transformer transformer = transformerFactory.newTransformer();
                            DOMSource source = new DOMSource(document);
                            StreamResult result = new StreamResult(xmlFile);
                            transformer.transform(source, result);
                            journaliser("La balise " + baliseAModifier + " a été modifiée avec succès.");
                            return true; // Modification réussie
                        }
                    }
                    return false; // La balise à modifier n'a pas été trouvée
                }
            }

            // Si l'ID spécifié n'est pas trouvé
            journaliser("L'ID " + id + " n'existe pas dans la balise principale " + balisePrincipale);
            return false; // L'ID spécifié n'a pas été trouvé
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
            journaliser("Erreur lors de la modification du fichier XML : " + e.getMessage());
            return false; // Erreur lors de la modification du fichier XML
        }
    }


    // Fonction pour journaliser les actions
    private static void journaliser(String message) {
        BufferedWriter writer = null;
        try {
            // Chemin du fichier de journal
            String journalFilePath = "journal.log";

            // Création d'un objet de format de date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = dateFormat.format(new Date());

            // Écriture du message dans le fichier de journal avec horodatage
            writer = new BufferedWriter(new FileWriter(journalFilePath, true));
            writer.write("[" + timestamp + "] " + message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*public static List<Adherent> listerAdherents(String xmlFilePath) {
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

                // Créez un objet Adherent
                Adherent adherent = new Adherent(id, nom, prenom, dateNaissance, genre, nomDeNaissance, paysNaissance, villeNaissance, nationalite,
                        codePostal, adresse, ville, numeroTelephone1, numeroTelephone2, adresseEmail, pratique, lateralite, categorie,
                        pratiqueEscrimeArmes, new ResponsableLegal(responsableNom, responsablePrenom));

                // Ajoutez l'adhérent à la liste
                adherents.add(adherent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adherents;
    }*/


    private static String getElementTextContent(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }

    /*public static void main(String[] args) {
        try {
            String xmlFilePath = "C:\\Users\\Gabriel\\IdeaProjects\\XMLHandler\\src\\club.xml"; // Chemin vers votre fichier XML

            // Appel de la fonction pour afficher l'adresse du club avec l'ID "1"
            String result = afficherXML(xmlFilePath, "club", "2", "nom");
            //System.out.println(comparerBalisesXML(xmlFilePath, "club", "nom", "test"));
            //if (!result.startsWith("Erreur")) {
            //System.out.println("Nom: "+result);
            //System.out.println(modifierValeurBalise(xmlFilePath, "club", "2", "nom", "jl"));
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
