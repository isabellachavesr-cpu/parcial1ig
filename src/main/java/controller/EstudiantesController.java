package controller;

import com.example.parcial1ig.AppContext;
import model.Estudiante;
import service.EstudianteService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class EstudiantesController implements ContextAware {

    @FXML private TextField txtNombre;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtEdad;

    @FXML private TableView<Estudiante> tablaEstudiantes;
    @FXML private TableColumn<Estudiante, String> colNombre;
    @FXML private TableColumn<Estudiante, String> colDocumento;
    @FXML private TableColumn<Estudiante, String> colTelefono;
    @FXML private TableColumn<Estudiante, String> colCorreo;
    @FXML private TableColumn<Estudiante, String> colEdad;
    @FXML private TableColumn<Estudiante, String> colFechaRegistro;

    private final ObservableList<Estudiante> datos = FXCollections.observableArrayList();
    private EstudianteService estudianteService;

    @Override
    public void setContext(AppContext context) {
        this.estudianteService = context.getEstudianteService();
        configurarTabla();
        refrescarTabla();
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombreCompleto()));
        colDocumento.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDocumentoIdentidad()));
        colTelefono.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTelefono()));
        colCorreo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCorreo()));
        colEdad.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getEdad())));
        colFechaRegistro.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFechaRegistro().toString()));
        tablaEstudiantes.setItems(datos);
    }

    private void refrescarTabla() {
        datos.setAll(estudianteService.listarTodos());
    }

    @FXML
    private void onRegistrar() {
        try {
            int edad = Integer.parseInt(txtEdad.getText().trim());
            estudianteService.registrar(
                    txtNombre.getText().trim(),
                    txtDocumento.getText().trim(),
                    txtTelefono.getText().trim(),
                    txtCorreo.getText().trim(),
                    edad
            );
            limpiarFormulario();
            refrescarTabla();
        } catch (NumberFormatException e) {
            mostrarError("La edad debe ser un numero entero.");
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    private void onLimpiar() {
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtDocumento.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtEdad.clear();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR, mensaje);
        alert.setHeaderText("No fue posible registrar el estudiante");
        alert.showAndWait();
    }
}
