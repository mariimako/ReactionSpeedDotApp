package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BulletTest {
    private Bullet testBullet;
    private Enemy testEnemy;
    private Player testPlayer;
    private SGame testGame;

    @BeforeEach
    void runBefore() {
        testGame = new SGame();
        testPlayer = testGame.getPlayer();
        testEnemy = new Enemy(SGame.HEIGHT/2+6,SGame.HEIGHT/2);
        testBullet = new Bullet(testPlayer);

    }

    @Test
    void eliminateEnemyTest() {
        testGame.fireBullet();
        testGame.getEnemies().add(testEnemy);
        testGame.moveBullets();
        testGame.moveBullets();
        assertTrue(testGame.getBullets().get(0).collidedWith(testEnemy));
        testGame.checkBullets();
        assertEquals(new ArrayList(), testGame.getBullets());

        Player boundaryPlayer = new Player(0, 0); //checking removal of boundary bullet
        Bullet boundaryBullet = new Bullet(boundaryPlayer);
        testGame.getBullets().add(boundaryBullet);
        testGame.checkBullets();
        assertEquals(new ArrayList(), testGame.getBullets());
    }

    @Test
    void collisionEnemyTest() {
        assertEquals(testPlayer.getDirection(), testBullet.getDirection());
        assertEquals(1, testBullet.getDirection());
        assertFalse(testBullet.getVerticalMovement());
        testBullet.move();
        assertFalse(testBullet.collidedWith(testEnemy));
        testBullet.move();
        assertTrue(testBullet.collidedWith(testEnemy));




    }
}