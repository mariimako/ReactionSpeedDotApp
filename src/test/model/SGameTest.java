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
        testEnemy = new Enemy(SGame.HEIGHT/2+6,SGame.HEIGHT/2); // enemy two moves away
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
        testGame.spawnEnemy();
        assertTrue(testGame.getEnemies().size() == 1);
        assertTrue(testGame.getEnemies().get(0).getPosX() <= SGame.WIDTH);
        assertTrue(testGame.getEnemies().get(0).getPosX() >= 0);
        assertTrue(testGame.getEnemies().get(0).getPosY() <= SGame.HEIGHT);
        assertTrue(testGame.getEnemies().get(0).getPosY() >= 0);


        testGame.spawnEnemy();
        assertTrue(testGame.getEnemies().size() == 2); // should be spawned at location
        assertTrue(testGame.getEnemies().get(1).getPosX() <= SGame.WIDTH); // should be spawned at location
        assertTrue(testGame.getEnemies().get(1).getPosX() >= 0); // should be spawned at location
        assertTrue(testGame.getEnemies().get(1).getPosY() <= SGame.HEIGHT);
        assertTrue(testGame.getEnemies().get(1).getPosY() >= 0);

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


    @Test
    void checkPlayerTest() {
        // only one enemy can collide at once, test when two enemies are colliding, only one should be removed
        Enemy collidedEnemy = new Enemy(250, 250);
        Enemy nonCollidedEnemy = new Enemy(100, 100);
        Enemy anotherCollidedEnemy = new Enemy(250, 250);

        testGame.getEnemies().add(nonCollidedEnemy);
        testGame.getEnemies().add(collidedEnemy);
        testGame.getEnemies().add(anotherCollidedEnemy);
        testGame.getEnemies().add(nonCollidedEnemy);

        testGame.getBeings().add(collidedEnemy);
        testGame.getBeings().add(anotherCollidedEnemy);
        testGame.getBeings().add(nonCollidedEnemy);


        testGame.checkPlayer();

        assertEquals(anotherCollidedEnemy, testGame.getEnemies().get(1));
        assertEquals(3, testGame.getEnemies().size());
        assertEquals(80, testGame.getPlayer().health);

        assertEquals(nonCollidedEnemy, testGame.getBeings().get(2)); // the player is in beings
        assertEquals(3, testGame.getBeings().size()); // the player is in beings, so 2 elements
        assertEquals(testGame.getPlayer(), testGame.getBeings().get(0)); // the player is in beings, so 2 elements
    }

    @Test
    void update() {
        testGame.fireBullet();
        testGame.getEnemies().add(new Enemy(100, 200));
        testGame.getEnemies().add(new Enemy(200, 300));

        double initX = testGame.getEnemies().get(0).getPosX();
        double initY = testGame.getEnemies().get(1).getPosX();


        testGame.update();
        assertEquals(253, testGame.getPlayer().getPosX());
        assertEquals(250, testGame.getPlayer().getPosY());

        assertTrue(testGame.getEnemies().get(0).getPosX() != initX); //check it has moved
        assertTrue(testGame.getEnemies().get(1).getPosX() != initY); //check it has moved

        testGame.setCounter(testGame.SPAWN_RATE);
        testGame.apperancerate = 0;
        testGame.update();
        assertEquals(3, testGame.getEnemies().size()); // should spawn enemy
        assertEquals(-1, testGame.apperancerate); // should spawn enemy
        assertEquals(151, testGame.counter); // should spawn enemy


    }

    @Test
    void testUpdateSpeedUp() {
        int initPlayerSpeed = testGame.getPlayer().getSpeed();
        int initEnemySpeed = testEnemy.getSpeed();
        int initBulletSpeed = testBullet.getSpeed();
        testGame.getBeings().add(testEnemy);
        testGame.getBeings().add(testBullet);

        while(testGame.counter % testGame.SPEED_UP_RATE == 0) { // loop until speeds up
            testGame.update();
        }



        testGame.update();
        // should have sped up by 1

        assertEquals(initPlayerSpeed+1, testGame.getBeings().get(0).getSpeed());
        assertEquals(initEnemySpeed + 1, testGame.getBeings().get(1).getSpeed());
        assertEquals(initBulletSpeed+1, testGame.getBeings().get(2).getSpeed());
    }

    @Test
    void testFalseUpdates() {
        testGame.stopPlaying();
        testGame.update();
        assertEquals(1,testGame.getBeings().size());

    }

    @Test
    void testFalseUpdateCase() {
        testGame.counter = 20;
        testGame.update();
        assertEquals(1,testGame.getBeings().size());
        assertEquals(21,testGame.counter);



    }

}