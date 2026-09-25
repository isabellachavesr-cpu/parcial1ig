package factory;
import model.Curso;
import model.CursoPersonalizado;

public class CursoPersonalizadoFactory extends CursoFactory {

    @Override
    public Curso crearCurso(DatosCurso datos) {
        return new CursoPersonalizado(
                datos.codigo,
                datos.nombre,
                datos.idioma,
                datos.descripcion,
                datos.duracionMeses,
                datos.valorMensual,
                datos.cantidadSesiones,
                datos.nivelReferencia,
                datos.objetivosEstudiante
        );
    }
}

