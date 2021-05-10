package core.model;

import javax.persistence.*;

@Entity
@Table(name = "raza", schema = "public", catalog = "shelter")
public class RazaEntity {
    int id;
    String nombre;

    EspecieEntity especie;

    @Id
    public int getId() {
        return id;
    }


    @OneToOne
    @JoinColumn(name = "especie_id")
    public EspecieEntity getEspecie() {
        return especie;
    }

    @Basic
    @Column(name = "nombre")
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
