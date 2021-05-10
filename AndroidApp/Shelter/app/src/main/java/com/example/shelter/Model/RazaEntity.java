package com.example.shelter.Model;

public class RazaEntity {
    int id;
    String nombre;

    EspecieEntity especie;

    public int getId() {
        return id;
    }

    public EspecieEntity getEspecie() {
        return especie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEspecie(EspecieEntity especie) {
        this.especie = especie;
    }
}
