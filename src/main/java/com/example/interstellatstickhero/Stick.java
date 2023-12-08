package com.example.interstellatstickhero;

import javafx.scene.shape.Rectangle;

public class Stick implements serializable{
    private Rectangle StickRectangle;

    public Stick(Rectangle stickRectangle) {
        StickRectangle = stickRectangle;
    }

    private int length ;
    private boolean isPowerUp;

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