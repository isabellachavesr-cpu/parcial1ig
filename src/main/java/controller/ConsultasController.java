package controller;

import model.Estudiante;
import model.Matricula;
import service.EstudianteService;
import service.IngresosService;
import service.MatriculaService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.util.Optional;


public class ConsultasController implements ContextAware, Refrescable {

    @FXML private TextField txtDocumentoBuscar;
    @FXML private Label lblResultadoEstudiante;

    @FXML private TableView<Matricula> tablaHistorial;
    @FXML private TableColumn<Matricula, String> colCurso;
    @FXML private TableColumn<Matricula, String> colFecha;
    @FXML private TableColumn<Matricula, String> colValorFinal;

    @FXML private DatePicker fechaInicio;
    @FXML private DatePicker fechaFin;
    @FXML private Label lblIngresos;

    private final ObservableList<Matricula> historial = FXCollections.observableArrayList();

    private EstudianteService estudianteService;
    private MatriculaService matriculaService;
    private IngresosService ingresosService;

    @Override
    public void setContext(AppContext context) {
        this.estudianteService = context.getEstudianteService();
        this.matriculaService = context.getMatriculaService();
        this.ingresosService = context.getIngresosService();

        colCurso.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCurso().getNombre()));
        colFecha.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFechaMatricula().toString()));
        colValorFinal.setCellValueFactory(d -> new SimpleStringProperty(
                String.format("$%.2f", d.getValue().getValorFinal())));
        tablaHistorial.setItems(historial);

        actualizar();
    }

    @Override
    public void actualizar() {
        historial.clear();
        lblResultadoEstudiante.setText("");
        lblIngresos.setText("");
    }

    @FXML
    private void onBuscarEstudiante() {
        String documento = txtDocumentoBuscar.getText().trim();
        if (documento.isEmpty()) {
            mostrarError("Escriba el documento de identidad a buscar.");
            return;
        }
        Optional<Estudiante> resultado = estudianteService.buscarPorDocumento(documento);
        if (resultado.isEmpty()) {
            lblResultadoEstudiante.setText("No se encontro ningun estudiante con ese documento.");
            historial.clear();
            return;
        }
        Estudiante estudiante = resultado.get();
        lblResultadoEstudiante.setText(estudiante.getNombreCompleto() + " | Tel: " + estudiante.getTelefono()
                + " | Correo: " + estudiante.getCorreo() + " | Edad: " + estudiante.getEdad());
        historial.setAll(matriculaService.listarPorEstudiante(documento));
    }

    @FXML
    private void onCalcularIngresos() {
        LocalDate inicio = fechaInicio.getValue();
        LocalDate fin = fechaFin.getValue();
        if (inicio == null || fin == null) {
            mostrarError("Seleccione la fecha inicial y la fecha final del periodo.");
            return;
        }
        try {
            double total = ingresosService.calcularIngresosPorPeriodo(inicio, fin);
            lblIngresos.setText("Ingresos generados entre " + inicio + " y " + fin
                    + ": $" + String.format("%.2f", total));
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR, mensaje);
        alert.setHeaderText("Consulta invalida");
        alert.showAndWait();
    }
}
