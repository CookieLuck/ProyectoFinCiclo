package com.example.shelter.Model;

import java.util.Date;
public class AnimalEntity {

    long id;
    String nombre;
    Date fecha_nacimiento;
    String description;
    boolean cuidados_especiales;
    ProtectoraEntity protectora;
    RazaEntity raza;
    UsuarioEntity adoptante;

    public UsuarioEntity getAdoptante() {
        return adoptante;
    }

    public void setAdoptante(UsuarioEntity adoptante) {
        this.adoptante = adoptante;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCuidados_especiales() {
        return cuidados_especiales;
    }

    public ProtectoraEntity getProtectora() {
        return protectora;
    }

    public RazaEntity getRaza() {
        return raza;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCuidados_especiales(boolean cuidados_especiales) {
        this.cuidados_especiales = cuidados_especiales;
    }

    public void setProtectora(ProtectoraEntity protectora) {
        this.protectora = protectora;
    }

    public void setRaza(RazaEntity raza) {
        this.raza = raza;
    }
}
