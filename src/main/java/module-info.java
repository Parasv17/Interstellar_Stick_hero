module com.example.interstellatstickhero {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.interstellatstickhero to javafx.fxml;
    exports com.example.interstellatstickhero;
}