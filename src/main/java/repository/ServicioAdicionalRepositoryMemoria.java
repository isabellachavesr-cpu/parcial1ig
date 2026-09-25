package repository;

import model.ServicioAdicional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServicioAdicionalRepositoryMemoria implements ServicioAdicionalRepository {

    private final List<ServicioAdicional> servicios = new ArrayList<>();

    @Override
    public void guardar(ServicioAdicional servicio) {
        servicios.add(servicio);
    }

    @Override
    public Optional<ServicioAdicional> buscarPorCodigo(String codigo) {
        return servicios.stream()
                .filter(s -> s.getCodigo().equalsIgnoreCase(codigo))
                .findFirst();
    }

    @Override
    public List<ServicioAdicional> listarTodos() {
        return servicios;
    }
}
