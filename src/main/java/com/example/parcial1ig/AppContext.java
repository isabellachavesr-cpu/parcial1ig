package com.example.parcial1ig;

import model.Academia;
import model.TipoCurso;

import abstractfactory.FabricaBeneficios;
import abstractfactory.FabricaBeneficiosIntensivo;
import abstractfactory.FabricaBeneficiosPersonalizado;
import abstractfactory.FabricaBeneficiosRegular;

import factory.CursoFactory;
import factory.CursoIntensivoFactory;
import factory.CursoPersonalizadoFactory;
import factory.CursoRegularFactory;

import repository.AsignacionRepository;
import repository.AsignacionRepositoryMemoria;
import repository.CursoRepository;
import repository.CursoRepositoryMemoria;
import repository.EstudianteRepository;
import repository.EstudianteRepositoryMemoria;
import repository.MatriculaRepository;
import repository.MatriculaRepositoryMemoria;
import repository.ProfesorRepository;
import repository.ProfesorRepositoryMemoria;
import repository.ServicioAdicionalRepository;
import repository.ServicioAdicionalRepositoryMemoria;

import service.CursoService;
import service.EstudianteService;
import service.IngresosService;
import service.MatriculaService;
import service.ProfesorService;
import service.ServicioAdicionalService;

import java.util.EnumMap;
import java.util.Map;


public class AppContext {

    private final Academia academia;

    private final EstudianteService estudianteService;
    private final ProfesorService profesorService;
    private final CursoService cursoService;
    private final ServicioAdicionalService servicioAdicionalService;
    private final MatriculaService matriculaService;
    private final IngresosService ingresosService;

    public AppContext() {
        this.academia = Academia.getInstance();

        EstudianteRepository estudianteRepository = new EstudianteRepositoryMemoria();
        ProfesorRepository profesorRepository = new ProfesorRepositoryMemoria();
        CursoRepository cursoRepository = new CursoRepositoryMemoria();
        ServicioAdicionalRepository servicioRepository = new ServicioAdicionalRepositoryMemoria();
        MatriculaRepository matriculaRepository = new MatriculaRepositoryMemoria();
        AsignacionRepository asignacionRepository = new AsignacionRepositoryMemoria();

        Map<TipoCurso, CursoFactory> fabricasCurso = new EnumMap<>(TipoCurso.class);
        fabricasCurso.put(TipoCurso.REGULAR, new CursoRegularFactory());
        fabricasCurso.put(TipoCurso.INTENSIVO, new CursoIntensivoFactory());
        fabricasCurso.put(TipoCurso.PERSONALIZADO, new CursoPersonalizadoFactory());

        Map<TipoCurso, FabricaBeneficios> fabricasBeneficios = new EnumMap<>(TipoCurso.class);
        fabricasBeneficios.put(TipoCurso.REGULAR, new FabricaBeneficiosRegular());
        fabricasBeneficios.put(TipoCurso.INTENSIVO, new FabricaBeneficiosIntensivo());
        fabricasBeneficios.put(TipoCurso.PERSONALIZADO, new FabricaBeneficiosPersonalizado());

        this.estudianteService = new EstudianteService(estudianteRepository);
        this.profesorService = new ProfesorService(profesorRepository);
        this.cursoService = new CursoService(cursoRepository, fabricasCurso, fabricasBeneficios);
        this.servicioAdicionalService = new ServicioAdicionalService(servicioRepository);
        this.matriculaService = new MatriculaService(matriculaRepository, asignacionRepository);
        this.ingresosService = new IngresosService(matriculaRepository);
    }

    public Academia getAcademia() {
        return academia;
    }

    public EstudianteService getEstudianteService() {
        return estudianteService;
    }

    public ProfesorService getProfesorService() {
        return profesorService;
    }

    public CursoService getCursoService() {
        return cursoService;
    }

    public ServicioAdicionalService getServicioAdicionalService() {
        return servicioAdicionalService;
    }

    public MatriculaService getMatriculaService() {
        return matriculaService;
    }

    public IngresosService getIngresosService() {
        return ingresosService;
    }
}
