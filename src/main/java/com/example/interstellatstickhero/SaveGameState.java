package com.example.interstellatstickhero;

import java.io.Serializable;

public class SaveGameState implements Serializable {
    private int Score;
    private  int cherryCount;

    public SaveGameState(int score, int cherryCount) {
        this.Score = score;
        this.cherryCount = cherryCount;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public int getCherryCount() {
        return cherryCount;
    }

    public void setCherryCount(int cherryCount) {
        this.cherryCount = cherryCount;
    }
}
