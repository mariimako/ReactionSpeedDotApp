package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.drawer.GameDraw;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BeingTest {
    private Player testPlayer;

    private Being difPosPlayer1;
    private Being difPosPlayer2;
    private Being difPosPlayer3;

    private Being difPosBeing4;
    private Being difPosBeing5;
    private Being difPosBeing6;

    @BeforeEach
    void runBefore() {
        testPlayer= new Player();
         difPosPlayer1 = new Player((int) testPlayer.getPosX() + 100, (int) testPlayer.getPosY());
         difPosPlayer2 = new Player( (int) testPlayer.getPosX(), (int) testPlayer.getPosY()+100);
         difPosPlayer3 = new Player((int) testPlayer.getPosX() +
                50, (int) testPlayer.getPosY() + 100);
        difPosBeing4 = new Bullet((int) testPlayer.getPosX() + GameDraw.BULLET_SIZE_X / 2,
                 (int) testPlayer.getPosY() - GameDraw.BULLET_SIZE_Y / 2);
        difPosBeing5 = new Enemy((int) testPlayer.getPosX() - GameDraw.ENEMY_SIZE_X / 2,
                 (int) testPlayer.getPosY() + GameDraw.ENEMY_SIZE_Y / 2);
        difPosBeing6 = new Player((int) testPlayer.getPosX() - GameDraw.PLAYER_SIZE_X / 2,
                 (int) testPlayer.getPosY() + GameDraw.PLAYER_SIZE_Y / 2);

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

        assertTrue(testPlayer.collidedWith(difPosBeing4));
        difPosBeing4 = new Bullet ((int) testPlayer.getPosX() - GameDraw.BULLET_SIZE_X / 2,
                (int) testPlayer.getPosY());
        assertTrue(testPlayer.collidedWith(difPosBeing4));

        difPosBeing4 = new Bullet ((int) testPlayer.getPosX(),
                (int) testPlayer.getPosY() - GameDraw.BULLET_SIZE_Y / 2);
        assertTrue(testPlayer.collidedWith(difPosBeing4));

        assertTrue(testPlayer.collidedWith(difPosBeing5));
        difPosBeing5 = new Enemy((int) testPlayer.getPosX(),
                (int) testPlayer.getPosY() - GameDraw.ENEMY_SIZE_Y / 2);
        assertTrue(testPlayer.collidedWith(difPosBeing5));
        difPosBeing5 = new Enemy((int) testPlayer.getPosX(),
                (int) testPlayer.getPosY() + GameDraw.ENEMY_SIZE_Y / 2);
        assertTrue(testPlayer.collidedWith(difPosBeing5));

        assertTrue(testPlayer.collidedWith(difPosBeing6));
        difPosBeing6 = new Player((int) testPlayer.getPosX() - GameDraw.PLAYER_SIZE_X / 2,
                (int) testPlayer.getPosY());
        assertTrue(testPlayer.collidedWith(difPosBeing6));

        difPosBeing6 = new Player((int) testPlayer.getPosX(),
                (int) testPlayer.getPosY() + GameDraw.PLAYER_SIZE_Y / 2);
        assertTrue(testPlayer.collidedWith(difPosBeing6));

        difPosBeing4 = new Bullet ((int) testPlayer.getPosX(),
                (int) testPlayer.getPosY() - GameDraw.ENEMY_SIZE_Y / 2);
        assertTrue(testPlayer.collidedWith(difPosBeing4));
        assertTrue(difPosBeing4.collidedWith(testPlayer));

        testPlayer = new Player((int) difPosBeing4.posX,
                (int) difPosBeing4.getPosY() - GameDraw.ENEMY_SIZE_Y / 2 - 10);
        assertFalse(testPlayer.collidedWith(difPosBeing4));

        testPlayer = new Player((int) difPosBeing4.posX,
                (int) difPosBeing4.getPosY() + GameDraw.ENEMY_SIZE_Y / 2 + 10);
        assertFalse(testPlayer.collidedWith(difPosBeing4));

    }

    @Test
    void colorTest() {
        testPlayer.setColor(Color.WHITE);
        assertEquals(Color.WHITE, testPlayer.getColor());

    }
}
