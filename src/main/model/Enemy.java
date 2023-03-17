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


}
