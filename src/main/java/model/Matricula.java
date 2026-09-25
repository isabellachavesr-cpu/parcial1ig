package model;
import co.edu.uniquindio.lenguajecafetero.exception.AcademiaException;
import co.edu.uniquindio.lenguajecafetero.model.descuento.PoliticaDescuento;
import co.edu.uniquindio.lenguajecafetero.model.descuento.SinDescuento;
import co.edu.uniquindio.lenguajecafetero.util.Validaciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Matrícula de un estudiante en un curso. Se construye con el patrón Builder porque tiene
 * varios datos obligatorios y opcionales (servicios, profesor, descuento) y reglas de validación.
 *
 * <p>Valor total = (valor base del curso + costo de sesiones con profesor + servicios) - descuento.</p>
 */
public class Matricula {

    private final String codigo;
    private final Estudiante estudiante;
    private final Curso curso;
    private final LocalDate fechaMatricula;
    private final List<ServicioAdicional> servicios;
    private final Asignacion asignacion;
    private final PoliticaDescuento politicaDescuento;

    private Matricula(Builder b) {
        this.codigo = b.codigo;
        this.estudiante = b.estudiante;
        this.curso = b.curso;
        this.fechaMatricula = b.fechaMatricula;
        this.servicios = new ArrayList<>(b.servicios);
        this.asignacion = b.asignacion;
        this.politicaDescuento = b.politicaDescuento;
    }

    public static Builder builder() {
        return new Builder();
    }

    public double calcularValorServicios() {
        double total = 0;
        for (ServicioAdicional servicio : servicios) {
            total += servicio.getPrecio();
        }
        return total;
    }

    public double calcularSubtotal() {
        double subtotal = curso.calcularValorBase() + calcularValorServicios();
        if (asignacion != null) {
            subtotal += asignacion.calcularCostoSesiones();
        }
        return subtotal;
    }

    public double calcularDescuento() {
        return politicaDescuento.calcularDescuento(calcularSubtotal(), curso);
    }

    public double calcularValorTotal() {
        return calcularSubtotal() - calcularDescuento();
    }

    public String getCodigo() {
        return codigo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }

    public List<ServicioAdicional> getServicios() {
        return Collections.unmodifiableList(servicios);
    }

    /** @return la asignación con profesor, o {@code null} si el curso no es personalizado. */
    public Asignacion getAsignacion() {
        return asignacion;
    }

    public PoliticaDescuento getPoliticaDescuento() {
        return politicaDescuento;
    }

    @Override
    public String toString() {
        return codigo + " - " + estudiante.getNombreCompleto() + " / " + curso.getNombre();
    }

    // ------------------------------------------------------------------ Builder

    public static class Builder {

        private String codigo;
        private Estudiante estudiante;
        private Curso curso;
        private LocalDate fechaMatricula = LocalDate.now();
        private final List<ServicioAdicional> servicios = new ArrayList<>();
        private Profesor profesor;
        private PoliticaDescuento politicaDescuento = new SinDescuento();
        private Asignacion asignacion;

        private Builder() {
        }

        public Builder codigo(String codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder estudiante(Estudiante estudiante) {
            this.estudiante = estudiante;
            return this;
        }

        public Builder curso(Curso curso) {
            this.curso = curso;
            return this;
        }

        public Builder fechaMatricula(LocalDate fechaMatricula) {
            this.fechaMatricula = fechaMatricula;
            return this;
        }

        public Builder agregarServicio(ServicioAdicional servicio) {
            if (servicio != null) {
                this.servicios.add(servicio);
            }
            return this;
        }

        public Builder servicios(Collection<ServicioAdicional> servicios) {
            this.servicios.clear();
            if (servicios != null) {
                this.servicios.addAll(servicios);
            }
            return this;
        }

        public Builder profesor(Profesor profesor) {
            this.profesor = profesor;
            return this;
        }

        public Builder politicaDescuento(PoliticaDescuento politicaDescuento) {
            this.politicaDescuento = politicaDescuento != null ? politicaDescuento : new SinDescuento();
            return this;
        }

        public Matricula build() {
            Validaciones.texto(codigo, "código de matrícula");
            Validaciones.requerido(estudiante, "estudiante");
            Validaciones.requerido(curso, "curso");
            Validaciones.requerido(fechaMatricula, "fecha de matrícula");

            if (curso.getEstado() != EstadoCurso.ACTIVO) {
                throw new AcademiaException("Solo se puede matricular en cursos activos. El curso '"
                        + curso.getNombre() + "' está " + curso.getEstado() + ".");
            }
            for (ServicioAdicional servicio : servicios) {
                if (!servicio.isDisponible()) {
                    throw new AcademiaException("El servicio '" + servicio.getNombre() + "' no está disponible.");
                }
            }

            if (curso instanceof CursoPersonalizado personalizado) {
                if (profesor == null) {
                    throw new AcademiaException("Un curso personalizado requiere un profesor asignado.");
                }
                if (profesor.getIdioma() != curso.getIdioma()) {
                    throw new AcademiaException("El profesor " + profesor.getNombre()
                            + " enseña " + profesor.getIdioma() + " y el curso es de " + curso.getIdioma() + ".");
                }
                asignacion = new Asignacion(estudiante, personalizado, profesor);
            } else {
                if (profesor != null) {
                    throw new AcademiaException("Solo los cursos personalizados tienen profesor asignado.");
                }
                asignacion = null;
            }
            return new Matricula(this);
        }
    }
}
