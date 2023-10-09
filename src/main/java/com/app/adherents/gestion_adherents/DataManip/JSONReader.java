package com.app.adherents.gestion_adherents.DataManip;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JSONReader {
    public static String getJsonValue(String key) {
        String filePath = "src/main/java/com/app/adherents/gestion_adherents/DataManip/paths.json";

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(filePath));

            JSONObject jsonObject = (JSONObject) obj;

            Object value = jsonObject.get(key);

            if (value != null) {
                return value.toString();
            } else {
                return "Clé introuvable";
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return "Erreur de lecture du fichier JSON";
        }
    }

    public static void main(String[] args) {
    }
}
