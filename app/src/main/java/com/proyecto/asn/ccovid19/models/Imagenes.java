package com.proyecto.asn.ccovid19.models;

public class Imagenes {
    private String imagen="";
    private int caso=0;

    public Imagenes() {
    }

    public Imagenes(String imagen, int caso) {
        this.imagen = imagen;
        this.caso = caso;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getCaso() {
        return caso;
    }

    public void setCaso(int caso) {
        this.caso = caso;
    }
}
