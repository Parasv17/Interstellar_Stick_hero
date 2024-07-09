package com.example.interstellatstickhero;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class homeController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private  Label HSC;
    public void initialize() throws IOException {

        loadHScore();
        HSC.setText(String.valueOf(GameController.hScore));
    }



    public void switchtoMainScreen(MouseEvent event) throws IOException {
        GameController.shouldLoad=false;
        root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchtoMainWithLoadScreen(MouseEvent event) throws IOException {
        GameController.shouldLoad=true;
        root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void loadHScore() throws IOException {
        try (FileReader fileReader = new FileReader("hscore.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String highScoreStr = bufferedReader.readLine();
            if (highScoreStr != null && !highScoreStr.isEmpty()) {
                GameController.hScore = Integer.parseInt(highScoreStr);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing high score: " + e.getMessage());

            GameController.hScore = 0;
        }
    }

    public static void main(String[] args) {


    }
}

