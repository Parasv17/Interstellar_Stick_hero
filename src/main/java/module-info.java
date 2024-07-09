module com.example.interstellatstickhero {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires org.junit.jupiter.api;
    requires org.testng;


    opens com.example.interstellatstickhero to javafx.fxml;
    exports com.example.interstellatstickhero;
}