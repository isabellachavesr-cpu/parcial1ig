package factory;
import model.Idioma;
import model.NivelReferencia;

public class DatosCurso {

    public String codigo;
    public String nombre;
    public Idioma idioma;
    public String descripcion;
    public int duracionMeses;
    public double valorMensual;

    // Datos exclusivos de un curso personalizado
    public int cantidadSesiones;
    public NivelReferencia nivelReferencia;
    public String objetivosEstudiante;
}
