package service;
import model.Idioma;
import model.Profesor;
import repository.ProfesorRepository;
import java.util.List;

public class ProfesorService {

    private final ProfesorRepository repository;

    public ProfesorService(ProfesorRepository repository) {
        this.repository = repository;
    }

    public Profesor registrar(String identificacion, String nombre, Idioma idioma,
                              String telefono, double tarifaPorSesion) {
        if (identificacion == null || identificacion.isBlank()) {
            throw new IllegalArgumentException("La identificacion del profesor es obligatoria.");
        }
        Profesor profesor = new Profesor(identificacion, nombre, idioma, telefono, tarifaPorSesion);
        repository.guardar(profesor);
        return profesor;
    }

    public List<Profesor> listarTodos() {
        return repository.listarTodos();
    }

    public List<Profesor> listarPorIdioma(Idioma idioma) {
        return repository.listarPorIdioma(idioma);
    }
}
