package com.proyecto.asn.ccovid19.models;

import java.util.List;

public class   Lugar{
    private String departamento, municipio;

    public Lugar() {
    }

    public Lugar(String departamento, String municipio) {
        this.departamento = departamento;
        this.municipio = municipio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }


}
