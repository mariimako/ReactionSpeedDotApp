package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SGameTest {
    private SGame testGame;

    @BeforeEach
    void runBefore() { testGame= new SGame();}

    @Test
    void testCheckGameOver() {
        testGame.checkGameOver();
        assertTrue(testGame.isPlaying());
        testGame.getPlayer().health = 0;
        testGame.checkGameOver();
        assertFalse(testGame.isPlaying());
        testGame.stopPlaying();
        assertFalse(testGame.isPlaying());
    }

    @Test
    void testSpawnEnemy () {
        testGame.spawnEnemy(10, 20);
        assertEquals(10, testGame.getEnemies().get(0).getPosX());
        assertEquals(20, testGame.getEnemies().get(0).getPosY());

        testGame.spawnEnemy(20, 40);
        assertEquals(20, testGame.getEnemies().get(1).getPosX());
        assertEquals(40, testGame.getEnemies().get(1).getPosY());

    }

    void testCheckBullets () {
    }


}