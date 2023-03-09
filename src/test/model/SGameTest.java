package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class SGameTest {
    private Bullet testBullet;
    private Enemy testEnemy;
    private Player testPlayer;
    private SGame testGame;

    Player boundaryPlayer = new Player(0, 0); //checking removal of boundary bullet
    Player difPosPlayer = new Player(testGame.WIDTH / 3, testGame.WIDTH / 4); // player in different pos
    Bullet difBullet = new Bullet(difPosPlayer); // bullet in different position

    @BeforeEach
    void runBefore() {
        testGame = new SGame();
        testPlayer = testGame.getPlayer();
        testEnemy = new Enemy(SGame.HEIGHT/2+Being.SPEED*2,SGame.HEIGHT/2); // enemy two moves away
        testBullet = new Bullet(testPlayer); // create bullet based off of gamestate player

    }


    @Test
    void testCheckGameOver() {
        testGame.checkGameOver();
        assertTrue(testGame.isPlaying()); // be playing
        testGame.getPlayer().health = 0; // player health is 0, should be game over
        testGame.checkGameOver();
        assertFalse(testGame.isPlaying()); // not playing
        testGame.stopPlaying();
        assertFalse(testGame.isPlaying()); // still not playing
    }

    @Test
    void testSpawnEnemy () {
        testGame.spawnEnemy(10, 20);
        assertEquals(10, testGame.getEnemies().get(0).getPosX()); // should be spawned at location
        assertEquals(20, testGame.getEnemies().get(0).getPosY());

        testGame.spawnEnemy(20, 40);
        assertEquals(20, testGame.getEnemies().get(1).getPosX()); // multiple should be able to be added
        assertEquals(40, testGame.getEnemies().get(1).getPosY()); // this enemy should be at index (1)

    }

    @Test
    void toJsonTest () {
        JsonReader reader = new JsonReader("./data/testWriterGeneralGameState.json");
        JsonWriter writer = new JsonWriter("./data/testWriterGeneralGameState.json");
        try {
            SGame gs = reader.read();
            assertEquals(250, gs.getPlayer().getPosX());
            assertEquals(250, gs.getPlayer().getPosY());
            assertEquals(1, gs.getPlayer().getDirection());
            assertFalse(gs.getPlayer().getVerticalMovement());

            assertEquals("{\"enemies\":" +
                    "[{\"positionY\":1,\"verticalMovement\":false," +
                    "\"positionX\":1,\"direction\":1}," +
                    "{\"positionY\":100,\"verticalMovement\":false," +
                    "\"positionX\":100,\"direction\":1}]", gs.toJson().toString().substring(0,154));

            assertEquals(",\"player\":[{\"positionY\":250,\"verticalMovement\":false," +
                    "\"positionX\":250,\"direction\":1}],\"bullets\":[{\"positionY\":20,\"verticalMovement\":false," +
                    "\"positionX\":10,\"direction\":1},{\"positionY\":40,\"verticalMovement\":false,\"positionX\":30," +
                    "\"direction\":1}]}", gs.toJson().toString().substring(154,393));

            writer.open();
            writer.write(gs);
            writer.close();
            gs = reader.read();

            assertEquals(250, gs.getPlayer().getPosX());
            assertEquals(250, gs.getPlayer().getPosY());
            assertEquals(1, gs.getPlayer().getDirection());
            assertFalse(gs.getPlayer().getVerticalMovement());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void checkBulletsTest() {
        testGame.getBullets().add(difBullet); // add a bullet with different position
        testGame.fireBullet(); // add a bullet with default position (center of screen)
        testGame.getEnemies().add(testEnemy); // add two enemies in near center location
        testGame.getEnemies().add(testEnemy);
        testGame.moveBullets(); // move bullets in total 6 units, now should collide with testEnemy
        testGame.moveBullets(); // only the bullets created by testPlayer, not difPosPlayer should have been collided
        assertTrue(testGame.getBullets().get(1).collidedWith(testEnemy)); // one bullet added second should collide        assertTrue(testGame.getBullets().get(1).collidedWith(testEnemy)); // one bullet added second should collide
        assertFalse(testGame.getBullets().get(0).collidedWith(testEnemy)); // difBullet should not have collided
        testGame.checkBullets(); // delete one colliding bullet
        assertEquals(difBullet, testGame.getBullets().get(0)); // should have one remaining bullet, in dif pos
        assertEquals(testEnemy, testGame.getEnemies().get(0)); // only one enemy eliminated per call
    }

}