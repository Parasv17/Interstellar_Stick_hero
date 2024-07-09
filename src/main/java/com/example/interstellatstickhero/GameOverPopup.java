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

    private int score;
    private  int cherry;
    private Pane gamePane;
    private GameController controller;
    private Rectangle background;
    private Label gameOverLabel, scoreLabel, scoreValue, powerUpCount;
    private ImageView powerUpIcon, restartButton, exitButton;

    public GameOverPopup(Pane gamePane, GameController cont, int score,int cherry) {
        this.gamePane = gamePane;
        this.controller = cont;
        this.cherry= cherry;
        this.score= score;
        createPopup();
    }

    private void createPopup() {
        background = new Rectangle(115, 43, 547, 335);
        background.setFill(Color.web("#0003058c"));
        background.setArcHeight(5.0);
        background.setArcWidth(5.0);
        gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setLayoutX(197);
        gameOverLabel.setLayoutY(69);
        gameOverLabel.setFont(new Font("ROGFonts-Regular", 42));
        gameOverLabel.setTextFill(Color.web("#ff0000"));
        scoreLabel = new Label("SCORE");
        scoreLabel.setLayoutX(240);
        scoreLabel.setLayoutY(131);
        scoreLabel.setFont(new Font("ROGFonts-Regular", 34));
        scoreLabel.setTextFill(Color.web("#ffd01c"));
        scoreValue = new Label(String.valueOf(score)); // Replace with actual score
        scoreValue.setLayoutX(452);
        scoreValue.setLayoutY(128);
        scoreValue.setFont(new Font("Rockwell Extra Bold", 42));
        scoreValue.setTextFill(Color.web("#ffd01c"));
        powerUpIcon = new ImageView(new Image(Main.class.getResourceAsStream("cherry.png")));
        powerUpIcon.setFitHeight(48);
        powerUpIcon.setFitWidth(55);
        powerUpIcon.setLayoutX(325);
        powerUpIcon.setLayoutY(178);
        powerUpCount = new Label(String.valueOf(cherry)); // Replace with actual count
        powerUpCount.setLayoutX(452);
        powerUpCount.setLayoutY(178);
        powerUpCount.setFont(new Font("Rockwell Extra Bold", 44));
        powerUpCount.setTextFill(Color.WHITE);
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
        exitButton = new ImageView(new Image(Main.class.getResourceAsStream("buttons-150552_640_3.png")));
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
        gamePane.getChildren().addAll(background, gameOverLabel, scoreLabel, scoreValue, powerUpIcon, powerUpCount, restartButton, exitButton);
    }

    private void restartGame() {
    }

}
