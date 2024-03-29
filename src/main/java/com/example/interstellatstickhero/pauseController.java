package com.example.interstellatstickhero;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class pauseController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void switchtoMainScreen(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
    public void switchtoHomeScreen(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("homeScreen.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
