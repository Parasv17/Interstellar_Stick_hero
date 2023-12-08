package com.example.interstellatstickhero.controller;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    @FXML
    private Rectangle stick;

    private List<Rectangle> sticks;
    private boolean isIncreasing;

    public GameController() {
        sticks = new ArrayList<>();
    }

    public void handleMouseClicked(MouseEvent event) {
        // Add your logic if needed
    }

    public void handleMousePressed(MouseEvent event) {
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

    public void handleMouseReleased(MouseEvent event) {
        isIncreasing = false;

        // Drop the stick and add a new stick
        dropStickAndAddNew();
    }

    private void increaseStickLength() {
        // Implement the logic to increase the stick length
        double newY = stick.getY() - 2; // Adjust the value to control the speed and direction
        stick.setY(newY);
        stick.setHeight(stick.getHeight() + 2);
    }

    private void dropStickAndAddNew() {
        // Create a rotation animation for the falling stick
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), stick);
        rotateTransition.setByAngle(90); // Rotate by 90 degrees
        rotateTransition.setInterpolator(Interpolator.EASE_BOTH);

        // Create a translation animation for the falling stick
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), stick);
        translateTransition.setToY(98);
        translateTransition.setToX(80);// Move to a certain height (adjust as needed)
        translateTransition.setInterpolator(Interpolator.EASE_BOTH);

        // Set up an event handler to trigger when animations are finished
        translateTransition.setOnFinished(event -> {
            // After the falling animation, spawn a new stick
            //addNewStick();
            // Reset the original stick properties
//            stick.setY(300); // Adjust the initial Y position
//            stick.setHeight(10); // Adjust the initial height
//            stick.setRotate(0); // Reset rotation
        });

        // Play both animations
        rotateTransition.play();
        translateTransition.play();
    }

    private void addNewStick() {
        // Create a new stick with initial properties
        Rectangle newStick = new Rectangle(stick.getWidth(), stick.getHeight());
        newStick.setY(stick.getY());

        // Add the new stick to the scene (you might need to access the parent Pane)
        // For example: ((Pane) stick.getParent()).getChildren().add(newStick);
    }
}
