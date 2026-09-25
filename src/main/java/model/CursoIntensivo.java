package model;
public class CursoIntensivo extends Curso {

    public CursoIntensivo(String codigo, String nombre, Idioma idioma, String descripcion,
                          int duracionMeses, double valorMensual) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual);
    }

    @Override
    public String getTipo() {
        return "Intensivo";
    }
}
