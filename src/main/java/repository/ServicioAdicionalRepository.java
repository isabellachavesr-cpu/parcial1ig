package repository;

import model.ServicioAdicional;
import java.util.List;
import java.util.Optional;

public interface ServicioAdicionalRepository {

    void guardar(ServicioAdicional servicio);

    Optional<ServicioAdicional> buscarPorCodigo(String codigo);

    List<ServicioAdicional> listarTodos();
}
