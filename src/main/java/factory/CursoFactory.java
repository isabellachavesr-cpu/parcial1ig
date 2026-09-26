package factory;
import model.Curso;

public abstract class CursoFactory {

    public abstract Curso crearCurso(DatosCurso datos);
}
