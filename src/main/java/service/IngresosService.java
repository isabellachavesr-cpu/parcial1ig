package service;

import model.Matricula;
import repository.MatriculaRepository;
import java.time.LocalDate;


public class IngresosService {

    private final MatriculaRepository matriculaRepository;

    public IngresosService(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    public double calcularIngresosPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha inicial no puede ser posterior a la fecha final.");
        }
        double total = 0;
        for (Matricula matricula : matriculaRepository.listarPorPeriodo(fechaInicio, fechaFin)) {
            total += matricula.getValorFinal();
        }
        return total;
    }
}
