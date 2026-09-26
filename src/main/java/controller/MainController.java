package controller;

import com.example.parcial1ig.AppContext;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;


public class MainController {

    @FXML private TabPane tabPane;
    @FXML private Label lblAcademia;

    @FXML private EstudiantesController estudiantesController;
    @FXML private ProfesoresController profesoresController;
    @FXML private CursosController cursosController;
    @FXML private ServiciosController serviciosController;
    @FXML private MatriculasController matriculasController;
    @FXML private ConsultasController consultasController;

    private AppContext context;

    public void setContext(AppContext context) {
        this.context = context;
        lblAcademia.setText(context.getAcademia().getNombreComercial()
                + "  |  NIT " + context.getAcademia().getNit()
                + "  |  " + context.getAcademia().getTelefono());

        estudiantesController.setContext(context);
        profesoresController.setContext(context);
        cursosController.setContext(context);
        serviciosController.setContext(context);
        matriculasController.setContext(context);
        consultasController.setContext(context);

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, anterior, nueva) -> {
            refrescarSiCorresponde(nueva);
        });
    }

    private void refrescarSiCorresponde(Tab tab) {
        if (tab == null) {
            return;
        }
        String id = tab.getId();
        if (id == null) {
            return;
        }
        switch (id) {
            case "tabMatriculas" -> matriculasController.actualizar();
            case "tabConsultas" -> consultasController.actualizar();
            default -> { /* las demas pestanas no requieren refresco externo */ }
        }
    }
}
