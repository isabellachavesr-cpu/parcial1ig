package model;
import co.edu.uniquindio.lenguajecafetero.util.Validaciones;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Clase abstracta que representa un curso de la academia.
 * Cada tipo concreto define cómo calcula su valor base (polimorfismo / OCP).
 */
public abstract class Curso {

    private final String codigo;
    private final String nombre;
    private final Idioma idioma;
    private final String descripcion;
    private final int duracionMeses;
    private final double valorMensual;
    private EstadoCurso estado;
    private final Set<Beneficio> beneficios;

    protected Curso(String codigo, String nombre, Idioma idioma, String descripcion,
                    int duracionMeses, double valorMensual, EstadoCurso estado,
                    Set<Beneficio> beneficios) {
        this.codigo = Validaciones.texto(codigo, "código");
        this.nombre = Validaciones.texto(nombre, "nombre");
        this.idioma = Validaciones.requerido(idioma, "idioma");
        this.descripcion = Validaciones.texto(descripcion, "descripción");
        this.duracionMeses = Validaciones.enteroPositivo(duracionMeses, "duración en meses");
        this.valorMensual = Validaciones.positivo(valorMensual, "valor mensual");
        this.estado = Validaciones.requerido(estado, "estado");
        this.beneficios = (beneficios == null || beneficios.isEmpty())
                ? EnumSet.noneOf(Beneficio.class)
                : EnumSet.copyOf(beneficios);
    }

    /** Tipo del curso (regular, intensivo o personalizado). */
    public abstract TipoCurso getTipo();

    /** Valor base del curso sin servicios adicionales ni descuentos. */
    public abstract double calcularValorBase();

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public double getValorMensual() {
        return valorMensual;
    }

    public EstadoCurso getEstado() {
        return estado;
    }

    public void setEstado(EstadoCurso estado) {
        this.estado = Validaciones.requerido(estado, "estado");
    }

    public Set<Beneficio> getBeneficios() {
        return Collections.unmodifiableSet(beneficios);
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (" + getTipo() + ")";
    }
}
