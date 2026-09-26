package com.example.parcial1ig;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        AppContext context = new AppContext();

        URL fxmlUrl = getClass().getResource("/com/example/parcial1ig/main.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        javafx.scene.layout.BorderPane root = loader.load();

        MainController controller = loader.getController();
        controller.setContext(context);

        Scene scene = new Scene(root, 1100, 700);
        URL cssUrl = getClass().getResource("/com/example/parcial1ig/css/estilos.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }

        stage.setTitle("Academia LenguajeCafetero - Gestion academica");
        stage.setScene(scene);
        stage.setMinWidth(950);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
