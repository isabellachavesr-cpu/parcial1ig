package controller;

import com.example.parcial1ig.AppContext;
import model.Curso;
import model.CursoPersonalizado;
import model.Estudiante;
import model.Matricula;
import model.Profesor;
import model.ServicioAdicional;
import service.CursoService;
import service.EstudianteService;
import service.MatriculaService;
import service.ProfesorService;
import service.ServicioAdicionalService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.util.List;

public class MatriculasController implements ContextAware, Refrescable {

    @FXML private ComboBox<Estudiante> comboEstudiante;
    @FXML private ComboBox<Curso> comboCurso;
    @FXML private ListView<ServicioAdicional> listaServicios;
    @FXML private TextField txtDescuento;

    @FXML private VBox panelProfesor;
    @FXML private ComboBox<Profesor> comboProfesor;

    @FXML private Label lblValorEstimado;

    @FXML private TableView<Matricula> tablaMatriculas;
    @FXML private TableColumn<Matricula, String> colId;
    @FXML private TableColumn<Matricula, String> colEstudiante;
    @FXML private TableColumn<Matricula, String> colCurso;
    @FXML private TableColumn<Matricula, String> colFecha;
    @FXML private TableColumn<Matricula, String> colServicios;
    @FXML private TableColumn<Matricula, String> colDescuento;
    @FXML private TableColumn<Matricula, String> colValorFinal;

    private final ObservableList<Matricula> datos = FXCollections.observableArrayList();

    private EstudianteService estudianteService;
    private CursoService cursoService;
    private ServicioAdicionalService servicioAdicionalService;
    private ProfesorService profesorService;
    private MatriculaService matriculaService;

    @Override
    public void setContext(AppContext context) {
        this.estudianteService = context.getEstudianteService();
        this.cursoService = context.getCursoService();
        this.servicioAdicionalService = context.getServicioAdicionalService();
        this.profesorService = context.getProfesorService();
        this.matriculaService = context.getMatriculaService();

        listaServicios.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(ServicioAdicional item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });

        comboCurso.valueProperty().addListener((obs, anterior, nuevo) -> actualizarVisibilidadProfesor(nuevo));

        configurarTabla();
        actualizar();
    }

    @Override
    public void actualizar() {
        comboEstudiante.setItems(FXCollections.observableArrayList(estudianteService.listarTodos()));
        comboCurso.setItems(FXCollections.observableArrayList(cursoService.listarTodos()));
        listaServicios.setItems(FXCollections.observableArrayList(servicioAdicionalService.listarDisponibles()));
        listaServicios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        actualizarVisibilidadProfesor(comboCurso.getValue());
        refrescarTablaMatriculas();
    }

    private void actualizarVisibilidadProfesor(Curso curso) {
        boolean personalizado = curso instanceof CursoPersonalizado;
        panelProfesor.setVisible(personalizado);
        panelProfesor.setManaged(personalizado);
        if (personalizado) {
            comboProfesor.setItems(FXCollections.observableArrayList(
                    profesorService.listarPorIdioma(curso.getIdioma())));
        }
    }

    private void configurarTabla() {
        colId.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getId())));
        colEstudiante.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstudiante().getNombreCompleto()));
        colCurso.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCurso().getNombre()));
        colFecha.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFechaMatricula().toString()));
        colServicios.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getServiciosAdicionales().isEmpty() ? "-" :
                        d.getValue().getServiciosAdicionales().size() + " servicio(s)"));
        colDescuento.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDescuentoPorcentaje() + " %"));
        colValorFinal.setCellValueFactory(d -> new SimpleStringProperty(
                String.format("$%.2f", d.getValue().getValorFinal())));
        tablaMatriculas.setItems(datos);
    }

    private void refrescarTablaMatriculas() {
        datos.setAll(matriculaService.listarTodas());
    }

    @FXML
    private void onMatricular() {
        try {
            Estudiante estudiante = comboEstudiante.getValue();
            Curso curso = comboCurso.getValue();
            if (estudiante == null || curso == null) {
                mostrarError("Seleccione un estudiante y un curso.");
                return;
            }

            double descuento = txtDescuento.getText().isBlank() ? 0
                    : Double.parseDouble(txtDescuento.getText().trim());

            List<ServicioAdicional> servicios = listaServicios.getSelectionModel().getSelectedItems();

            Profesor profesor = null;
            if (curso instanceof CursoPersonalizado) {
                profesor = comboProfesor.getValue();
                if (profesor == null) {
                    mostrarError("Los cursos personalizados requieren asignar un profesor.");
                    return;
                }
            }

            Matricula matricula = matriculaService.matricular(
                    estudiante, curso, LocalDate.now(), servicios, descuento, profesor);

            lblValorEstimado.setText("Valor final de la matricula: $" + String.format("%.2f", matricula.getValorFinal()));
            limpiarFormulario();
            refrescarTablaMatriculas();
        } catch (NumberFormatException e) {
            mostrarError("El descuento debe ser un valor numerico.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarError(e.getMessage());
        }
    }

    private void limpiarFormulario() {
        comboEstudiante.getSelectionModel().clearSelection();
        comboCurso.getSelectionModel().clearSelection();
        listaServicios.getSelectionModel().clearSelection();
        txtDescuento.clear();
        comboProfesor.getSelectionModel().clearSelection();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR, mensaje);
        alert.setHeaderText("No fue posible registrar la matricula");
        alert.showAndWait();
    }
}
