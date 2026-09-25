package repository;

import model.Curso;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CursoRepositoryMemoria implements CursoRepository {

    private final List<Curso> cursos = new ArrayList<>();

    @Override
    public void guardar(Curso curso) {
        cursos.add(curso);
    }

    @Override
    public Optional<Curso> buscarPorCodigo(String codigo) {
        return cursos.stream()
                .filter(c -> c.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
    }

    @Override
    public List<Curso> listarTodos() {
        return cursos;
    }
}