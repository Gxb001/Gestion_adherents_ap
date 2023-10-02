package com.xml.gestion_adherents_ap;

public class Catégorie {
    //Une catégorie possede un nom, un code, une année min et une année max
    private String nom;
    private String code;
    private int anneeMin;
    private int anneeMax;

    public Catégorie(String nom, String code, int anneeMin, int anneeMax) {
        this.nom = nom;
        this.code = code;
        this.anneeMin = anneeMin;
        this.anneeMax = anneeMax;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAnneeMin() {
        return anneeMin;
    }

    public void setAnneeMin(int anneeMin) {
        this.anneeMin = anneeMin;
    }

    public int getAnneeMax() {
        return anneeMax;
    }

    public void setAnneeMax(int anneeMax) {
        this.anneeMax = anneeMax;
    }

    @Override
    public String toString() {
        return "Catégorie{" +
                "nom='" + nom + '\'' +
                ", code='" + code + '\'' +
                ", anneeMin=" + anneeMin +
                ", anneeMax=" + anneeMax +
                '}';
    }
}
