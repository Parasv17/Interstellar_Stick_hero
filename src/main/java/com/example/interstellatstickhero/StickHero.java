package com.example.interstellatstickhero;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.io.IOException;

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
        double newLayoutX =  distance;
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(jadu.layoutXProperty(), newLayoutX);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(800), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        if (delaySeconds > 0) {
            timeline.setDelay(Duration.seconds(delaySeconds));
        }

        ControlledBy.setAllAnims(timeline);
        timeline.setOnFinished(event -> {
            PauseTransition pause = new PauseTransition(Duration.millis(500));
            pause.setOnFinished(delayedEvent -> {
                try {
                    ControlledBy.checkStickAlignment();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ControlledBy.resetStick();
                ControlledBy.remAnims(timeline);
            });
            pause.play();
        });
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