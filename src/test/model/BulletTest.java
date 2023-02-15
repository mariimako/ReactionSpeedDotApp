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
    Player boundaryPlayer = new Player(0, 0); //checking removal of boundary bullet

    @BeforeEach
    void runBefore() {
        testGame = new SGame();
        testPlayer = testGame.getPlayer();
        testEnemy = new Enemy(SGame.HEIGHT/2+6,SGame.HEIGHT/2);
        testBullet = new Bullet(testPlayer);

    }

    @Test
    void eliminateEnemyTest() {
        Player difPosPlayer = new Player( testGame.WIDTH/3,testGame.WIDTH/4 );
        Bullet difBullet = new Bullet(difPosPlayer);
        testGame.getBullets().add(difBullet); // add a bullet with different position
        testGame.fireBullet();
        testGame.getEnemies().add(testEnemy);
        testGame.getEnemies().add(testEnemy);
        testGame.moveBullets(); // move bullets in total 6 units, now should collide with testEnemy
        testGame.moveBullets();
        assertTrue(testGame.getBullets().get(1).collidedWith(testEnemy)); // one bullet added second should collide        assertTrue(testGame.getBullets().get(1).collidedWith(testEnemy)); // one bullet added second should collide
        assertFalse(testGame.getBullets().get(0).collidedWith(testEnemy)); // difBullet should not have collided
        testGame.checkBullets();
        assertEquals(difBullet, testGame.getBullets().get(0)); // should have one remaining bullet
        assertEquals(testEnemy, testGame.getEnemies().get(0)); // should have one more enemy remaining

        Bullet boundaryBullet = new Bullet(boundaryPlayer);
        testGame.getBullets().add(boundaryBullet);
        testGame.checkBullets();
        assertEquals(1, testGame.getBullets().size());
    }

    // check when no enemies are hit, bullets added should exist
    @Test
    void checkNoCollision() {
        Player difPosPlayer = new Player( testGame.WIDTH/3,testGame.WIDTH/4 );
        Bullet difBullet = new Bullet(difPosPlayer);
        testGame.getBullets().add(difBullet); // add a bullet with different position
        testGame.fireBullet();
        Enemy enemyDifPos = new Enemy(testGame.WIDTH/5, testGame.WIDTH/9 );
        testGame.getEnemies().add(enemyDifPos);
        testGame.getEnemies().add(enemyDifPos);

        testGame.checkBullets();

        assertEquals(2, testGame.getEnemies().size()); // bullets should have no bullets removed
        assertEquals(2, testGame.getBullets().size()); // bullets should have no bullets removed

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

    @Test
    void boundaryTest() {
        testPlayer.posX = testGame.WIDTH ; // put player on boundary
        testPlayer.posY = testGame.HEIGHT ;
        testGame.fireBullet(); // fire bullet, should be on boundary
        testGame.fireBullet(); // fire another bullet, should be on boundary
        assertEquals(testGame.WIDTH, testGame.getBullets().get(0).getPosX()); //check if at boundary
        assertEquals(testGame.HEIGHT, testGame.getBullets().get(0).getPosY());
        testGame.checkBullets(); // should delete only one bullet per tick
        assertEquals(1, testGame.getBullets().size()); //must be one bullet remaining
    }
}