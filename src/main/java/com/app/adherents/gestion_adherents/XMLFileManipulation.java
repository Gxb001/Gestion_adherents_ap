package com.app.adherents.gestion_adherents;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
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
import java.util.*;

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
                    } else {
                        throw new Exception("La balise " + baliseAAfficher + " n'existe pas sous l'ID " + id + ".");
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return "Erreur lors de la lecture du fichier XML : " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur : " + e.getMessage();
        }
        return xmlFilePath;
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
    public static String[] afficherNomPrenomResponsable(String xmlFilePath, String balisePrincipale, String id) {
        try {
            // Configuration du parseur DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Chargement du fichier XML
            File xmlFile = new File(xmlFilePath);

            if (!xmlFile.exists()) {
                return new String[] {"Le fichier XML n'existe pas."};
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

                        return new String[] {nom, prenom};
                    } else {
                        throw new Exception("Les balises du responsable légal n'existent pas sous l'ID " + id + ".");
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return new String[] {"Erreur lors de la lecture du fichier XML : " + e.getMessage()};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[] {"Erreur : " + e.getMessage()};
        }
        return new String[] {xmlFilePath};
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
                return Integer.parseInt(lastId);
            } else {
                System.out.println("Aucun élément <"+ balisePrincipale.toLowerCase() +"> trouvé dans le fichier XML.");
            }
        } catch (Exception e) {
            System.err.println("Une erreur s'est produite : " + e.getMessage());
        }
        return 0;
    }
    public static List<String> afficherArmesPratiquées(String xmlFilePath, String balisePrincipale, String id) {
        List<String> armesPratiquées = new ArrayList<>();

        try {
            // Configuration du parseur DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Chargement du fichier XML
            File xmlFile = new File(xmlFilePath);

            if (!xmlFile.exists()) {
                return Collections.singletonList("Le fichier XML n'existe pas.");
            }

            // Analyse du fichier XML
            Document document = builder.parse(xmlFile);
            NodeList nodeList = document.getElementsByTagName(balisePrincipale);

            // Parcours des éléments à la recherche de l'ID spécifié
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String elementID = element.getAttribute("id");

                if (elementID.equals(id)) {
                    // Recherche des balises Arme
                    NodeList armesNodes = element.getElementsByTagName("Arme");

                    for (int j = 0; j < armesNodes.getLength(); j++) {
                        Element arme = (Element) armesNodes.item(j);
                        armesPratiquées.add(arme.getTextContent());
                    }

                    if (armesPratiquées.isEmpty()) {
                        throw new Exception("L'adhérent n'a pas spécifié d'armes pratiquées sous l'ID " + id + ".");
                    }

                    return armesPratiquées;
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return Collections.singletonList("Erreur lors de la lecture du fichier XML : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonList("Erreur : " + e.getMessage());
        }

        return Collections.singletonList(xmlFilePath);
    }


    public static boolean modifierValeurBalise(String xmlFilePath, String balisePrincipale, String id, String baliseAModifier, List<String> nouvellesValeurs) {
        try {
            // Configuration du parseur DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Chargement du fichier XML
            File xmlFile = new File(xmlFilePath);

            if (!xmlFile.exists()) {
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
                            // Vérifier si la balise à modifier est "Pratique_Escrime"
                            if (baliseAModifier.equals("Pratique_Escrime")) {
                                // Supprimer les anciennes balises d'armes
                                Node pratiqueEscrimeNode = childNode;
                                NodeList armesNodes = pratiqueEscrimeNode.getChildNodes();

                                for (int k = armesNodes.getLength() - 1; k >= 0; k--) {
                                    Node armeNode = armesNodes.item(k);
                                    if (armeNode.getNodeType() == Node.ELEMENT_NODE && armeNode.getNodeName().equals("Arme")) {
                                        pratiqueEscrimeNode.removeChild(armeNode);
                                    }
                                }

                                // Ajouter les nouvelles balises d'armes
                                for (String arme : nouvellesValeurs) {
                                    Element armeElement = document.createElement("Arme");
                                    armeElement.setTextContent(arme);
                                    pratiqueEscrimeNode.appendChild(armeElement);
                                }
                            } else if (baliseAModifier.equals("Responsable_Légal")) {
                                // Vérifier si la balise à modifier est "Responsable_Légal"
                                Element parentElement = (Element) childNode;
                                NodeList responsableNodes = parentElement.getChildNodes();

                                // Supprimer les anciennes balises "Nom_responsable" et "Prenom_responsable"
                                for (int k = responsableNodes.getLength() - 1; k >= 0; k--) {
                                    Node responsableNode = responsableNodes.item(k);
                                    if (responsableNode.getNodeType() == Node.ELEMENT_NODE && (responsableNode.getNodeName().equals("Nom_responsable") || responsableNode.getNodeName().equals("Prenom_responsable"))) {
                                        parentElement.removeChild(responsableNode);
                                    }
                                }

                                // Ajouter les nouvelles balises "Nom_responsable" et "Prenom_responsable"
                                Element nouvelleBaliseNom = document.createElement("Nom_responsable");
                                Element nouvelleBalisePrenom = document.createElement("Prenom_responsable");

                                nouvelleBaliseNom.setTextContent(nouvellesValeurs.get(0));
                                nouvelleBalisePrenom.setTextContent(nouvellesValeurs.get(1));

                                parentElement.appendChild(nouvelleBaliseNom);
                                parentElement.appendChild(nouvelleBalisePrenom);
                            }

                            // Enregistrement des modifications dans le fichier XML
                            TransformerFactory transformerFactory = TransformerFactory.newInstance();
                            Transformer transformer = transformerFactory.newTransformer();
                            transformer.setOutputProperty(OutputKeys.INDENT, "no"); // Activer l'indentation
                            DOMSource source = new DOMSource(document);
                            StreamResult result = new StreamResult(xmlFile);
                            transformer.transform(source, result);
                            return true; // Modification réussie
                        }
                    }
                    return false; // La balise à modifier n'a pas été trouvée
                }
            }
            // Si l'ID spécifié n'est pas trouvé
            return false; // L'ID spécifié n'a pas été trouvé
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
            return false; // Erreur lors de la modification du fichier XML
        }
    }


    public static void main(String[] args) {
        try {   // Chemin vers votre fichier XML
            String xmlFilePath = JSONReader.getJsonValue("adherent");

            //List<String> nouvellesValeurArmes = Arrays.asList( "Fleuret", "Epée");
            //modifierValeurBalise(xmlFilePath, "adhérent", "1", "Pratique_Escrime", nouvellesValeurArmes);
            //System.out.println(afficherArmesPratiquées(xmlFilePath, "adhérent", "1"));

            List<String> nouvellesValeursResponsableLegal = Arrays.asList("test", "Michelle");
            modifierValeurBalise(xmlFilePath, "adhérent", "1", "Responsable_Légal", nouvellesValeursResponsableLegal);

            System.out.println(afficherNomPrenomResponsable(xmlFilePath, "adhérent", "1")[0]);
            System.out.println(afficherNomPrenomResponsable(xmlFilePath, "adhérent", "1")[1]);
            System.out.println(afficherArmesPratiquées(xmlFilePath, "adhérent", "1"));


            //List<String> nouvellesValeursArmes = Arrays.asList("Fleuret");
            //modifierValeurBalise(xmlFilePath, "adhérent", "1", "Pratique_Escrime", nouvellesValeursArmes);

            //modifierValeurBalise(xmlFilePath, "adhérent", "1", "Nom", "test");
            //List result = afficherArmesPratiquées(xmlFilePath, "adhérent", "1");
            //System.out.println(result);
            //String rnom = afficherNomPrenomResponsable(xmlFilePath, "adhérent", "1")[0];
            //String rprenom = afficherNomPrenomResponsable(xmlFilePath, "adhérent", "1")[1];
            //System.out.println("Le responsable légal se nome : " + rnom + " " + rprenom);
            // Appel de la fonction pour afficher l'adresse du club avec l'ID "1"
            //String result = afficherXML(xmlFilePath, "adhérent", "2", "Responsable_Légal");
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
