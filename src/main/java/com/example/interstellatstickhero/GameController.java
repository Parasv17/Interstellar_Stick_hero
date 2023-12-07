package com.example.interstellatstickhero;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class GameController {


//    private final StickHero stickHero;
    @FXML
    private Rectangle stick;
    private boolean isIncreasing;

//    public GameController(StickHero stickHero) {
//        this.stickHero = stickHero;
//    }

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
        stick.setHeight(stick.getHeight()+2);

    }
}
