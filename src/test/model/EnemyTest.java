package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnemyTest {
    private Player testPlayer;
    private Enemy testEnemy;
    @BeforeEach
    void runBefore() {
        testPlayer= new Player();
        testEnemy = new Enemy(0,0);
    }

    @Test
    void decreaseHealthTest() {
        testPlayer.decreaseHealth(30);
        assertEquals(70, testPlayer.health);
    }

    @Test
    void EnemyUpdateTest() {
        double intPosX = testEnemy.getPosX();
        double intPosY = testEnemy.getPosY();

        double distanceX = testPlayer.getPosX() - testEnemy.getPosX(); // x y distance to player
        double distanceY = testPlayer.getPosY() - testEnemy.getPosY();
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY); // use euclidean distance
        double speed = 3.0; // as enemy gets closer, slows down, should be 3.0

        double angle = Math.atan2(distanceY, distanceX); //angle from enemy to player

        double vx = speed * Math.cos(angle);
        double vy = speed * Math.sin(angle); // velocity components

        testEnemy.enemyUpdate(testPlayer);

        assertEquals(vx + intPosX, testEnemy.getPosX());
        assertEquals(vy + intPosY, testEnemy.getPosY());

    }

    @Test
    void EnemyCloseUpdateTest() { // check enemy getting closer to player or not
        testEnemy = new Enemy( 230, 230);
        double intPosX = testEnemy.getPosX();
        double intPosY = testEnemy.getPosY();

        double distanceX = testPlayer.getPosX() - testEnemy.getPosX(); // x y distance to player
        double distanceY = testPlayer.getPosY() - testEnemy.getPosY();

        double angle = Math.atan2(distanceY, distanceX); //angle from enemy to player

        double vx = testEnemy.getSpeed() * Math.cos(angle);
        double vy = testEnemy.getSpeed() * Math.sin(angle); // velocity components

        testEnemy.enemyUpdate(testPlayer);
        assertEquals(vx + intPosX, testEnemy.getPosX());
        assertEquals(vy + intPosY, testEnemy.getPosX());

    }
}