package repository;

import model.Idioma;
import model.Profesor;
import java.util.List;
import java.util.Optional;

public interface ProfesorRepository {

    void guardar(Profesor profesor);

    Optional<Profesor> buscarPorIdentificacion(String identificacion);

    List<Profesor> listarTodos();

    List<Profesor> listarPorIdioma(Idioma idioma);
}
