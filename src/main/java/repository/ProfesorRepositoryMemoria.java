package repository;

import model.Idioma;
import model.Profesor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProfesorRepositoryMemoria implements ProfesorRepository {

    private final List<Profesor> profesores = new ArrayList<>();

    @Override
    public void guardar(Profesor profesor) {
        profesores.add(profesor);
    }

    @Override
    public Optional<Profesor> buscarPorIdentificacion(String identificacion) {
        return profesores.stream()
                .filter(p -> p.getIdentificacion().equalsIgnoreCase(identificacion))
                .findFirst();
    }

    @Override
    public List<Profesor> listarTodos() {
        return profesores;
    }

    @Override
    public List<Profesor> listarPorIdioma(Idioma idioma) {
        return profesores.stream()
                .filter(p -> p.getIdiomaQueEnsenia() == idioma)
                .collect(Collectors.toList());
    }
}
