package factory;
import model.Curso;
import model.CursoIntensivo;

public class CursoIntensivoFactory extends CursoFactory {

    @Override
    public Curso crearCurso(DatosCurso datos) {
        return new CursoIntensivo(
                datos.codigo,
                datos.nombre,
                datos.idioma,
                datos.descripcion,
                datos.duracionMeses,
                datos.valorMensual
        );
    }
}
