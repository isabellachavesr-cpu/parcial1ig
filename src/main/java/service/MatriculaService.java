package service;
import model.Asignacion;
import model.Curso;
import model.CursoPersonalizado;
import model.Estudiante;
import model.Matricula;
import model.Profesor;
import model.ServicioAdicional;
import repository.AsignacionRepository;
import repository.MatriculaRepository;
import java.time.LocalDate;
import java.util.List;


public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AsignacionRepository asignacionRepository;

    public MatriculaService(MatriculaRepository matriculaRepository,
                            AsignacionRepository asignacionRepository) {
        this.matriculaRepository = matriculaRepository;
        this.asignacionRepository = asignacionRepository;
    }

    public Matricula matricular(Estudiante estudiante, Curso curso, LocalDate fecha,
                                List<ServicioAdicional> servicios, double descuentoPorcentaje,
                                Profesor profesorAsignado) {

        Matricula matricula = new Matricula.Builder()
                .estudiante(estudiante)
                .curso(curso)
                .fechaMatricula(fecha)
                .servicios(servicios)
                .descuentoPorcentaje(descuentoPorcentaje)
                .build();

        matriculaRepository.guardar(matricula);

        if (curso instanceof CursoPersonalizado personalizado && profesorAsignado != null) {
            asignacionRepository.guardar(
                    new Asignacion(estudiante, personalizado, profesorAsignado, fecha));
        }

        return matricula;
    }

    public List<Matricula> listarTodas() {
        return matriculaRepository.listarTodas();
    }

    public List<Matricula> listarPorEstudiante(String documentoIdentidad) {
        return matriculaRepository.listarPorEstudiante(documentoIdentidad);
    }
}
