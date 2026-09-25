package repository;

import model.Asignacion;
import java.util.List;

public interface AsignacionRepository {

    void guardar(Asignacion asignacion);

    List<Asignacion> listarTodas();
}
