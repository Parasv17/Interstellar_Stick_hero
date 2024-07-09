package com.example.interstellatstickhero;

import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Stick implements Serializable {
    private static Stick instance;
    private Rectangle stickRectangle;
    private GameController controlledBy;
    private int length;
    private boolean isPowerUp;

    Stick(Rectangle stickRectangle, GameController cont) {
        this.controlledBy = cont;
        this.stickRectangle = stickRectangle;
    }

    public static Stick getInstance(Rectangle stickRectangle, GameController cont) {
        if (instance == null) {
            instance = new Stick(stickRectangle, cont);
        }
        return instance;
    }

    // Getters and setters
    public Rectangle getStickRectangle() {
        return stickRectangle;
    }

    public void setStickRectangle(Rectangle stickRectangle) {
        this.stickRectangle = stickRectangle;
    }

    public GameController getControlledBy() {
        return controlledBy;
    }

    public void setControlledBy(GameController controlledBy) {
        this.controlledBy = controlledBy;
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
        this.isPowerUp = powerUp;
    }

    public void growNDropStick() {
        // Implementation...
    }
}
