package model;
import java.util.Set;

/** Curso intensivo: tiene un recargo del 25% sobre el valor regular por su mayor carga horaria. */
public class CursoIntensivo extends Curso {

    public static final double RECARGO_INTENSIVO = 0.25;

    public CursoIntensivo(String codigo, String nombre, Idioma idioma, String descripcion,
                          int duracionMeses, double valorMensual, EstadoCurso estado,
                          Set<Beneficio> beneficios) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, estado, beneficios);
    }

    @Override
    public TipoCurso getTipo() {
        return TipoCurso.INTENSIVO;
    }

    @Override
    public double calcularValorBase() {
        return getValorMensual() * getDuracionMeses() * (1 + RECARGO_INTENSIVO);
    }
}