package com.example.interstellatstickhero;

import javafx.scene.control.Label;

import java.io.Serializable;

public class highScore implements Serializable {
    private int highest;
    private Label HSC;

    public highScore(int highest) {
        this.highest = highest;
    }

    public int getHighest() {
        return highest;
    }

    public void setHighest(int highest) {
        this.highest = highest;
    }
}
