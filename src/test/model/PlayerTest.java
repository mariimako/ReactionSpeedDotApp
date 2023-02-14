package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
    private Player testPlayer;

    @BeforeEach
    void runBefore() { testPlayer= new Player();}

    @Test
    void decreaseHealthTest() {
        testPlayer.decreaseHealth(30);
        assertEquals(70, testPlayer.health);
    }

    @Test
    void moveTest() {
        testPlayer.move(); // initially moving right
        assertEquals(SGame.WIDTH / 2 + testPlayer.SPEED, testPlayer.getPosX());
        testPlayer.faceUp(); //move upwards
        testPlayer.move();
        assertEquals(SGame.HEIGHT / 2 + testPlayer.SPEED, testPlayer.getPosY());
        testPlayer.faceLeft(); //move leftwards
        testPlayer.move();
        assertEquals(SGame.WIDTH / 2, testPlayer.getPosX());
        testPlayer.faceDown(); //move downwards
        testPlayer.move();
        assertEquals(SGame.HEIGHT / 2, testPlayer.getPosY());
    }

    @Test
    void topRightBoundaryTest() {
        testPlayer = new Player(SGame.WIDTH, SGame.HEIGHT); //boundary tests, at right boundary, top bounary
        testPlayer.move(); //  moving right
        assertEquals(SGame.WIDTH, testPlayer.getPosX());
        testPlayer.faceLeft();
        testPlayer.move(); //  moving left
        assertEquals(SGame.WIDTH - testPlayer.SPEED, testPlayer.getPosX());
        testPlayer.faceUp(); // moving up, top boundary test
        testPlayer.move();
        assertEquals(SGame.HEIGHT, testPlayer.getPosY());
        testPlayer.faceDown(); // moving up
        testPlayer.move();
        assertEquals(SGame.HEIGHT - testPlayer.SPEED, testPlayer.getPosY());

    }

    @Test
    void leftBottomBoundaryTest () {
        testPlayer = new Player(0, 0);  // boundary test, at left boundary and bottom test
        testPlayer.faceLeft();
        testPlayer.move(); //  moving left
        assertEquals(0, testPlayer.getPosX());
        testPlayer.faceRight();
        testPlayer.move(); //  moving right
        assertEquals(0 + testPlayer.SPEED, testPlayer.getPosX());

        testPlayer.faceDown(); // moving down, bottom boundary test
        testPlayer.move();
        assertEquals(0, testPlayer.getPosY());
        testPlayer.faceUp(); // moving up
        testPlayer.move();
        assertEquals(0+testPlayer.SPEED, testPlayer.getPosY());
    }
}