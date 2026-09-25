package model;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa a un estudiante matriculado (o por matricular) en la academia.
 * El documento de identidad es el identificador natural usado en las
 * consultas ("buscar un estudiante mediante su documento de identidad").
 */
public class Estudiante {

    private String nombreCompleto;
    private String documentoIdentidad;
    private String telefono;
    private String correo;
    private int edad;
    private LocalDate fechaRegistro;

    public Estudiante(String nombreCompleto, String documentoIdentidad, String telefono,
                      String correo, int edad, LocalDate fechaRegistro) {
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.telefono = telefono;
        this.correo = correo;
        this.edad = edad;
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estudiante)) return false;
        Estudiante that = (Estudiante) o;
        return Objects.equals(documentoIdentidad, that.documentoIdentidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentoIdentidad);
    }

    @Override
    public String toString() {
        return nombreCompleto + " (CC " + documentoIdentidad + ")";
    }
}
