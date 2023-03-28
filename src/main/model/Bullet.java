package model;

import org.json.JSONObject;

import java.awt.*;

// represents a bullet shot by players that eliminate enemies when it hits them
public class Bullet extends Being {

    protected Color color = new Color(103, 37, 11);

    /*
    EFFECTS: instantiates bullet that is the same as the player for direction, position
     */
    public Bullet(Player player) {
        this.direction = player.getDirection();
        this.verticalMovement = player.getVerticalMovement();
        this.posX = player.getPosX();
        this.posY = player.getPosY();
        this.setSpeed(5);
    }

    /*
    EFFECTS: instantiates bullet at specified position, used for loading game
     */
    public Bullet(int posX, int posY) {
        super(posX, posY);
    }

    @Override
    public void move() {
        super.move();
    }


}
