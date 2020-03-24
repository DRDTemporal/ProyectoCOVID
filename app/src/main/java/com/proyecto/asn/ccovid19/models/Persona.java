package com.proyecto.asn.ccovid19.models;

public class Persona {
    private String nombres = "", apellidos = "", tipoID = "", id = "", telefono = "", departamento = "", municipio = "", edad = "", email = "", direccion = "";
    private int caso = 0;

    public Persona() {
    }

    public Persona(String nombres, String apellidos, String tipoID, String id, String telefono, String departamento, String municipio, String edad, String email, String direccion, int caso) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoID = tipoID;
        this.id = id;
        this.telefono = telefono;
        this.departamento = departamento;
        this.municipio = municipio;
        this.edad = edad;
        this.email = email;
        this.direccion = direccion;
        this.caso = caso;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipoID() {
        return tipoID;
    }

    public void setTipoID(String tipoID) {
        this.tipoID = tipoID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCaso() {
        return caso;
    }

    public void setCaso(int caso) {
        this.caso = caso;
    }
}
