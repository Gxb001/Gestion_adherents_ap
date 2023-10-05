package com.app.adherents.gestion_adherents;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                        return result;
                    }
                }
            }

            // Si l'ID spécifié n'est pas trouvé ou la balise n'est pas trouvée, vous pouvez renvoyer une valeur par défaut ou lancer une exception.
            throw new Exception("La balise " + baliseAAfficher + " n'existe pas sous l'ID " + id + ".");

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return "Erreur lors de la lecture du fichier XML : " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur : " + e.getMessage();
        }
    }


    /*
    verifie si une valeur est egal à la nouvelle
     */
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


    // Function pour modifier la valeur d'une balise spécifiée
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


    private static String getElementTextContent(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }

    /*
    Recupere le nombre d'elements dans une fichier xml, par exemple si il y à 20 adherents cela renvoie 20.
     */
    public static int last_id(String xmlFilePath, String balisePrincipale) {
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList BalList = doc.getElementsByTagName(balisePrincipale);

            if (BalList.getLength() > 0) {
                Node lastBal = BalList.item(BalList.getLength() - 1);
                Element lastElement = (Element) lastBal;
                String lastId = lastElement.getAttribute("id");
                //System.out.println("Le dernier ID est : " + lastId);
                return Integer.parseInt(lastId);
            } else {
                System.out.println("Aucun élément <"+ balisePrincipale.toLowerCase() +"> trouvé dans le fichier XML.");
            }
        } catch (Exception e) {
            System.err.println("Une erreur s'est produite : " + e.getMessage());
        }
        return 0;
    }

    public static void main(String[] args) {
        try {   // Chemin vers votre fichier XML

            // Appel de la fonction pour afficher l'adresse du club avec l'ID "1"
            //String result = afficherXML(xmlFilePath, "categorie", "2", "nom");
            //System.out.println(result);
            //System.out.println(last_id(xmlFilePath, "categorie"));
            //System.out.println(comparerBalisesXML(xmlFilePath, "club", "nom", "test"));
            //if (!result.startsWith("Erreur")) {
            //System.out.println("Nom: "+result);
            //System.out.println(modifierValeurBalise(xmlFilePath, "club", "2", "nom", "jl"));
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
