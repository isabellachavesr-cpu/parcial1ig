package repository;

import model.Curso;
import java.util.List;
import java.util.Optional;

public interface CursoRepository {

    void guardar(Curso curso);

    Optional<Curso> buscarPorCodigo(String codigo);

    List<Curso> listarTodos();
}
