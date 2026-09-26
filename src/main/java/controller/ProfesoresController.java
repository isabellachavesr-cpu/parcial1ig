package controller;

import com.example.parcial1ig.AppContext;
import model.Idioma;
import model.Profesor;
import service.ProfesorService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProfesoresController implements ContextAware {

    @FXML private TextField txtIdentificacion;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<Idioma> comboIdioma;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtTarifa;

    @FXML private TableView<Profesor> tablaProfesores;
    @FXML private TableColumn<Profesor, String> colIdentificacion;
    @FXML private TableColumn<Profesor, String> colNombre;
    @FXML private TableColumn<Profesor, String> colIdioma;
    @FXML private TableColumn<Profesor, String> colTelefono;
    @FXML private TableColumn<Profesor, String> colTarifa;

    private final ObservableList<Profesor> datos = FXCollections.observableArrayList();
    private ProfesorService profesorService;

    @Override
    public void setContext(AppContext context) {
        this.profesorService = context.getProfesorService();
        comboIdioma.setItems(FXCollections.observableArrayList(Idioma.values()));
        configurarTabla();
        refrescarTabla();
    }

    private void configurarTabla() {
        colIdentificacion.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getIdentificacion()));
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colIdioma.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getIdiomaQueEnsenia().toString()));
        colTelefono.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTelefono()));
        colTarifa.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getTarifaPorSesion())));
        tablaProfesores.setItems(datos);
    }

    private void refrescarTabla() {
        datos.setAll(profesorService.listarTodos());
    }

    @FXML
    private void onRegistrar() {
        try {
            if (comboIdioma.getValue() == null) {
                mostrarError("Seleccione el idioma que ensenia el profesor.");
                return;
            }
            double tarifa = Double.parseDouble(txtTarifa.getText().trim());
            profesorService.registrar(
                    txtIdentificacion.getText().trim(),
                    txtNombre.getText().trim(),
                    comboIdioma.getValue(),
                    txtTelefono.getText().trim(),
                    tarifa
            );
            limpiarFormulario();
            refrescarTabla();
        } catch (NumberFormatException e) {
            mostrarError("La tarifa por sesion debe ser un valor numerico.");
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    private void onLimpiar() {
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtIdentificacion.clear();
        txtNombre.clear();
        comboIdioma.getSelectionModel().clearSelection();
        txtTelefono.clear();
        txtTarifa.clear();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR, mensaje);
        alert.setHeaderText("No fue posible registrar el profesor");
        alert.showAndWait();
    }
}

