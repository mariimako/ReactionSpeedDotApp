package model;

// represents an enemy, spawned at locations. When this hits an player, player's health decreases
public class Enemy extends Being {

    public Enemy(int spawnX, int spawnY) {
        super(spawnX, spawnY);
    }
}