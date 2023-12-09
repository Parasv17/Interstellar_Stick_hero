package com.example.interstellatstickhero;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.image.ImageView;

import java.io.IOException;


public class PauseGamePopup {

    private Pane gamePane;
    private GameController controller;
    private Rectangle background;
    private Label pauseLabel, scoreLabel, scoreValue, powerUpCount;
    private ImageView powerUpIcon, resumeButton, exitButton;

    public PauseGamePopup(Pane gamePane, GameController cont) {
        this.gamePane = gamePane;
        this.controller= cont;
        createPopup();
    }


        private void createPopup() {
            // Background rectangle
            background = new Rectangle(115, 43, 547, 335);
            background.setFill(Color.web("#0003058c"));
            background.setArcHeight(5.0);
            background.setArcWidth(5.0);

            // Pause label
           pauseLabel = new Label("GAME PAUSED");
            pauseLabel.setLayoutX(197);
            pauseLabel.setLayoutY(69);
            pauseLabel.setFont(new Font("ROGFonts-Regular", 42));
            pauseLabel.setTextFill(Color.WHITE);

            // Score label
             scoreLabel = new Label("SCORE");
            scoreLabel.setLayoutX(240);
            scoreLabel.setLayoutY(131);
            scoreLabel.setFont(new Font("ROGFonts-Regular", 34));
            scoreLabel.setTextFill(Color.web("#ffd01c"));

            // Score value
             scoreValue = new Label("12"); // Replace with actual score
            scoreValue.setLayoutX(452);
            scoreValue.setLayoutY(128);
            scoreValue.setFont(new Font("Rockwell Extra Bold", 42));
            scoreValue.setTextFill(Color.web("#ffd01c"));

//             Power-up icon
             powerUpIcon = new ImageView(new Image(Main.class.getResourceAsStream("6640_prev_ui.png")));
            powerUpIcon.setFitHeight(48);
            powerUpIcon.setFitWidth(55);
            powerUpIcon.setLayoutX(325);
            powerUpIcon.setLayoutY(178);

            // Power-up count
             powerUpCount = new Label("4"); // Replace with actual count
            powerUpCount.setLayoutX(465);
            powerUpCount.setLayoutY(178);
            powerUpCount.setFont(new Font("Rockwell Extra Bold", 44));
            powerUpCount.setTextFill(Color.WHITE);

            // Resume game button
             resumeButton = new ImageView(new Image(Main.class.getResourceAsStream("buttons-150552_640 2.png")));
            resumeButton.setFitHeight(98);
            resumeButton.setFitWidth(101);
            resumeButton.setLayoutX(214);
            resumeButton.setLayoutY(254);
            resumeButton.setOnMouseClicked(event -> resumeGame());

            // Exit game button
            exitButton = new ImageView(new Image(Main.class.getResourceAsStream("buttons-150552_640 3.png")));
            exitButton.setFitHeight(98);
            exitButton.setFitWidth(101);
            exitButton.setLayoutX(481);
            exitButton.setLayoutY(254);
            exitButton.setOnMouseClicked(event -> {
                try {
                    controller.switchtoHomeScreen();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            // Add all elements to gamePane
            gamePane.getChildren().addAll(background, pauseLabel, scoreLabel, scoreValue, powerUpIcon, powerUpCount, resumeButton, exitButton);
        }

        private void resumeGame() {
            // Logic to resume the game
            gamePane.getChildren().removeAll(background, pauseLabel, scoreLabel, scoreValue, powerUpIcon, powerUpCount, resumeButton, exitButton);

            controller.resumeGame();

           // Remove pause screen elements
        }

        private void exitGame() {


        }


}
