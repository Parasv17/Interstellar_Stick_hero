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
    private int score;
    private  int cherry;
    private Pane gamePane;
    private GameController controller;
    private Rectangle background;
    private Label pauseLabel, scoreLabel, scoreValue, powerUpCount;
    private ImageView powerUpIcon, resumeButton, exitButton;

    public PauseGamePopup(Pane gamePane, GameController cont,int score, int cherry) {
        this.gamePane = gamePane;
        this.controller= cont;
        this.score= score;
        this.cherry= cherry;
        createPopup();
    }


        private void createPopup() {
            background = new Rectangle(115, 43, 547, 335);
            background.setFill(Color.web("#0003058c"));
            background.setArcHeight(5.0);
            background.setArcWidth(5.0);
           pauseLabel = new Label("GAME PAUSED");
            pauseLabel.setLayoutX(197);
            pauseLabel.setLayoutY(69);
            pauseLabel.setFont(new Font("ROGFonts-Regular", 42));
            pauseLabel.setTextFill(Color.WHITE);
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
             powerUpCount = new Label(String.valueOf(cherry));
            powerUpCount.setLayoutX(452);
            powerUpCount.setLayoutY(178);
            powerUpCount.setFont(new Font("Rockwell Extra Bold", 44));
            powerUpCount.setTextFill(Color.WHITE);
             resumeButton = new ImageView(new Image(Main.class.getResourceAsStream("buttons-150552_640_2.png")));
            resumeButton.setFitHeight(98);
            resumeButton.setFitWidth(101);
            resumeButton.setLayoutX(214);
            resumeButton.setLayoutY(254);
            resumeButton.setOnMouseClicked(event -> resumeGame());
            exitButton = new ImageView(new Image(Main.class.getResourceAsStream("buttons-150552_640_3.png")));
            exitButton.setFitHeight(98);
            exitButton.setFitWidth(101);
            exitButton.setLayoutX(481);
            exitButton.setLayoutY(254);
            exitButton.setOnMouseClicked(event -> {
                try {
                    controller.saveGame();
                    controller.switchtoHomeScreen();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            gamePane.getChildren().addAll(background, pauseLabel, scoreLabel, scoreValue, powerUpIcon, powerUpCount, resumeButton, exitButton);
        }
        private void resumeGame() {
            gamePane.getChildren().removeAll(background, pauseLabel, scoreLabel, scoreValue, powerUpIcon, powerUpCount, resumeButton, exitButton);
            controller.resumeGame();
        }

        private void exitGame() {
        }
}
