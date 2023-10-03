package com.app.adherents.gestion_adherents;

import java.util.List;

public class Adherent {
    private int id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String genre;
    private String nomDeNaissance;
    private String paysNaissance;
    private String villeNaissance;
    private String nationalite;
    private String codePostal;
    private String adresse;
    private String ville;
    private String numeroTelephone1;
    private String numeroTelephone2;
    private String adresseEmail;
    private String pratique;
    private String lateralite;
    private String idClub;
    private String categorie;
    private List<String> pratiqueEscrimeArmes;
    private ResponsableLegal responsableLegal;

    public Adherent(int id, String nom, String prenom, String dateNaissance, String genre, String nomDeNaissance, String paysNaissance, String villeNaissance, String nationalite, String codePostal, String adresse, String ville, String numeroTelephone1, String numeroTelephone2, String adresseEmail, String pratique, String lateralite, String idClub, String categorie, List<String> pratiqueEscrimeArmes, ResponsableLegal responsableLegal) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.genre = genre;
        this.nomDeNaissance = nomDeNaissance;
        this.paysNaissance = paysNaissance;
        this.villeNaissance = villeNaissance;
        this.nationalite = nationalite;
        this.codePostal = codePostal;
        this.adresse = adresse;
        this.ville = ville;
        this.numeroTelephone1 = numeroTelephone1;
        this.numeroTelephone2 = numeroTelephone2;
        this.adresseEmail = adresseEmail;
        this.pratique = pratique;
        this.lateralite = lateralite;
        this.idClub = idClub;
        this.categorie = categorie;
        this.pratiqueEscrimeArmes = pratiqueEscrimeArmes;
        this.responsableLegal = responsableLegal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getNomDeNaissance() {
        return nomDeNaissance;
    }

    public void setNomDeNaissance(String nomDeNaissance) {
        this.nomDeNaissance = nomDeNaissance;
    }

    public String getPaysNaissance() {
        return paysNaissance;
    }

    public void setPaysNaissance(String paysNaissance) {
        this.paysNaissance = paysNaissance;
    }

    public String getVilleNaissance() {
        return villeNaissance;
    }

    public void setVilleNaissance(String villeNaissance) {
        this.villeNaissance = villeNaissance;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getNumeroTelephone1() {
        return numeroTelephone1;
    }

    public void setNumeroTelephone1(String numeroTelephone1) {
        this.numeroTelephone1 = numeroTelephone1;
    }

    public String getNumeroTelephone2() {
        return numeroTelephone2;
    }

    public void setNumeroTelephone2(String numeroTelephone2) {
        this.numeroTelephone2 = numeroTelephone2;
    }

    public String getAdresseEmail() {
        return adresseEmail;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }

    public String getPratique() {
        return pratique;
    }

    public void setPratique(String pratique) {
        this.pratique = pratique;
    }

    public String getLateralite() {
        return lateralite;
    }

    public void setLateralite(String lateralite) {
        this.lateralite = lateralite;
    }

    public String getIdClub() {
        return idClub;
    }

    public void setIdClub(String idClub) {
        this.idClub = idClub;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public List<String> getPratiqueEscrimeArmes() {
        return pratiqueEscrimeArmes;
    }

    public void setPratiqueEscrimeArmes(List<String> pratiqueEscrimeArmes) {
        this.pratiqueEscrimeArmes = pratiqueEscrimeArmes;
    }

    public ResponsableLegal getResponsableLegal() {
        return responsableLegal;
    }

    public void setResponsableLegal(ResponsableLegal responsableLegal) {
        this.responsableLegal = responsableLegal;
    }

    @Override
    public String toString() {
        return "Adherent{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance='" + dateNaissance + '\'' +
                ", genre='" + genre + '\'' +
                ", nomDeNaissance='" + nomDeNaissance + '\'' +
                ", paysNaissance='" + paysNaissance + '\'' +
                ", villeNaissance='" + villeNaissance + '\'' +
                ", nationalite='" + nationalite + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", adresse='" + adresse + '\'' +
                ", ville='" + ville + '\'' +
                ", numeroTelephone1='" + numeroTelephone1 + '\'' +
                ", numeroTelephone2='" + numeroTelephone2 + '\'' +
                ", adresseEmail='" + adresseEmail + '\'' +
                ", pratique='" + pratique + '\'' +
                ", lateralite='" + lateralite + '\'' +
                ", idClub='" + idClub + '\'' +
                ", categorie='" + categorie + '\'' +
                ", pratiqueEscrimeArmes=" + pratiqueEscrimeArmes +
                ", responsableLegal=" + responsableLegal +
                '}';
    }
}

