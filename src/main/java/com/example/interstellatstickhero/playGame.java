package com.example.interstellatstickhero;

import java.io.Serializable;

public class playGame implements Serializable {
    private StickHero stickHero;
    private GameController gameController;
    private int score;
    private int positionX;
    private int cherries;
    private int PowerUp;

    private static int cherriesRequiredForRevive = 5;

    void startGame(StickHero stickHero){

    }

}
