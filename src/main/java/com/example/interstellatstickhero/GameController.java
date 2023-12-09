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
    private boolean canrotate=false;
    private Stick redStick;

    private int clickcount;

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
        redStick= new Stick(stick,this);
       resetAssets();

    }

    public void handleMouseClicked(MouseEvent event) {
        if(canrotate){
            Rotate rotate = new Rotate();
            rotate.setPivotY(jaadu.getJadu().getY() + jaadu.getJadu().getFitHeight()); // Set pivot point to the bottom of the stick
            jaadu.getJadu().getTransforms().add(rotate);
            rotate.setAngle(180);

    }
    }


    public void handleMousePressed(MouseEvent event) {
        canrotate=false;
        stickbuff = stick;
        isIncreasing = true;

        // Start a thread to increase the stick length continuously
        Thread increaseStickThread = new Thread(() -> {
            while (isIncreasing && clickcount==0) {
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
        if(clickcount==0)
            dropStickAndAddNew();
        clickcount=+5;

        // Drop the stick and add a new stick

    }

    private void increaseStickLength() {


        // Implement the logic to increase the stick length
        double newY = redStick.getStickRectangle().getY() - 5; // Adjust the value to control the speed and direction
        redStick.getStickRectangle().setY(newY);
        redStick.getStickRectangle().setHeight(stick.getHeight() + 5);
    }

    private void dropStickAndAddNew() {
        // Set up a Rotate transform for the falling stick
        Rotate rotate = new Rotate();
        rotate.setPivotY(redStick.getStickRectangle().getY() + redStick.getStickRectangle().getHeight()); // Set pivot point to the bottom of the stick
        redStick.getStickRectangle().getTransforms().add(rotate);
        rotate.setAngle(0); // Reset the angle to 0 before starting the animation

        // Create a rotation animation for the falling stick using Timeline
        Timeline timeline = new Timeline();

        // KeyFrames for the rotation animation
        KeyFrame rotateKeyFrame = new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0));
        KeyFrame rotateEndKeyFrame = new KeyFrame(Duration.seconds(1), new KeyValue(rotate.angleProperty(), 90));

        // Add the keyframes to the timeline
        timeline.getKeyFrames().addAll(rotateKeyFrame, rotateEndKeyFrame);

        // Set up an event handler to trigger when animations are finished
        // timeline.setOnFinished(event -> resetStick());

        // Play the timeline
        timeline.play();
        movehero();

    }

    private void addNewStick() {
        // Create a new stick with initial properties
        Rectangle newStick = new Rectangle(redStick.getStickRectangle().getWidth(), redStick.getStickRectangle().getHeight());
        newStick.setY(redStick.getStickRectangle().getY());

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
        double distanceToWalk = p2.getPillarRectangle().getLayoutX()+ p2.getPillarRectangle().getWidth()-jaadu.getJadu().getFitWidth();
        System.out.println("shoul dmmove to"+distanceToWalk);
        if(!isStickAlignedWithNextPillar()){
            System.out.println("shoul dmmove to"+distanceToWalk);
            System.out.println("stick "+redStick.getStickRectangle().getHeight());
            distanceToWalk=jaadu.getJadu().getLayoutX()+redStick.getStickRectangle().getHeight()+jaadu.getJadu().getFitWidth();
            System.out.println("moving to"+distanceToWalk);
        }
        jaadu.moveHorizontally(distanceToWalk, 1);


    }

    public void checkStickAlignment() {
        if(isStickAlignedWithNextPillar()) {

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
        double diffInPillars= p2.getPillarRectangle().getLayoutX()-p1.getPillarRectangle().getWidth();

        if(redStick.getStickRectangle().getHeight()>diffInPillars && redStick.getStickRectangle().getHeight()<diffInPillars+ p2.getPillarRectangle().getWidth()){
            System.out.println("OK");
            return true;
        }
        else {
            System.out.println("NO");
            return false;
        }

    }

    private void moveHeroTo2ndPillar() {
//       TranslateTransition transition= new TranslateTransition(Duration.seconds(2),jaadu.getJadu());
////       transition.setByX(p1.getPillarRectangle().getLayoutX()+p1.getPillarRectangle().getWidth()-jaadu.getJadu().getLayoutX()-jaadu.getJadu().getFitWidth());
//        transition.setToX(0);
//    transition.play();
        ImageView hero = jaadu.getJadu();

        // Calculate the new layoutX position
        double newLayoutX =(double) p2.getPillarRectangle().getWidth()-jaadu.getJadu().getFitWidth();
//        double newLayoutX=;

        // Create a Timeline for the animation
        Timeline timeline = new Timeline();

        // Define the KeyValue for the animation (animate layoutX to newLayoutX)
        KeyValue keyValue = new KeyValue(hero.layoutXProperty(), newLayoutX);

        // Define the KeyFrame using the KeyValue, setting the duration of the animation
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), keyValue);

        // Add the KeyFrame to the Timeline
        timeline.getKeyFrames().add(keyFrame);
//        timeline.setOnFinished(event -> );
//                // Play the animation
        timeline.play();

    }
    private  void resetAssets(){
        jaadu.getJadu().setX(p1.getPillarRectangle().getLayoutX()+p1.getPillarRectangle().getWidth()-jaadu.getJadu().getLayoutX()-jaadu.getJadu().getFitWidth());
        redStick.getStickRectangle().setLayoutX(p1.getPillarRectangle().getWidth()-redStick.getStickRectangle().getWidth());
    }
    public void removeOldPillar() {

        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), p1.getPillarRectangle());
        transition.setByX(-500);
        transition.play();
        transition.setOnFinished(event ->gamePane.getChildren().remove(p1.getPillarRectangle())
        );

    }




    private void move2ndPillar() {
        double distanceToMove = -p2.getPillarRectangle().getLayoutX();
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), p2.getPillarRectangle());
        TranslateTransition stickTransition = new TranslateTransition(Duration.seconds(2),redStick.getStickRectangle());
        stickTransition.setByX(distanceToMove);
        transition.setByX(distanceToMove);
        stickTransition.play();
        transition.play();
        transition.setOnFinished(event -> {
            createNewPillar();
            canrotate=false;
        });
    }

    public void resetStick() {

        redStick.getStickRectangle().setHeight(0);
        Rotate rotate = new Rotate();
        rotate.setPivotY(redStick.getStickRectangle().getY() + redStick.getStickRectangle().getHeight()); // Set pivot point to the bottom of the stick
        redStick.getStickRectangle().getTransforms().add(rotate);
        rotate.setAngle(-90);
        clickcount = 0;
    }
    public void resetStick2(){
//        redStick.getStickRectangle().setLayoutX(p1.getPillarRectangle().getWidth()-redStick.getStickRectangle().getWidth());
//        redStick.getStickRectangle().setX(jaadu.getJadu().getLayoutX()+2*jaadu.getJadu().getFitWidth());
        redStick.getStickRectangle().setX(0);


    }



    private void createNewPillar() {
        // Create and return a new pillar
//        System.out.println("p1"+ p1);
//        System.out.println("p2 "+(double)p2.getPillarRectangle().getWidth());
        p1 =p2; // Shift the current pillar to be the old pillar

        int dist = random.nextInt(300-170 + 1) + 170 ;// Calculate the distance for the new pillar
        p2 = new Pillar(dist, 700); // Adjust duration as needed
        gamePane.getChildren().add(p2.getPillarRectangle());


//        System.out.println("addded nwe pillar!!!!!");
//        System.out.println("p1"+ p1);
//        System.out.println("'p2"+p2);
        PauseTransition pause = new PauseTransition(Duration.millis(100));
        pause.setOnFinished(event -> resetStick2()); // Call resetStick2 after the delay
        pause.play(); // Start the pause
//

    }





}




