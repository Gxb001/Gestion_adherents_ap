package com.app.adherents.gestion_adherents.Class;

public class Club {
    private int id;
    private String nom;
    private String adresse;
    private String contact;
    private String tel;
    private String mail;
    private String site;

    public Club(int id, String nom, String adresse, String contact, String tel, String mail, String site) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.contact = contact;
        this.tel = tel;
        this.mail = mail;
        this.site = site;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", contact='" + contact + '\'' +
                ", tel='" + tel + '\'' +
                ", mail='" + mail + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}

