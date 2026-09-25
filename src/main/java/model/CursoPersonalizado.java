package model;

public class CursoPersonalizado extends Curso {

    private int cantidadSesiones;
    private NivelReferencia nivelReferencia;
    private String objetivosEstudiante;

    public CursoPersonalizado(String codigo, String nombre, Idioma idioma, String descripcion,
                              int duracionMeses, double valorMensual, int cantidadSesiones,
                              NivelReferencia nivelReferencia, String objetivosEstudiante) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual);
        this.cantidadSesiones = cantidadSesiones;
        this.nivelReferencia = nivelReferencia;
        this.objetivosEstudiante = objetivosEstudiante;
    }

    @Override
    public String getTipo() {
        return "Personalizado";
    }

    public int getCantidadSesiones() {
        return cantidadSesiones;
    }

    public void setCantidadSesiones(int cantidadSesiones) {
        this.cantidadSesiones = cantidadSesiones;
    }

    public NivelReferencia getNivelReferencia() {
        return nivelReferencia;
    }

    public void setNivelReferencia(NivelReferencia nivelReferencia) {
        this.nivelReferencia = nivelReferencia;
    }

    public String getObjetivosEstudiante() {
        return objetivosEstudiante;
    }

    public void setObjetivosEstudiante(String objetivosEstudiante) {
        this.objetivosEstudiante = objetivosEstudiante;
    }

    /**
     * Patron Prototype: clona la configuracion de este curso personalizado
     * (idioma, duracion, valor mensual, cantidad de sesiones, nivel y
     * beneficios) para crear rapidamente uno nuevo con distinto codigo y
     * objetivos, sin tener que rellenar de nuevo todo el formulario.
     */
    public CursoPersonalizado clonarPlantilla(String nuevoCodigo, String nuevosObjetivos) {
        CursoPersonalizado clon = new CursoPersonalizado(
                nuevoCodigo,
                this.getNombre(),
                this.getIdioma(),
                this.getDescripcion(),
                this.getDuracionMeses(),
                this.getValorMensual(),
                this.cantidadSesiones,
                this.nivelReferencia,
                nuevosObjetivos
        );
        for (Beneficio b : this.getBeneficios()) {
            clon.agregarBeneficio(b);
        }
        return clon;
    }
}
