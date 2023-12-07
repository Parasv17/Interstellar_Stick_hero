package com.example.interstellatstickhero;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private GameController gameController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        StickHero stickHero = new StickHero();
        gameController = new GameController(stickHero);

        // Set the controller for the FXML file
        fxmlLoader.setController(new MyController());

        stage.setTitle("Interstellar Stick Hero");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public class MyController {


        // Set up mouse event handlers inside the controller class
        @FXML
        public void handleMouseClicked(MouseEvent event) {
            gameController.handleMouseClicked(event);
        }

        @FXML
        public void handleMousePressed(MouseEvent event) {
            gameController.handleMousePressed(event);
        }

        @FXML
        public void handleMouseReleased(MouseEvent event) {
            gameController.handleMouseReleased(event);
        }
    }
}
