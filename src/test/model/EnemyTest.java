package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnemyTest {
    private Player testPlayer;

    @BeforeEach
    void runBefore() { testPlayer= new Player();}

    @Test
    void decreaseHealthTest() {
        testPlayer.decreaseHealth(30);
        assertEquals(70, testPlayer.health);
    }
}