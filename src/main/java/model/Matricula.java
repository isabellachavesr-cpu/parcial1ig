package model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Matricula {

    private static int contadorId = 1;

    private final int id;
    private final Estudiante estudiante;
    private final Curso curso;
    private final LocalDate fechaMatricula;
    private final List<ServicioAdicional> serviciosAdicionales;
    private final double descuentoPorcentaje;
    private final double valorFinal;

    private Matricula(Builder builder) {
        this.id = contadorId++;
        this.estudiante = builder.estudiante;
        this.curso = builder.curso;
        this.fechaMatricula = builder.fechaMatricula;
        this.serviciosAdicionales = builder.serviciosAdicionales;
        this.descuentoPorcentaje = builder.descuentoPorcentaje;
        this.valorFinal = calcularValorFinal();
    }

    private double calcularValorFinal() {
        double valorServicios = serviciosAdicionales.stream()
                .mapToDouble(ServicioAdicional::getPrecio)
                .sum();
        double subtotal = curso.calcularValorBase() + valorServicios;
        double descuento = subtotal * (descuentoPorcentaje / 100.0);
        return subtotal - descuento;
    }

    public int getId() {
        return id;
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

    public List<ServicioAdicional> getServiciosAdicionales() {
        return Collections.unmodifiableList(serviciosAdicionales);
    }

    public double getDescuentoPorcentaje() {
        return descuentoPorcentaje;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    @Override
    public String toString() {
        return "Matricula #" + id + " - " + estudiante.getNombreCompleto()
                + " en " + curso.getNombre() + " ($" + String.format("%.2f", valorFinal) + ")";
    }

    /** Constructor paso a paso (patron Builder) para {@link Matricula}. */
    public static class Builder {

        private Estudiante estudiante;
        private Curso curso;
        private LocalDate fechaMatricula = LocalDate.now();
        private final List<ServicioAdicional> serviciosAdicionales = new ArrayList<>();
        private double descuentoPorcentaje = 0;

        public Builder estudiante(Estudiante estudiante) {
            this.estudiante = estudiante;
            return this;
        }

        public Builder curso(Curso curso) {
            this.curso = curso;
            return this;
        }

        public Builder fechaMatricula(LocalDate fechaMatricula) {
            if (fechaMatricula != null) {
                this.fechaMatricula = fechaMatricula;
            }
            return this;
        }

        public Builder agregarServicio(ServicioAdicional servicio) {
            if (servicio != null) {
                this.serviciosAdicionales.add(servicio);
            }
            return this;
        }

        public Builder servicios(List<ServicioAdicional> servicios) {
            if (servicios != null) {
                this.serviciosAdicionales.addAll(servicios);
            }
            return this;
        }

        /** El descuento nunca puede quedar fuera del rango logico 0-100%. */
        public Builder descuentoPorcentaje(double descuentoPorcentaje) {
            if (descuentoPorcentaje < 0) {
                this.descuentoPorcentaje = 0;
            } else if (descuentoPorcentaje > 100) {
                this.descuentoPorcentaje = 100;
            } else {
                this.descuentoPorcentaje = descuentoPorcentaje;
            }
            return this;
        }

        public Matricula build() {
            if (estudiante == null || curso == null) {
                throw new IllegalStateException(
                        "Una matricula requiere un estudiante y un curso definidos.");
            }
            return new Matricula(this);
        }
    }
}
