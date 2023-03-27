package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BeingTest {
    private Player testPlayer;

    private Being difPosPlayer1;
    private Being difPosPlayer2;
    private Being difPosPlayer3;

    private Being difPosPlayer4;
    private Being difPosPlayer5;

    @BeforeEach
    void runBefore() {
        testPlayer= new Player();
         difPosPlayer1 = new Player((int) testPlayer.getPosX() + 100, (int) testPlayer.getPosY());
         difPosPlayer2 = new Player( (int) testPlayer.getPosX(), (int) testPlayer.getPosY()+100);
         difPosPlayer3 = new Player((int) testPlayer.getPosX() +
                50, (int) testPlayer.getPosY() + 100);
         difPosPlayer4 = new Player((int) testPlayer.getPosX(), (int) testPlayer.getPosY()+2);
         difPosPlayer5 = new Player((int) testPlayer.getPosX()+300, (int) testPlayer.getPosY());
    }

    @Test
    void decreaseHealthTest() {
        testPlayer.decreaseHealth(30);
        assertEquals(70, testPlayer.health);
    }

    @Test
    void collisionTest() {
        //new being that is at a different player, but same y pos. other cases are tested in subclasses
        assertFalse(testPlayer.collidedWith(difPosPlayer1));
        Being samePosPlayer = new Player(); // same position as player, in middle of gamestate
        assertTrue(testPlayer.collidedWith(samePosPlayer));

        // new being that has same x value but not y value
        assertFalse(testPlayer.collidedWith(difPosPlayer2));

        // new being that has no similar values
        assertFalse(testPlayer.collidedWith(difPosPlayer3));

    }

    @Test
    void collisionShapeTest() {

        assertTrue(testPlayer.collidedWith(difPosPlayer4));

        assertFalse(testPlayer.collidedWith(difPosPlayer5));

    }

    @Test
    void colorTest() {
        testPlayer.setColor(Color.WHITE);
        assertEquals(Color.WHITE, testPlayer.getColor());

    }
}
