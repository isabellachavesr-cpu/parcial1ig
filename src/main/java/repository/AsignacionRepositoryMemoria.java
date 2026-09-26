package repository;

import model.Asignacion;
import java.util.ArrayList;
import java.util.List;


public class AsignacionRepositoryMemoria implements AsignacionRepository {

    private final List<Asignacion> asignaciones = new ArrayList<>();

    @Override
    public void guardar(Asignacion asignacion) {
        asignaciones.add(asignacion);
    }

    @Override
    public List<Asignacion> listarTodas() {
        return asignaciones;
    }
}
