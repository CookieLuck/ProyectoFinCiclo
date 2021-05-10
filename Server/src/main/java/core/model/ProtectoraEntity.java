package core.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "protectora", schema = "public", catalog = "shelter")
public class ProtectoraEntity {
    private String nombre;
    private String contrasenia;
    private String direccion;
    private String descripcion;

    @Id
    private String correo;
    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "contrasenia")
    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @Basic
    @Column(name = "direccion")
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Basic
    @Column(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Basic
    @Column(name = "correo")
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtectoraEntity that = (ProtectoraEntity) o;
        return correo == that.correo && Objects.equals(nombre, that.nombre) && Objects.equals(contrasenia, that.contrasenia) && Objects.equals(direccion, that.direccion) && Objects.equals(descripcion, that.descripcion) && Objects.equals(correo, that.correo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, contrasenia, direccion, descripcion, correo);
    }
}
