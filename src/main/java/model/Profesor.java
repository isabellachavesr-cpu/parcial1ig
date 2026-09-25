package model;
import java.util.Objects;

/**
 * Profesor de la academia. Los profesores pueden ser asignados a
 * estudiantes matriculados en cursos personalizados (ver {@link Asignacion}).
 */
public class Profesor {

    private String identificacion;
    private String nombre;
    private Idioma idiomaQueEnsenia;
    private String telefono;
    private double tarifaPorSesion;

    public Profesor(String identificacion, String nombre, Idioma idiomaQueEnsenia,
                    String telefono, double tarifaPorSesion) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.idiomaQueEnsenia = idiomaQueEnsenia;
        this.telefono = telefono;
        this.tarifaPorSesion = tarifaPorSesion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Idioma getIdiomaQueEnsenia() {
        return idiomaQueEnsenia;
    }

    public void setIdiomaQueEnsenia(Idioma idiomaQueEnsenia) {
        this.idiomaQueEnsenia = idiomaQueEnsenia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getTarifaPorSesion() {
        return tarifaPorSesion;
    }

    public void setTarifaPorSesion(double tarifaPorSesion) {
        this.tarifaPorSesion = tarifaPorSesion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profesor)) return false;
        Profesor profesor = (Profesor) o;
        return Objects.equals(identificacion, profesor.identificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificacion);
    }

    @Override
    public String toString() {
        return nombre + " - " + idiomaQueEnsenia + " (" + identificacion + ")";
    }
}

