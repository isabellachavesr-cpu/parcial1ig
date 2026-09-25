package model;
import co.edu.uniquindio.lenguajecafetero.util.Validaciones;

import java.util.Set;

/**
 * Curso personalizado: incluye sesiones con profesor, nivel de referencia requerido
 * y los objetivos del estudiante. El costo de las sesiones se calcula en la {@link Asignacion}.
 */
public class CursoPersonalizado extends Curso {

    private final int cantidadSesiones;
    private final NivelReferencia nivelRequerido;
    private final String objetivosEstudiante;

    public CursoPersonalizado(String codigo, String nombre, Idioma idioma, String descripcion,
                              int duracionMeses, double valorMensual, EstadoCurso estado,
                              Set<Beneficio> beneficios, int cantidadSesiones,
                              NivelReferencia nivelRequerido, String objetivosEstudiante) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, estado, beneficios);
        this.cantidadSesiones = Validaciones.enteroPositivo(cantidadSesiones, "cantidad de sesiones");
        this.nivelRequerido = Validaciones.requerido(nivelRequerido, "nivel de referencia");
        this.objetivosEstudiante = Validaciones.texto(objetivosEstudiante, "objetivos del estudiante");
    }

    @Override
    public TipoCurso getTipo() {
        return TipoCurso.PERSONALIZADO;
    }

    @Override
    public double calcularValorBase() {
        return getValorMensual() * getDuracionMeses();
    }

    public int getCantidadSesiones() {
        return cantidadSesiones;
    }

    public NivelReferencia getNivelRequerido() {
        return nivelRequerido;
    }

    public String getObjetivosEstudiante() {
        return objetivosEstudiante;
    }
}

