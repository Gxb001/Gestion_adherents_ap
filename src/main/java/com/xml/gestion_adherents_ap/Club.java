package com.xml.gestion_adherents_ap;

public class Club {
    //#TODO : ajouter id club
    // un club possede un nom, adresse, contact, tel, mail, site
    private String nom;
    private String adresse;
    private String contact;
    private String tel;
    private String mail;

    public Club(String nom, String adresse, String contact, String tel, String mail) {
        this.nom = nom;
        this.adresse = adresse;
        this.contact = contact;
        this.tel = tel;
        this.mail = mail;
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

    @Override
    public String toString() {
        return "Club{" +
                "nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", contact='" + contact + '\'' +
                ", tel='" + tel + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
