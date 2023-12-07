package com.example.interstellatstickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
public class Pillar implements serializable{
    private Rectangle pillarRectangle;
    private int width;
    private int positionX;

    public Pillar(int width, int positionX, int durationMillis) {
        pillarRectangle = new Rectangle(width, 269, Color.BLUE);
        pillarRectangle.setLayoutX(positionX);
        pillarRectangle.setLayoutY(0);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(durationMillis), pillarRectangle);
        translateTransition.setByX(-positionX);
        translateTransition.play();
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
