package com.example.interstellatstickhero;

import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestStickman {

    @Test
    public void testHighScore() {
        int expectedHighScore = 100;
        highScore score = new highScore(expectedHighScore);
        assertEquals(expectedHighScore, score.getHighest(), "The high score should be set correctly");
    }

    @Test
    public void testGameState() {
        int expectedScore = 150;
        int expectedCherries = 5;
        SaveGameState gameState = new SaveGameState(expectedScore, expectedCherries);
        assertEquals(expectedScore, gameState.getScore(), "Score should be set correctly");
        assertEquals(expectedCherries, gameState.getCherryCount(), "Cherry count should be set correctly");
    }

    @Test
    public void testRandomWidthGenerator() {
        int min = 70;
        int max = 150;
        int randomWidth = Pillar.getRandomWidth(min, max);
        assertTrue(randomWidth >= min && randomWidth <= max, "Random width should be within the specified bounds");
    }
}
