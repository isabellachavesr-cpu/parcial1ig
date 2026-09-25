package repository;

import model.Estudiante;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EstudianteRepositoryMemoria implements EstudianteRepository {

    private final List<Estudiante> estudiantes = new ArrayList<>();

    @Override
    public void guardar(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }

    @Override
    public Optional<Estudiante> buscarPorDocumento(String documentoIdentidad) {
        return estudiantes.stream()
                .filter(e -> e.getDocumentoIdentidad().equalsIgnoreCase(documentoIdentidad))
                .findFirst();
    }

    @Override
    public List<Estudiante> listarTodos() {
        return estudiantes;
    }

    @Override
    public boolean existe(String documentoIdentidad) {
        return buscarPorDocumento(documentoIdentidad).isPresent();
    }
}
