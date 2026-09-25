package repository;

import model.Estudiante;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
