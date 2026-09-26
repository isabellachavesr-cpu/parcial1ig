package controller;

import com.example.parcial1ig.AppContext;
import model.ServicioAdicional;
import service.ServicioAdicionalService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
public class ServiciosController implements ContextAware {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPrecio;
    @FXML private CheckBox chkDisponible;

    @FXML private TableView<ServicioAdicional> tablaServicios;
    @FXML private TableColumn<ServicioAdicional, String> colCodigo;
    @FXML private TableColumn<ServicioAdicional, String> colNombre;
    @FXML private TableColumn<ServicioAdicional, String> colDescripcion;
    @FXML private TableColumn<ServicioAdicional, String> colPrecio;
    @FXML private TableColumn<ServicioAdicional, String> colDisponible;

    private final ObservableList<ServicioAdicional> datos = FXCollections.observableArrayList();
    private ServicioAdicionalService servicioAdicionalService;

    @Override
    public void setContext(AppContext context) {
        this.servicioAdicionalService = context.getServicioAdicionalService();
        configurarTabla();
        refrescarTabla();
    }

    private void configurarTabla() {
        colCodigo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCodigo()));
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colDescripcion.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDescripcion()));
        colPrecio.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getPrecio())));
        colDisponible.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().isDisponibilidad() ? "Si" : "No"));
        tablaServicios.setItems(datos);
    }

    private void refrescarTabla() {
        datos.setAll(servicioAdicionalService.listarTodos());
    }

    @FXML
    private void onRegistrar() {
        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            servicioAdicionalService.registrar(
                    txtCodigo.getText().trim(),
                    txtNombre.getText().trim(),
                    txtDescripcion.getText().trim(),
                    precio,
                    chkDisponible.isSelected()
            );
            limpiarFormulario();
            refrescarTabla();
        } catch (NumberFormatException e) {
            mostrarError("El precio debe ser un valor numerico.");
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    private void onLimpiar() {
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtCodigo.clear();
        txtNombre.clear();
        txtDescripcion.clear();
        txtPrecio.clear();
        chkDisponible.setSelected(true);
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR, mensaje);
        alert.setHeaderText("No fue posible registrar el servicio");
        alert.showAndWait();
    }
}

