package service;

import model.Estudiante;
import repository.EstudianteRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class EstudianteService {

    private final EstudianteRepository repository;

    public EstudianteService(EstudianteRepository repository) {
        this.repository = repository;
    }

    public Estudiante registrar(String nombreCompleto, String documentoIdentidad, String telefono,
                                String correo, int edad) {
        if (documentoIdentidad == null || documentoIdentidad.isBlank()) {
            throw new IllegalArgumentException("El documento de identidad es obligatorio.");
        }
        if (repository.existe(documentoIdentidad)) {
            throw new IllegalArgumentException("Ya existe un estudiante con ese documento.");
        }
        Estudiante estudiante = new Estudiante(nombreCompleto, documentoIdentidad, telefono,
                correo, edad, LocalDate.now());
        repository.guardar(estudiante);
        return estudiante;
    }

    public Optional<Estudiante> buscarPorDocumento(String documentoIdentidad) {
        return repository.buscarPorDocumento(documentoIdentidad);
    }

    public List<Estudiante> listarTodos() {
        return repository.listarTodos();
    }
}
