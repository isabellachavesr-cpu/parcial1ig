package model;
import co.edu.uniquindio.lenguajecafetero.exception.AcademiaException;
import co.edu.uniquindio.lenguajecafetero.exception.RegistroDuplicadoException;
import co.edu.uniquindio.lenguajecafetero.exception.RegistroNoEncontradoException;
import co.edu.uniquindio.lenguajecafetero.model.descuento.DescuentoPorDuracion;
import co.edu.uniquindio.lenguajecafetero.model.descuento.DescuentoPorcentaje;
import co.edu.uniquindio.lenguajecafetero.model.descuento.PoliticaDescuento;
import co.edu.uniquindio.lenguajecafetero.model.descuento.SinDescuento;
import co.edu.uniquindio.lenguajecafetero.util.Validaciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class Academia {

    private final String nombreComercial;
    private final String nit;
    private final String direccion;
    private final String telefono;
    private final String correo;
    private final String paginaWeb;

    private final List<Estudiante> estudiantes = new ArrayList<>();
    private final List<Profesor> profesores = new ArrayList<>();
    private final List<Curso> cursos = new ArrayList<>();
    private final List<ServicioAdicional> serviciosAdicionales = new ArrayList<>();
    private final List<Matricula> matriculas = new ArrayList<>();
    private final List<PoliticaDescuento> politicasDescuento = new ArrayList<>();

    private int contadorMatriculas;

    private static class Holder {
        private static final Academia INSTANCIA = new Academia();
    }

    private Academia() {

        this.nombreComercial = "LenguajeCafetero";
        this.nit = "900.123.456-7";
        this.direccion = "Carrera 14 # 12-30, Armenia, Quindío";
        this.telefono = "(606) 741 0000";
        this.correo = "info@lenguajecafetero.com";
        this.paginaWeb = "www.lenguajecafetero.com";

        politicasDescuento.add(new SinDescuento());
        politicasDescuento.add(new DescuentoPorDuracion());
        politicasDescuento.add(new DescuentoPorcentaje("Promoción de temporada", 10));
        politicasDescuento.add(new DescuentoPorcentaje("Estudiante fiel", 5));
    }

    public static Academia getInstance() {
        return Holder.INSTANCIA;
    }

    // ------------------------------------------------------------- Estudiantes

    public void registrarEstudiante(Estudiante estudiante) {
        Validaciones.requerido(estudiante, "estudiante");
        if (buscarEstudiantePorDocumento(estudiante.getDocumento()).isPresent()) {
            throw new RegistroDuplicadoException("Ya existe un estudiante con el documento "
                    + estudiante.getDocumento() + ".");
        }
        estudiantes.add(estudiante);
    }

    /** Búsqueda de un estudiante mediante su documento de identidad. */
    public Optional<Estudiante> buscarEstudiantePorDocumento(String documento) {
        if (documento == null || documento.isBlank()) {
            return Optional.empty();
        }
        String buscado = documento.trim();
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getDocumento().equals(buscado)) {
                return Optional.of(estudiante);
            }
        }
        return Optional.empty();
    }

    // ---------------------------------------------------------------- Profesores

    public void registrarProfesor(Profesor profesor) {
        Validaciones.requerido(profesor, "profesor");
        if (buscarProfesorPorIdentificacion(profesor.getIdentificacion()).isPresent()) {
            throw new RegistroDuplicadoException("Ya existe un profesor con la identificación "
                    + profesor.getIdentificacion() + ".");
        }
        profesores.add(profesor);
    }

    public Optional<Profesor> buscarProfesorPorIdentificacion(String identificacion) {
        if (identificacion == null) {
            return Optional.empty();
        }
        return profesores.stream()
                .filter(p -> p.getIdentificacion().equals(identificacion.trim()))
                .findFirst();
    }

    // -------------------------------------------------------------------- Cursos

    public void registrarCurso(Curso curso) {
        Validaciones.requerido(curso, "curso");
        if (buscarCursoPorCodigo(curso.getCodigo()).isPresent()) {
            throw new RegistroDuplicadoException("Ya existe un curso con el código " + curso.getCodigo() + ".");
        }
        cursos.add(curso);
    }

    public Optional<Curso> buscarCursoPorCodigo(String codigo) {
        if (codigo == null) {
            return Optional.empty();
        }
        return cursos.stream()
                .filter(c -> c.getCodigo().equalsIgnoreCase(codigo.trim()))
                .findFirst();
    }

    // ------------------------------------------------------ Servicios adicionales

    public void registrarServicioAdicional(ServicioAdicional servicio) {
        Validaciones.requerido(servicio, "servicio adicional");
        boolean existe = serviciosAdicionales.stream()
                .anyMatch(s -> s.getCodigo().equalsIgnoreCase(servicio.getCodigo()));
        if (existe) {
            throw new RegistroDuplicadoException("Ya existe un servicio adicional con el código "
                    + servicio.getCodigo() + ".");
        }
        serviciosAdicionales.add(servicio);
    }

    // ----------------------------------------------------------------- Matrículas

    /** Código que tendrá la próxima matrícula (no consume el consecutivo). */
    public String proximoCodigoMatricula() {
        return String.format("MAT-%04d", contadorMatriculas + 1);
    }

    public void registrarMatricula(Matricula matricula) {
        Validaciones.requerido(matricula, "matrícula");

        if (!estudiantes.contains(matricula.getEstudiante())) {
            throw new RegistroNoEncontradoException("El estudiante no está registrado en la academia.");
        }
        if (!cursos.contains(matricula.getCurso())) {
            throw new RegistroNoEncontradoException("El curso no está registrado en la academia.");
        }
        Asignacion asignacion = matricula.getAsignacion();
        if (asignacion != null && !profesores.contains(asignacion.getProfesor())) {
            throw new RegistroNoEncontradoException("El profesor no está registrado en la academia.");
        }
        for (Matricula existente : matriculas) {
            if (existente.getCodigo().equals(matricula.getCodigo())) {
                throw new RegistroDuplicadoException("Ya existe la matrícula " + matricula.getCodigo() + ".");
            }
            if (existente.getEstudiante().equals(matricula.getEstudiante())
                    && existente.getCurso().equals(matricula.getCurso())) {
                throw new RegistroDuplicadoException("El estudiante ya está matriculado en ese curso.");
            }
        }

        matriculas.add(matricula);
        contadorMatriculas++;
        matricula.getEstudiante().agregarMatricula(matricula);
        if (asignacion != null) {
            asignacion.getProfesor().agregarAsignacion(asignacion);
        }
    }

    /**
     * Recorre las matrículas registradas y devuelve las realizadas dentro del periodo
     * (ambas fechas incluidas).
     */
    public List<Matricula> obtenerMatriculasEnPeriodo(LocalDate fechaInicial, LocalDate fechaFinal) {
        Validaciones.requerido(fechaInicial, "fecha inicial");
        Validaciones.requerido(fechaFinal, "fecha final");
        if (fechaInicial.isAfter(fechaFinal)) {
            throw new AcademiaException("La fecha inicial no puede ser posterior a la fecha final.");
        }
        List<Matricula> enPeriodo = new ArrayList<>();
        for (Matricula matricula : matriculas) {
            LocalDate fecha = matricula.getFechaMatricula();
            if (!fecha.isBefore(fechaInicial) && !fecha.isAfter(fechaFinal)) {
                enPeriodo.add(matricula);
            }
        }
        return enPeriodo;
    }

    /** Ingresos generados por las matrículas realizadas en el periodo consultado. */
    public double calcularIngresos(LocalDate fechaInicial, LocalDate fechaFinal) {
        double total = 0;
        for (Matricula matricula : obtenerMatriculasEnPeriodo(fechaInicial, fechaFinal)) {
            total += matricula.calcularValorTotal();
        }
        return total;
    }

    // -------------------------------------------------------------------- Getters

    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public List<Estudiante> getEstudiantes() {
        return Collections.unmodifiableList(estudiantes);
    }

    public List<Profesor> getProfesores() {
        return Collections.unmodifiableList(profesores);
    }

    public List<Curso> getCursos() {
        return Collections.unmodifiableList(cursos);
    }

    public List<ServicioAdicional> getServiciosAdicionales() {
        return Collections.unmodifiableList(serviciosAdicionales);
    }

    public List<Matricula> getMatriculas() {
        return Collections.unmodifiableList(matriculas);
    }

    public List<PoliticaDescuento> getPoliticasDescuento() {
        return Collections.unmodifiableList(politicasDescuento);
    }
}
