package model;

// represents a bullet shot by players that eliminate enemies when it hits them
public class Bullet extends Being {

    public Bullet(Player player) {
        super();
        this.posX = player.getPosX();
        this.posY = player.getPosY();
    }
}
