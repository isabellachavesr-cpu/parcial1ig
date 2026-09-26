module com.example.parcial1ig {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.parcial1ig to javafx.fxml;
    opens controller to javafx.fxml;

    exports com.example.parcial1ig;
}