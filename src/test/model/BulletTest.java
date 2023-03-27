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
    Player difPosPlayer = new Player(testGame.WIDTH / 3, testGame.WIDTH / 4); // player in different pos
    Bullet difBullet = new Bullet(difPosPlayer); // bullet in different position

    @BeforeEach
    void runBefore() {
        testGame = new SGame();
        testPlayer = testGame.getPlayer();
        testEnemy = new Enemy(SGame.HEIGHT/2+3,SGame.HEIGHT/2); // enemy two moves away
        testBullet = new Bullet(testPlayer); // create bullet based off of gamestate player

    }



    // check when no enemies are hit, bullets added should exist
    @Test
    void checkNoCollision() {
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
        testBullet.move(); // moved 3 units, has now collided because of rectangle size
        assertTrue(testBullet.collidedWith(testEnemy));
        testBullet.move(); // moded 6 units, now should have collided
        assertTrue(testBullet.collidedWith(testEnemy));
    }

    @Test
    void boundaryTest() {
        Bullet boundaryBullet = new Bullet(boundaryPlayer); // bullet on boundary: 0, 0
        testGame.getBullets().add(boundaryBullet);
        testGame.checkBullets();
        assertEquals(0, testGame.getBullets().size()); // should have deleted this bullet

        testPlayer.posX = testGame.WIDTH ; // put player on boundary
        testPlayer.posY = testGame.HEIGHT ;
        testGame.fireBullet(); // fire bullet, should be on boundary: WIDTH, HEIGHT
        testGame.fireBullet(); // fire another bullet, should be on boundary
        assertEquals(testGame.WIDTH, testGame.getBullets().get(0).getPosX()); //check if at boundary
        assertEquals(testGame.HEIGHT, testGame.getBullets().get(0).getPosY());
        testGame.checkBullets(); // should delete only one bullet per tick
        assertEquals(1, testGame.getBullets().size()); //must be one bullet remaining, only delete one bullet
    }
}