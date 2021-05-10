package core.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "animal", schema = "public", catalog = "shelter")
public class AnimalEntity {

    long id;
    String nombre;
    Date fecha_nacimiento;
    String description;
    boolean cuidados_especiales;
    ProtectoraEntity protectora;
    RazaEntity raza;
    UsuarioEntity adoptante;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    @Basic
    @Column(name="nombre")
    public String getNombre() {
        return nombre;
    }

    @Basic
    @Column(name="fecha_nacimiento")
    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    @Basic
    @Column(name="descripcion")
    public String getDescription() {
        return description;
    }

    @Basic
    @Column(name="cuidados_especiales")
    public boolean isCuidados_especiales() {
        return cuidados_especiales;
    }

    @ManyToOne
    @JoinColumn(name="adoptante")
    public UsuarioEntity getAdoptante(){return adoptante;}

    @ManyToOne
    @JoinColumn(name="protectora_correo")
    public ProtectoraEntity getProtectora() {
        return protectora;
    }

    @ManyToOne
    @JoinColumn(name="raza_id")
    public RazaEntity getRaza() {
        return raza;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAdoptante(UsuarioEntity adoptante) {
        this.adoptante = adoptante;
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
