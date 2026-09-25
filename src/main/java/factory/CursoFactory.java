package factory;
import model.Curso;

public abstract class CursoFactory {

    /** Metodo fabrica: cada subclase decide que tipo concreto de Curso construir. */
    public abstract Curso crearCurso(DatosCurso datos);
}
