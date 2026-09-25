package model;
import co.edu.uniquindio.lenguajecafetero.util.Validaciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Estudiante de la academia. Puede tener varias matrículas (una por curso). */
public class Estudiante {

    private final String nombreCompleto;
    private final String documento;
    private final String telefono;
    private final String correo;
    private final int edad;
    private final LocalDate fechaRegistro;
    private final List<Matricula> matriculas = new ArrayList<>();

    public Estudiante(String nombreCompleto, String documento, String telefono,
                      String correo, int edad, LocalDate fechaRegistro) {
        this.nombreCompleto = Validaciones.texto(nombreCompleto, "nombre completo");
        this.documento = Validaciones.texto(documento, "documento de identidad");
        this.telefono = Validaciones.texto(telefono, "teléfono");
        this.correo = Validaciones.correo(correo);
        this.edad = Validaciones.enteroPositivo(edad, "edad");
        this.fechaRegistro = Validaciones.requerido(fechaRegistro, "fecha de registro");
    }

    /** Solo la academia (mismo paquete) asocia matrículas al estudiante. */
    void agregarMatricula(Matricula matricula) {
        matriculas.add(matricula);
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getDocumento() {
        return documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public int getEdad() {
        return edad;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public List<Matricula> getMatriculas() {
        return Collections.unmodifiableList(matriculas);
    }

    @Override
    public String toString() {
        return documento + " - " + nombreCompleto;
    }
}

