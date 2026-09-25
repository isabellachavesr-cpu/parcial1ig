package model;
import java.util.Set;

/** Curso grupal regular: valor mensual por la duración contratada. */
public class CursoRegular extends Curso {

    public CursoRegular(String codigo, String nombre, Idioma idioma, String descripcion,
                        int duracionMeses, double valorMensual, EstadoCurso estado,
                        Set<Beneficio> beneficios) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, estado, beneficios);
    }

    @Override
    public TipoCurso getTipo() {
        return TipoCurso.REGULAR;
    }

    @Override
    public double calcularValorBase() {
        return getValorMensual() * getDuracionMeses();
    }
}
