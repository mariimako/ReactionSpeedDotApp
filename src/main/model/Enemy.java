package model;

import org.json.JSONObject;

import java.awt.*;

// represents an enemy, spawned at locations. When this hits a player, player's health decreases
// it can also be eliminated by bullets
public class Enemy extends Being {

    /*
    EFFECTS: instantiates enemy in specified input location.
     */
    public Enemy(int spawnX, int spawnY) {
        super(spawnX, spawnY);
    }

    /*
    EFFECTS: move towards the player position, with speed varying by distance
    MODIFIES: this
     */
    public void enemyUpdate(Player player) {

        double distanceX = player.getPosX() - this.getPosX(); // x y distance to player
        double distanceY = player.getPosY() - this.getPosY();
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY); // use euclidean distance

        double maxSpeed = this.getSpeed();

        double slowDown = 10;

        if (distance < 100) { // slow down less when closer
            slowDown = 5;
        }

        double speed = distance / slowDown; // as enemy gets closer, slows down
        speed = Math.min(speed, maxSpeed); // do not exceed maximum speed, which is this speed

        double angle = Math.atan2(distanceY, distanceX); //angle from enemy to player
        double vx = speed * Math.cos(angle);
        double vy = speed * Math.sin(angle); // velocity components

        this.posX = this.posX + vx;
        this.posY = this.posY + vy;
    }
}
