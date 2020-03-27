package com.proyecto.asn.ccovid19.models;

public class HoraFecha {
    private  String dateTime ="";

    public HoraFecha() {
    }

    public HoraFecha(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
