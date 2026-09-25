package model;
import java.time.LocalDate;

/**
 * Relacion entre un estudiante, el curso personalizado que adquirio y el
 * profesor responsable de dictarlo. Un profesor puede atender distintos
 * estudiantes; cada Asignacion identifica de forma unica esa relacion
 * tripartita, tal como lo exige el enunciado.
 */
public class Asignacion {

    private final Estudiante estudiante;
    private final CursoPersonalizado curso;
    private final Profesor profesor;
    private final LocalDate fechaAsignacion;

    public Asignacion(Estudiante estudiante, CursoPersonalizado curso, Profesor profesor,
                      LocalDate fechaAsignacion) {
        this.estudiante = estudiante;
        this.curso = curso;
        this.profesor = profesor;
        this.fechaAsignacion = fechaAsignacion;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public CursoPersonalizado getCurso() {
        return curso;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    @Override
    public String toString() {
        return estudiante.getNombreCompleto() + " -> " + curso.getNombre()
                + " con " + profesor.getNombre();
    }
}
