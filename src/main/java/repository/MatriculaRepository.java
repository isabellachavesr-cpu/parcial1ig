package repository;

import model.Matricula;
import java.time.LocalDate;
import java.util.List;

public interface MatriculaRepository {

    void guardar(Matricula matricula);

    List<Matricula> listarTodas();

    List<Matricula> listarPorEstudiante(String documentoIdentidad);

    List<Matricula> listarPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin);
}
