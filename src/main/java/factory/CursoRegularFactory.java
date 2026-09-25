package factory;
import model.Curso;
import model.CursoRegular;


public class CursoRegularFactory extends CursoFactory {

    @Override
    public Curso crearCurso(DatosCurso datos) {
        return new CursoRegular(
                datos.codigo,
                datos.nombre,
                datos.idioma,
                datos.descripcion,
                datos.duracionMeses,
                datos.valorMensual
        );
    }
}

