package com.example.interstellatstickhero;

import javafx.animation.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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

    private boolean isFlipped = false;



    public void switchtoPauseScreen(MouseEvent event) throws IOException {
        if(!isPaused){pauseGame();}
        else {
            resumeGame();

        }
//        root = FXMLLoader.load(getClass().getResource("pauseScreen.fxml"));
//        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
//        scene= new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }
    private ArrayList<Timeline> allAnims= new ArrayList<Timeline>();
    private ArrayList<TranslateTransition> alltrans= new ArrayList<TranslateTransition>();


    private static final Random random = new Random();
    private  boolean isPaused=false;

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
    private int score=0;

    public GameController() {
        sticks = new ArrayList<>();
    }
    @FXML
    private ImageView Hero;
    private StickHero jaadu;
    public void initialize() {
        // Initialization logic here
        pillarStart();
        jaadu = new StickHero(Hero, this);
        redStick = new Stick(stick, this);


        // The call to setupKeyPressHandler is delayed until the scene is fully displayed
        gamePane.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setupKeyPressHandler(newValue);
            }
        });
        resetAssets();
    }


    private void setupKeyPressHandler(Scene scene) {
        // Set the key pressed event handler on the Scene
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                System.out.println("Space key pressed");
                flipPlayer();
            }
        });
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
                    Thread.sleep(40); // Adjust the delay based on your needs
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
        //timeline.setOnFinished(event ->);

        // Play the timeline
        allAnims.add(timeline);
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

    public ArrayList<Timeline> getAllAnims() {
        return allAnims;
    }

    public ArrayList<TranslateTransition> getAlltrans() {
        return alltrans;
    }

    public void setAlltrans(TranslateTransition tl) {
        this.alltrans.add(tl);
    }

    public void setAllAnims(Timeline tl) {
        this.allAnims.add(tl);
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
            travelStickLengthAndFall();
            // Handle the case where the stick does not reach the next pillar
            // For example, make the hero fall, end the game, etc.
        }
    }

    private void travelStickLengthAndFall() {
        double stickLength = redStick.getStickRectangle().getHeight();
        double travelDistance = 0;

        // Travel horizontally
        TranslateTransition travelTransition = new TranslateTransition(Duration.seconds(1), jaadu.getJadu());
        travelTransition.setToX(travelDistance);
        alltrans.add(travelTransition);
        travelTransition.play();

        // Falling animation after the horizontal movement
        travelTransition.setOnFinished(event -> fallDown());
    }

    private void fallDown() {
        // Falling animation
        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(1), jaadu.getJadu());
        fallTransition.setToY(gamePane.getHeight()); // Fall to the bottom of the gamePane
        alltrans.add(fallTransition);
        fallTransition.play();
    }


    private boolean isStickAlignedWithNextPillar() {
        // Calculate the right edge of the first pillar
        double p1RightEdge = p1.getPillarRectangle().getLayoutX() + p1.getPillarRectangle().getWidth();

        // Calculate the left and right edges of the second pillar
        double p2LeftEdge = p2.getPillarRectangle().getLayoutX();
        double p2RightEdge = p2LeftEdge + p2.getPillarRectangle().getWidth();

        // The stick's length when laid down
        double stickLength = redStick.getStickRectangle().getHeight(); // Assuming the height is the length when laid down

        // Check if the player is flipped and touching either pillar
        if (isFlipped && (isPlayerTouchingPillar(p1) || isPlayerTouchingPillar(p2))) {
            System.out.println("Player is flipped and touching a pillar.");
            return false;
        }

        // Check if the stick, when laid down, spans from the right edge of p1 to anywhere within the width of p2
        if (p1RightEdge + stickLength >= p2LeftEdge && p1RightEdge + stickLength <= p2RightEdge) {
            System.out.println("Stick is aligned with the next pillar.");
            score++;
            return true;
        } else {
            System.out.println("Stick is not aligned with the next pillar.");
            return false;
        }
    }

    private boolean isPlayerTouchingPillar(Pillar pillar) {
        // Calculate the player's edges
        double playerLeftEdge = jaadu.getJadu().getLayoutX();
        double playerRightEdge = playerLeftEdge + jaadu.getJadu().getFitWidth();

        // Calculate the left and right edges of the pillar
        double pillarLeftEdge = pillar.getPillarRectangle().getLayoutX();
        double pillarRightEdge = pillarLeftEdge + pillar.getPillarRectangle().getWidth();

        // Check if the player overlaps with the pillar
        return (playerRightEdge >= pillarLeftEdge && playerLeftEdge <= pillarRightEdge);
    }



    private void moveHeroTo2ndPillar() {
//       TranslateTransition transition= new TranslateTransition(Duration.seconds(2),jaadu.getJadu());
////       transition.setByX(p1.getPillarRectangle().getLayoutX()+p1.getPillarRectangle().getWidth()-jaadu.getJadu().getLayoutX()-jaadu.getJadu().getFitWidth());
//        transition.setToX(0);
//    transition.play();
        ImageView hero = jaadu.getJadu();

        redStick.getStickRectangle().setLayoutX(0.0);
        redStick.getStickRectangle().setLayoutX((double) (p1.getPillarRectangle().getWidth()-redStick.getStickRectangle().getWidth()));

        // Calculate the new layoutX position
        jaadu.getJadu().setLayoutX(0.0);
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
        allAnims.add(timeline);

        timeline.play();

    }
    private  void resetAssets(){
        jaadu.getJadu().setX(p1.getPillarRectangle().getLayoutX()+p1.getPillarRectangle().getWidth()-jaadu.getJadu().getLayoutX()-jaadu.getJadu().getFitWidth());
        redStick.getStickRectangle().setLayoutX(p1.getPillarRectangle().getWidth()-redStick.getStickRectangle().getWidth());
    }
    public void removeOldPillar() {

        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), p1.getPillarRectangle());
        transition.setByX(-500);
        alltrans.add(transition);
        transition.play();
        transition.setOnFinished(event ->gamePane.getChildren().remove(p1.getPillarRectangle())
        );

    }




    private void move2ndPillar() {
        double distanceToMove = -p2.getPillarRectangle().getLayoutX();
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), p2.getPillarRectangle());
//        TranslateTransition stickTransition = new TranslateTransition(Duration.seconds(2),redStick.getStickRectangle());
//        stickTransition.setByX(distanceToMove);
        transition.setByX(distanceToMove);
