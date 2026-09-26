package model;
import java.util.ArrayList;
import java.util.List;

public abstract class Curso {

    private final String codigo;
    private String nombre;
    private final Idioma idioma;
    private String descripcion;
    private int duracionMeses;
    private double valorMensual;
    private EstadoCurso estado;
    private final List<Beneficio> beneficios = new ArrayList<>();

    protected Curso(String codigo, String nombre, Idioma idioma, String descripcion,
                    int duracionMeses, double valorMensual) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.idioma = idioma;
        this.descripcion = descripcion;
        this.duracionMeses = duracionMeses;
        this.valorMensual = valorMensual;
        this.estado = EstadoCurso.ACTIVO;
    }


    public abstract String getTipo();


    public double calcularValorBase() {
        return duracionMeses * valorMensual;
    }

    public void agregarBeneficio(Beneficio beneficio) {
        beneficios.add(beneficio);
    }

    public List<Beneficio> getBeneficios() {
        return beneficios;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public void setDuracionMeses(int duracionMeses) {
        this.duracionMeses = duracionMeses;
    }

    public double getValorMensual() {
        return valorMensual;
    }

    public void setValorMensual(double valorMensual) {
        this.valorMensual = valorMensual;
    }

    public EstadoCurso getEstado() {
        return estado;
    }

    public void setEstado(EstadoCurso estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "[" + getTipo() + "] " + codigo + " - " + nombre + " (" + idioma + ")";
    }
}
