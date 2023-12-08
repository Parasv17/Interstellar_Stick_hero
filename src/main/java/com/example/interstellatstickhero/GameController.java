package com.example.interstellatstickhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

public class GameController {


    //    private final StickHero stickHero;
    private static final Random random = new Random();
    @FXML
    private Rectangle stick;
    private boolean isIncreasing;
    @FXML
    private Pane gamePane;
//    public GameController(StickHero stickHero) {
//        this.stickHero = stickHero;statr
//    }
public void initialize() {
    // Initialization logic here
    pillarStart();
}
    public void handleMouseClicked(MouseEvent event) {
//        stickHero.setFlipped(!stickHero.isFlipped());
    }

    public void handleMousePressed(MouseEvent event) {
//        if (stickHero.isMoving()) {
//            return;
//        } else {
        isIncreasing = true;

        // Start a thread to increase the stick length continuously
        Thread increaseStickThread = new Thread(() -> {
            while (isIncreasing) {
                increaseStickLength();
                try {
                    Thread.sleep(50); // Adjust the delay based on your needs
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        increaseStickThread.start();
    }
//    }

    public void handleMouseReleased(MouseEvent event) {
        isIncreasing = false;
    }

    private void increaseStickLength() {
        // Implement the logic to increase the stick length
//        stickHero.increaseStickLength(5); // You can adjust the amount to increase
        double newY = stick.getY() - 2; // Adjust the value to control the speed and direction
        stick.setY(newY);
        stick.setHeight(stick.getHeight() + 2);

    }
    public void pillarStart() {
        int dist=0;
        addNewPillar(dist,1000);
        addNewPillar(dist+270,2500);
    }

    private void addNewPillar(int dist, int ms) {
        // Example pillar creation, adjust as necessary

        Pillar newPillar = new Pillar(dist, ms); // Example parameters
        gamePane.getChildren().add(newPillar.getPillarRectangle());
    }
}