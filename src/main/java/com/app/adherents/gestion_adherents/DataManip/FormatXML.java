package com.app.adherents.gestion_adherents.DataManip;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;

public class FormatXML {

    public static void formaterXML(String xmlFilePath) {
        try {
            // Charger le fichier XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(new File(xmlFilePath));

            // Supprimer les espaces vides entre les balises
            document.getDocumentElement().normalize();

            // Configurer la sortie pour supprimer l'indentation
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");

            // Écrire le document XML avec indentation mais sans espaces vides
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            String xmlString = writer.toString().replaceAll("\n\\s*", "").trim();

            // Écrire le fichier XML formaté dans le fichier d'origine
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(xmlFilePath))) {
                bufferedWriter.write(xmlString);
            }

            System.out.println("Fichier XML formaté avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Exemple d'utilisation de la fonction pour formater un fichier XML
        String xmlFilePath = JSONReader.getJsonValue("adherent");
        formaterXML(xmlFilePath);
    }
}