//        stickTransition.play();
        alltrans.add(transition);
        transition.play();
        transition.setOnFinished(event -> {
            createNewPillar();
            canrotate=false;
        });
    }
//    public void resetStick() {
//        // Create a RotateTransition for the stick's rotation
//        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), redStick.getStickRectangle());
//        rotateTransition.setByAngle(-90); // Rotate by -90 degrees
//        rotateTransition.setOnFinished(event -> decreaseStickHeight()); // After rotation, decrease height
//
//        // Start the rotation animation
//        rotateTransition.play();
//
//        // Reset click count
//        clickcount = 0;
//    }



    public void resetStick() {


        redStick.getStickRectangle().getTransforms().clear();

        // Reset the stick's height
//        redStick.getStickRectangle().setHeight(redStick.getStickRectangle().getHeight());

        // Set the stick's Y position to keep its base at the same place
//        redStick.getStickRectangle().setLayoutY(269.00); // Adjust this value based on your game's layout
        clickcount=0;

    }
//public void resetStick() {
//    // Setting up the rotation animation
//    Rotate rotate = new Rotate(0, 0, redStick.getStickRectangle().getY() + redStick.getStickRectangle().getHeight());
//    redStick.getStickRectangle().getTransforms().add(rotate);
//
//    KeyValue rotateKeyValue = new KeyValue(rotate.angleProperty(), -90);
//    KeyFrame rotateKeyFrame = new KeyFrame(Duration.seconds(1), rotateKeyValue); // 1-second duration for rotation
//
//    // Setting up the height reset animation
//    KeyValue heightKeyValue = new KeyValue(redStick.getStickRectangle().heightProperty(), 0);
//    KeyFrame heightKeyFrame = new KeyFrame(Duration.seconds(1), heightKeyValue); // 1-second duration for height reset
//
//    // Setting up the Y position reset animation
//    KeyValue layoutYKeyValue = new KeyValue(redStick.getStickRectangle().layoutYProperty(), 500);
//    KeyFrame layoutYKeyFrame = new KeyFrame(Duration.seconds(1), layoutYKeyValue); // 1-second duration for Y position reset
//
//    // Creating and playing the timeline
//    Timeline timeline = new Timeline(rotateKeyFrame, heightKeyFrame, layoutYKeyFrame);
//    timeline.setOnFinished(event -> clickcount = 0); // Reset clickcount after animation finishes
//    timeline.play();
//}

    public void resetStick2(){
        redStick.getStickRectangle().setLayoutX(0.0);
        redStick.getStickRectangle().setLayoutX((double) (p1.getPillarRectangle().getWidth()-redStick.getStickRectangle().getWidth()));
        decreaseStickHeightFromTop();
//        redStick.getStickRectangle().setLayoutX(jaadu.getJadu().getLayoutX()+2*jaadu.getJadu().getFitWidth());
//        redStick.getStickRectangle().setX(0);


    }



    private void createNewPillar() {
        // Create and return a new pillar
//        System.out.println("p1"+ p1);
//        System.out.println("p2 "+(double)p2.getPillarRectangle().getWidth());
        p1 =p2; // Shift the current pillar to be the old pillar

        int dist = random.nextInt(600-170 + 1) + 170 ;// Calculate the distance for the new pillar
        p2 = new Pillar(dist, 700); // Adjust duration as needed
        gamePane.getChildren().add(p2.getPillarRectangle());


//        System.out.println("addded nwe pillar!!!!!");
//        System.out.println("p1"+ p1);
//        System.out.println("'p2"+p2);
        PauseTransition pause = new PauseTransition(Duration.millis(100));
        pause.setOnFinished(event -> {
            resetStick2();
        }); // Call resetStick2 after the delay
        pause.play(); // Start the pause
//

    }

    private void decreaseStickHeightFromTop() {
        new Thread(() -> {
            while (redStick.getStickRectangle().getHeight() > 0) {
                double decrement = 1; // Amount to decrease height by
                double newHeight = redStick.getStickRectangle().getHeight() - decrement;
                double newY = redStick.getStickRectangle().getY() + decrement; // Move Y position downward

                Platform.runLater(() -> {
                    redStick.getStickRectangle().setHeight(newHeight);
                    redStick.getStickRectangle().setY(newY); // Update the Y position
                });

                try {
                    Thread.sleep(1); // Small delay to slow down the height reduction
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Platform.runLater(() -> clickcount = 0); // Reset click count on JavaFX thread
        }).start();
    }




    public void flipPlayer() {
        isFlipped=(!isFlipped);
        Rotate rotate = new Rotate();
        rotate.setPivotX(jaadu.getJadu().getX()+jaadu.getJadu().getFitWidth());
        rotate.setPivotY(jaadu.getJadu().getY() + jaadu.getJadu().getFitHeight());
        // Set pivot point to the bottom of the stick

        jaadu.getJadu().getTransforms().add(rotate);
        rotate.setAngle(180);

    }

    public void pauseGame(){
        isPaused=true;
        for (TranslateTransition tl:alltrans
             ) {
            tl.pause();

        }
        for (Timeline tl:allAnims
             ) {tl.pause();

        }
    }

    public void resumeGame(){
        isPaused=false;
        for (TranslateTransition tl:alltrans
        ) {

            tl.play();

        }
        for (Timeline tl:allAnims
        ) {tl.play();

        }
    }




}




