module com.example.interstellatstickhero {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.interstellatstickhero to javafx.fxml;
    exports com.example.interstellatstickhero;
    exports com.example.interstellatstickhero.controller;
    opens com.example.interstellatstickhero.controller to javafx.fxml;
}