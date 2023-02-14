package model;

// represents a bullet shot by players that eliminate enemies when it hits them
public class Bullet extends Being {


    /*
    EFFECTS: instantiates bullet that is the same as the player for direction, position
     */
    public Bullet(Player player) {
        this.direction = player.getDirection();
        this.verticalMovement = player.getVerticalMovement();
        this.posX = player.getPosX();
        this.posY = player.getPosY();
    }
}
