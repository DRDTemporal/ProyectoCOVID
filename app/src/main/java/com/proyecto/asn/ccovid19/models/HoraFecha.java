package com.proyecto.asn.ccovid19.models;

public class HoraFecha {
    private  String datetime ="";

    public HoraFecha() {
    }

    public HoraFecha(String datetime) {
        this.datetime = datetime;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
