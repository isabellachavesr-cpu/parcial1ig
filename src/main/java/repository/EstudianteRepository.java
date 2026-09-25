package repository;

import model.Estudiante;
import java.util.List;
import java.util.Optional;

public interface EstudianteRepository {

    void guardar(Estudiante estudiante);

    Optional<Estudiante> buscarPorDocumento(String documentoIdentidad);

    List<Estudiante> listarTodos();

    boolean existe(String documentoIdentidad);
}
