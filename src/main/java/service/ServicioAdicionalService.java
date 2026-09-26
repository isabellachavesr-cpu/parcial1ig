package service;
import model.ServicioAdicional;
import repository.ServicioAdicionalRepository;
import java.util.List;

public class ServicioAdicionalService {

    private final ServicioAdicionalRepository repository;

    public ServicioAdicionalService(ServicioAdicionalRepository repository) {
        this.repository = repository;
    }

    public ServicioAdicional registrar(String codigo, String nombre, String descripcion,
                                       double precio, boolean disponibilidad) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El codigo del servicio es obligatorio.");
        }
        ServicioAdicional servicio = new ServicioAdicional(codigo, nombre, descripcion, precio, disponibilidad);
        repository.guardar(servicio);
        return servicio;
    }

    public List<ServicioAdicional> listarTodos() {
        return repository.listarTodos();
    }

    public List<ServicioAdicional> listarDisponibles() {
        return repository.listarTodos().stream().filter(ServicioAdicional::isDisponibilidad).toList();
    }
}
