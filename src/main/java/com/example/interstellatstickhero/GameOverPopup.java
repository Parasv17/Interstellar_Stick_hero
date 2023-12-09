package com.example.interstellatstickhero;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class GameOverPopup {

    private Pane gamePane;
    private GameController controller;
    private Rectangle background;
    private Label gameOverLabel, scoreLabel, scoreValue, powerUpCount;
    private ImageView powerUpIcon, restartButton, exitButton;

    public GameOverPopup(Pane gamePane, GameController cont) {
        this.gamePane = gamePane;
        this.controller = cont;
        createPopup();
    }

    private void createPopup() {
        // Background rectangle
        background = new Rectangle(115, 43, 547, 335);
        background.setFill(Color.web("#0003058c"));
        background.setArcHeight(5.0);
        background.setArcWidth(5.0);

        // Game over label
        gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setLayoutX(197);
        gameOverLabel.setLayoutY(69);
        gameOverLabel.setFont(new Font("ROGFonts-Regular", 42));
        gameOverLabel.setTextFill(Color.WHITE);

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

        // Power-up icon
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

        // Restart game button
        restartButton = new ImageView(new Image(Main.class.getResourceAsStream("restart.png")));
        restartButton.setFitHeight(98);
        restartButton.setFitWidth(101);
        restartButton.setLayoutX(214);
        restartButton.setLayoutY(254);
        restartButton.setOnMouseClicked(event -> {
            try {
                controller.switchtoMainScreen();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

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
        gamePane.getChildren().addAll(background, gameOverLabel, scoreLabel, scoreValue, powerUpIcon, powerUpCount, restartButton, exitButton);
    }

    private void restartGame() {
        // Logic to restart the game
        // This might involve resetting game state, scores, etc., and then starting a new game
    }

}
