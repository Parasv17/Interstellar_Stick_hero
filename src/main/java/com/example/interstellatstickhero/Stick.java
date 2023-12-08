package com.example.interstellatstickhero;

import javafx.scene.shape.Rectangle;

public class Stick implements serializable{
    private Rectangle StickRectangle;
    private GameController ControlledBy;

    public Stick(Rectangle stickRectangle,GameController cont) {
        this.ControlledBy= cont;
        StickRectangle = stickRectangle;
    }

    private int length ;
    private boolean isPowerUp;

    public Rectangle getStickRectangle() {
        return StickRectangle;
    }

    public void setStickRectangle(Rectangle stickRectangle) {
        StickRectangle = stickRectangle;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isPowerUp() {
        return isPowerUp;
    }

    public void setPowerUp(boolean powerUp) {
        isPowerUp = powerUp;
    }

    public void growNDropStick(){

    }
}