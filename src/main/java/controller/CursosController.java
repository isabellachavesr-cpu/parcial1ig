package controller;

import com.example.parcial1ig.AppContext;
import model.Curso;
import model.Idioma;
import model.NivelReferencia;
import model.TipoCurso;
import factory.DatosCurso;
import service.CursoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.util.stream.Collectors;

public class CursosController implements ContextAware {

    @FXML private ComboBox<TipoCurso> comboTipo;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<Idioma> comboIdioma;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtDuracion;
    @FXML private TextField txtValorMensual;

    @FXML private VBox panelPersonalizado;
    @FXML private TextField txtSesiones;
    @FXML private ComboBox<NivelReferencia> comboNivel;
    @FXML private TextArea txtObjetivos;

    @FXML private TableView<Curso> tablaCursos;
    @FXML private TableColumn<Curso, String> colTipo;
    @FXML private TableColumn<Curso, String> colCodigo;
    @FXML private TableColumn<Curso, String> colNombre;
    @FXML private TableColumn<Curso, String> colIdioma;
    @FXML private TableColumn<Curso, String> colDuracion;
    @FXML private TableColumn<Curso, String> colValorMensual;
    @FXML private TableColumn<Curso, String> colEstado;
    @FXML private TableColumn<Curso, String> colBeneficios;

    private final ObservableList<Curso> datos = FXCollections.observableArrayList();
    private CursoService cursoService;

    @Override
    public void setContext(AppContext context) {
        this.cursoService = context.getCursoService();
        comboTipo.setItems(FXCollections.observableArrayList(TipoCurso.values()));
        comboIdioma.setItems(FXCollections.observableArrayList(Idioma.values()));
        comboNivel.setItems(FXCollections.observableArrayList(NivelReferencia.values()));
        comboTipo.valueProperty().addListener((obs, anterior, nuevo) -> actualizarVisibilidadPersonalizado(nuevo));
        actualizarVisibilidadPersonalizado(null);
        configurarTabla();
        refrescarTabla();
    }

    private void actualizarVisibilidadPersonalizado(TipoCurso tipo) {
        boolean esPersonalizado = tipo == TipoCurso.PERSONALIZADO;
        panelPersonalizado.setVisible(esPersonalizado);
        panelPersonalizado.setManaged(esPersonalizado);
    }

    private void configurarTabla() {
        colTipo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTipo()));
        colCodigo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCodigo()));
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colIdioma.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getIdioma().toString()));
        colDuracion.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDuracionMeses() + " mes(es)"));
        colValorMensual.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getValorMensual())));
        colEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstado().toString()));
        colBeneficios.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getBeneficios().stream().map(Object::toString).collect(Collectors.joining(", "))));
        tablaCursos.setItems(datos);
    }

    private void refrescarTabla() {
        datos.setAll(cursoService.listarTodos());
    }

    @FXML
    private void onCrearCurso() {
        try {
            TipoCurso tipo = comboTipo.getValue();
            if (tipo == null) {
                mostrarError("Seleccione el tipo de curso.");
                return;
            }
            if (comboIdioma.getValue() == null) {
                mostrarError("Seleccione el idioma del curso.");
                return;
            }

            DatosCurso datos = new DatosCurso();
            datos.codigo = txtCodigo.getText().trim();
            datos.nombre = txtNombre.getText().trim();
            datos.idioma = comboIdioma.getValue();
            datos.descripcion = txtDescripcion.getText().trim();
            datos.duracionMeses = Integer.parseInt(txtDuracion.getText().trim());
            datos.valorMensual = Double.parseDouble(txtValorMensual.getText().trim());

            if (tipo == TipoCurso.PERSONALIZADO) {
                if (comboNivel.getValue() == null) {
                    mostrarError("Seleccione el nivel de referencia requerido.");
                    return;
                }
                datos.cantidadSesiones = Integer.parseInt(txtSesiones.getText().trim());
                datos.nivelReferencia = comboNivel.getValue();
                datos.objetivosEstudiante = txtObjetivos.getText().trim();
            }

            cursoService.crearCurso(tipo, datos);
            limpiarFormulario();
            refrescarTabla();
        } catch (NumberFormatException e) {
            mostrarError("Duracion, valor mensual y cantidad de sesiones deben ser numericos.");
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
        comboIdioma.getSelectionModel().clearSelection();
        txtDescripcion.clear();
        txtDuracion.clear();
        txtValorMensual.clear();
        txtSesiones.clear();
        comboNivel.getSelectionModel().clearSelection();
        txtObjetivos.clear();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR, mensaje);
        alert.setHeaderText("No fue posible crear el curso");
        alert.showAndWait();
    }
}
