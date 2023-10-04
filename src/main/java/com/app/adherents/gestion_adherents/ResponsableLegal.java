package com.app.adherents.gestion_adherents;

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
}

