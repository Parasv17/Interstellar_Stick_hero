package com.example.interstellatstickhero;

//import javax.swing.text.html.ImageView;

import javafx.animation.PauseTransition;
import javafx.animation.*;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class StickHero implements serializable{
    private GameController ControlledBy;

    private transient  ImageView jadu;
    public StickHero(ImageView hero,GameController cont){
        this.ControlledBy= cont;
        this.jadu= hero;

    }
    private Stick pole;
    private boolean isFlipped;
    private int score;

    public ImageView getJadu() {
        return jadu;
    }

    public void setJadu(ImageView jadu) {
        this.jadu = jadu;
    }

    private int cherries;
    private boolean moving;
    private boolean isRevived;

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isRevived() {
        return isRevived;
    }

    public void setRevived(boolean revived) {
        isRevived = revived;
    }

    private int PowerUp;
    private int positionX;

    public boolean onPillar(){
        return false;
    }

    public void flippedNTouchingPillar(boolean isFlipped, int positionX){

    }
    public void moveHorizontally(double distance, double delaySeconds) {
        double newLayoutX =  distance; // Calculate the new layoutX position

        // Create a Timeline for the animation
        Timeline timeline = new Timeline();

        // Set up the key values and key frames for the animation
        KeyValue keyValue = new KeyValue(jadu.layoutXProperty(), newLayoutX);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue); // Duration of 1 second for the animation

        // Add the key frame to the timeline
        timeline.getKeyFrames().add(keyFrame);

        // Set a delay if needed
        if (delaySeconds > 0) {
            timeline.setDelay(Duration.seconds(delaySeconds));
        }

        // Set up action to perform after the animation finishes
        timeline.setOnFinished(event -> {
            PauseTransition pause = new PauseTransition(Duration.millis(900));
            pause.setOnFinished(delayedEvent -> {
                ControlledBy.checkStickAlignment();
                ControlledBy.resetStick();
            });
            pause.play();
        });

        // Start the animation
        timeline.play();
    }

    public void saveGame(){

    }
    public void useCherries(int cherries){

    }

    public void usePowerUp(int powerUp){

    }

    public Stick getPole() {
        return pole;
    }

    public void setPole(Stick pole) {
        this.pole = pole;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCherries() {
        return cherries;
    }

    public void setCherries(int cherries) {
        this.cherries = cherries;
    }

    public int getPowerUp() {
        return PowerUp;
    }

    public void setPowerUp(int powerUp) {
        PowerUp = powerUp;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void increaseStickLength(int i) {
        if(!isFlipped){
            pole.setLength(pole.getLength()+i);
        }

    }
}