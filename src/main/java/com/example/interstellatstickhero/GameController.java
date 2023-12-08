package com.example.interstellatstickhero;

import javafx.animation.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.ImageView;

import java.io.IOException;
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
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchtoHomeScreen(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("homeScreen.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchtoPauseScreen(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("pauseScreen.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private static final Random random = new Random();

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
        jaadu= new StickHero(Hero,this);
       resetAssets();
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
        double newY = stick.getY() - 5; // Adjust the value to control the speed and direction
        stick.setY(newY);
        stick.setHeight(stick.getHeight() + 5);
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




    private Pillar p1;
    private Pillar p2;
    public void pillarStart(){
        p1= addNewPillar(0,100);
        p2= addNewPillar(250,200);


    }
    private Pillar addNewPillar(int dist, int ms) {
        // Example pillar creation, adjust as necessary

        Pillar newPillar = new Pillar(dist, ms); // Example parameters
        gamePane.getChildren().add(newPillar.getPillarRectangle());
        return newPillar;
    }

    private void movehero(){
        double distanceToWalk = stick.getHeight();
        jaadu.moveHorizontally(distanceToWalk, 1);


    }

    public void checkStickAlignment() {
        if (isStickAlignedWithNextPillar()) {

            removeOldPillar();
            move2ndPillar();
            moveHeroTo2ndPillar();
        } else {

            removeOldPillar();
         move2ndPillar();
            moveHeroTo2ndPillar();
            // Handle the case where the stick does not reach the next pillar
            // For example, make the hero fall, end the game, etc.
        }
    }

    private boolean isStickAlignedWithNextPillar() {
        double diffInPillars= p2.getPillarRectangle().getLayoutX()-p2.getPillarRectangle().getWidth();

        if(stick.getHeight()>diffInPillars && stick.getHeight()<diffInPillars+ p2.getPillarRectangle().getWidth()){
            System.out.println("OK");
            return true;
        }
        else {
            System.out.println("NO");
            return false;
        }

    }

    private void moveHeroTo2ndPillar() {
       TranslateTransition transition= new TranslateTransition(Duration.seconds(2),jaadu.getJadu());
//       transition.setByX(p1.getPillarRectangle().getLayoutX()+p1.getPillarRectangle().getWidth()-jaadu.getJadu().getLayoutX()-jaadu.getJadu().getFitWidth());
        transition.setToX(0);
    transition.play();

    }
    private  void resetAssets(){
        jaadu.getJadu().setX(p1.getPillarRectangle().getLayoutX()+p1.getPillarRectangle().getWidth()-jaadu.getJadu().getLayoutX()-jaadu.getJadu().getFitWidth());
        stick.setLayoutX(p1.getPillarRectangle().getWidth()-stick.getWidth());
    }
    public void removeOldPillar() {

        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), p1.getPillarRectangle());
        transition.setByX(-500);
        transition.play();

    }


    private void move2ndPillar() {

        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), p2.getPillarRectangle());
        transition.setByX(-p2.getPillarRectangle().getLayoutX()); // Set the distance to move
//        transition.setByX(0);
        transition.play();
        transition.setOnFinished(event -> createNewPillar());


    }

    private void createNewPillar() {
        // Create and return a new pillar
        p1 = p2; // Shift the current pillar to be the old pillar

        int dist = random.nextInt(300-170 + 1) + 170 ;// Calculate the distance for the new pillar
        Pillar newPillar = new Pillar(dist, 700); // Adjust duration as needed
        gamePane.getChildren().add(newPillar.getPillarRectangle());
        p2= newPillar;
        System.out.println("addded nwe pillar!!!!!");

    }





}




