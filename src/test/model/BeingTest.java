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
        //new being that is at a different player, but same y pos. other cases are tested in subclasses
        Being difPosPlayer1 = new Player(testPlayer.getPosX()+100, testPlayer.getPosY());
        assertFalse(testPlayer.collidedWith(difPosPlayer1));
        Being samePosPlayer = new Player(); // same position as player, in middle of gamestate
        assertTrue(testPlayer.collidedWith(samePosPlayer));

        // new being that has same x value but not y value
        Being difPosPlayer2 = new Player(testPlayer.getPosX(), testPlayer.getPosY()+100);
        assertFalse(testPlayer.collidedWith(difPosPlayer2));

        // new being that has no similar values
        Being difPosPlayer3 = new Player(testPlayer.getPosX()+50, testPlayer.getPosY()+100);
        assertFalse(testPlayer.collidedWith(difPosPlayer3));

    }
}