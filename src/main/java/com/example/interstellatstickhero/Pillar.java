package com.example.interstellatstickhero;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

import java.util.Random;

public class Pillar implements serializable{
    private transient Rectangle pillarRectangle;
    private int width;
    private int positionX;
    private static final Random random = new Random();
    public Pillar(Pillar otherPillar) {
        this.width = otherPillar.width;
        this.positionX = otherPillar.positionX;
        this.bonusLength = otherPillar.bonusLength;
        this.bonusPositionX = otherPillar.bonusPositionX;

        // Create a new Rectangle with the same properties
        this.pillarRectangle = new Rectangle(
                otherPillar.pillarRectangle.getWidth(),
                otherPillar.pillarRectangle.getHeight(),
                otherPillar.pillarRectangle.getFill());
        this.pillarRectangle.setArcWidth(otherPillar.pillarRectangle.getArcWidth());
        this.pillarRectangle.setArcHeight(otherPillar.pillarRectangle.getArcHeight());
        this.pillarRectangle.setStroke(otherPillar.pillarRectangle.getStroke());
        this.pillarRectangle.setStrokeType(otherPillar.pillarRectangle.getStrokeType());
        this.pillarRectangle.setStrokeWidth(otherPillar.pillarRectangle.getStrokeWidth());
        this.pillarRectangle.setLayoutX(otherPillar.pillarRectangle.getLayoutX());
        this.pillarRectangle.setLayoutY(otherPillar.pillarRectangle.getLayoutY());
        // Note: You might also want to copy other properties if they are used
    }
    public Pillar(int positionX, int durationMillis) {
        this.width = getRandomWidth(70, 150); // Random width between 70 and 150
        this.positionX = positionX ;// Random distance between 40 and 100

        pillarRectangle = new Rectangle(width, 150, Color.valueOf("#000000b9"));
        pillarRectangle.setArcWidth(5.0);
        pillarRectangle.setArcHeight(5.0);
        pillarRectangle.setStroke(Color.BLACK);
        pillarRectangle.setStrokeType(StrokeType.INSIDE);
        pillarRectangle.setStrokeWidth(0.0);
        pillarRectangle.setLayoutX(this.positionX);
        pillarRectangle.setLayoutY(269.0);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(durationMillis), pillarRectangle);
        fadeTransition.setFromValue(0); // Start from fully transparent
        fadeTransition.setToValue(1);   // End with fully opaque
        fadeTransition.play();
    }
    private static int getRandomWidth(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private static int getRandomDistance(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private int bonusLength;
    private int bonusPositionX;

    public int getWidth() {
        return width;
    }

    public Rectangle getPillarRectangle(){
        return pillarRectangle;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getBonusLength() {
        return bonusLength;
    }

    public void setBonusLength(int bonusLength) {
        this.bonusLength = bonusLength;
    }

    public int getBonusPositionX() {
        return bonusPositionX;
    }

    public void setBonusPositionX(int bonusPositionX) {
        this.bonusPositionX = bonusPositionX;
    }
}
