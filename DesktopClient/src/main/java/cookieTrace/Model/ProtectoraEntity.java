package cookieTrace.Model;

import java.util.Objects;

public class ProtectoraEntity {
    private String nombre;
    private String contrasenia;
    private String direccion;
    private String descripcion;

    private String correo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

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
