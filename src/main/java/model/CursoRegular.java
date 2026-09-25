package model;

public class CursoRegular extends Curso {

    public CursoRegular(String codigo, String nombre, Idioma idioma, String descripcion,
                        int duracionMeses, double valorMensual) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual);
    }

    @Override
    public String getTipo() {
        return "Regular";
    }
}