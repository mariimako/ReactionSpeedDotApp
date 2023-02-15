package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BeingTest {
    private Player testPlayer;

    @BeforeEach
    void runBefore() { testPlayer= new Player();}

    @Test
    void decreaseHealthTest() {
        testPlayer.decreaseHealth(30);
        assertEquals(70, testPlayer.health);
    }

    @Test
    void collisionTest() {
        //new being that is at a different player
        Being difPosPlayer = new Player(testPlayer.getPosX()+100, testPlayer.getPosY()+500);
        assertFalse(testPlayer.collidedWith(difPosPlayer));
        Being samePosPlayer = new Player(); // same position as player, in middle of gamestate
        assertTrue(testPlayer.collidedWith(samePosPlayer));
    }
}