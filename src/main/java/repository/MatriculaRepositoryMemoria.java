package repository;

import model.Matricula;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatriculaRepositoryMemoria implements MatriculaRepository {

    private final List<Matricula> matriculas = new ArrayList<>();

    @Override
    public void guardar(Matricula matricula) {
        matriculas.add(matricula);
    }

    @Override
    public List<Matricula> listarTodas() {
        return matriculas;
    }

    @Override
    public List<Matricula> listarPorEstudiante(String documentoIdentidad) {
        return matriculas.stream()
                .filter(m -> m.getEstudiante().getDocumentoIdentidad().equalsIgnoreCase(documentoIdentidad))
                .collect(Collectors.toList());
    }

    @Override
    public List<Matricula> listarPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        return matriculas.stream()
                .filter(m -> !m.getFechaMatricula().isBefore(fechaInicio)
                        && !m.getFechaMatricula().isAfter(fechaFin))
                .collect(Collectors.toList());
    }
}
