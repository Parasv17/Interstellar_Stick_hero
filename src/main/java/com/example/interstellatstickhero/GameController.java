package com.example.interstellatstickhero;

import javafx.animation.*;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//public class GameController {
//
////
//    @FXML
//    private Rectangle stick;
//
//    private List<Rectangle> sticks;
//    private boolean isIncreasing;
//
//    public GameController() {
//        sticks = new ArrayList<>();
//    }
//    static final Random random = new Random();
//
//
//
//
//    public void handleMouseClicked(MouseEvent event) {
//        // Add your logic if needed
//    }
//
//    public void handleMousePressed(MouseEvent event) {
//        isIncreasing = true;
//
//        // Start a thread to increase the stick length continuously
//        Thread increaseStickThread = new Thread(() -> {
//            while (isIncreasing) {
//                increaseStickLength();
//                try {
//                    Thread.sleep(50); // Adjust the delay based on your needs
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        increaseStickThread.start();
//    }
//
//    public void handleMouseReleased(MouseEvent event) {
//        isIncreasing = false;
//
//        // Drop the stick and add a new stick
//        dropStickAndAddNew();
//    }
//
//    private void increaseStickLength() {
//        // Implement the logic to increase the stick length
//        double newY = stick.getY() - 2; // Adjust the value to control the speed and direction
//        stick.setY(newY);
//        stick.setHeight(stick.getHeight() + 2);
//    }
//
//    private void dropStickAndAddNew() {
//        // Create a rotation animation for the falling stick
//        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), stick);
//        rotateTransition.setByAngle(90); // Rotate by 90 degrees
//        rotateTransition.setInterpolator(Interpolator.EASE_BOTH);
//
//        // Create a translation animation for the falling stick
//        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), stick);
//        translateTransition.setToY(98);
//        translateTransition.setToX(80);// Move to a certain height (adjust as needed)
//        translateTransition.setInterpolator(Interpolator.EASE_BOTH);
//
//        // Set up an event handler to trigger when animations are finished
//
//        // Play both animations
//        rotateTransition.play();
//        translateTransition.play();
//        movehero();
//
//    }
//
//    private void addNewStick() {
//        // Create a new stick with initial properties
//        Rectangle newStick = new Rectangle(stick.getWidth(), stick.getHeight());
//        newStick.setY(stick.getY());
//
//        // Add the new stick to the scene (you might need to access the parent Pane)
//        // For example: ((Pane) stick.getParent()).getChildren().add(newStick);
//    }
//      public void pillarStart(){
//        int dist=0;
//        addNewPillar(dist,1000);
//        addNewPillar(dist+270,2500);
//    }
//
//    private void addNewPillar(int dist, int ms) {
//        // Example pillar creation, adjust as necessary
//
//        Pillar newPillar = new Pillar(dist, ms); // Example parameters
//        gamePane.getChildren().add(newPillar.getPillarRectangle());
//    }
//
//
//
//
//
//
//
//
//
//
//}
public class GameController {
    @FXML
    private Pane gamePane;
//

    private Rectangle stickbuff;


    @FXML
    private Rectangle stick;

    private List<Rectangle> sticks;
    private boolean isIncreasing;

    public GameController() {
        sticks = new ArrayList<>();
    }
    @FXML
    private ImageView Hero;
    private StickHero jaadu;
    public void initialize() {
        // Initialization logic here
        pillarStart();
        jaadu= new StickHero(Hero);

//
    }

    public void handleMouseClicked(MouseEvent event) {
        // Add your logic if needed
    }


    public void handleMousePressed(MouseEvent event) {
        stickbuff = stick;
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
        // Set up a Rotate transform for the falling stick
        Rotate rotate = new Rotate();
        rotate.setPivotY(stick.getY() + stick.getHeight()); // Set pivot point to the bottom of the stick
        stick.getTransforms().add(rotate);
        rotate.setAngle(0); // Reset the angle to 0 before starting the animation

        // Create a rotation animation for the falling stick using Timeline
        Timeline timeline = new Timeline();

        // KeyFrames for the rotation animation
        KeyFrame rotateKeyFrame = new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0));
        KeyFrame rotateEndKeyFrame = new KeyFrame(Duration.seconds(1), new KeyValue(rotate.angleProperty(), 90));

        // Add the keyframes to the timeline
        timeline.getKeyFrames().addAll(rotateKeyFrame, rotateEndKeyFrame);

        // Set up an event handler to trigger when animations are finished
        timeline.setOnFinished(event -> addNewStick());

        // Play the timeline
        timeline.play();
        movehero();
    }

    private void addNewStick() {
        // Create a new stick with initial properties
        Rectangle newStick = new Rectangle(stick.getWidth(), stick.getHeight());
        newStick.setY(stick.getY());

        // Add the new stick to the scene (you might need to access the parent Pane)
        // For example: ((Pane) stick.getParent()).getChildren().add(newStick);
    }

          public void pillarStart(){
        int dist=0;
        addNewPillar(dist,1000);
        addNewPillar(dist+270,2500);
    }

    private void addNewPillar(int dist, int ms) {
        // Example pillar creation, adjust as necessary

        Pillar newPillar = new Pillar(dist, ms); // Example parameters
        gamePane.getChildren().add(newPillar.getPillarRectangle());
    }

    private void movehero(){
        jaadu.moveHorizontally((double) stick.getHeight(),2);
    }
}




