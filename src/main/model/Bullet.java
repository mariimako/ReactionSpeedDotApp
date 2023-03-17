package model;

import org.json.JSONObject;

import java.awt.*;

// represents a bullet shot by players that eliminate enemies when it hits them
public class Bullet extends Being {

    private static final Color COLOR = new Color(128, 50, 20);
    public static final int SIZE_X = 5;
    public static final int SIZE_Y = 8;

    /*
    EFFECTS: instantiates bullet that is the same as the player for direction, position
     */
    public Bullet(Player player) {
        this.direction = player.getDirection();
        this.verticalMovement = player.getVerticalMovement();
        this.posX = player.getPosX();
        this.posY = player.getPosY();
    }

    /*
    EFFECTS: instantiates bullet at specified position, used for loading game
     */
    public Bullet(int posX, int posY) {
        super(posX, posY);
    }

    @Override
    public void draw(Graphics g) {
        Color savedCol = g.getColor();
        g.setColor(COLOR);
        g.fillOval(getPosX() - SIZE_X / 2, getPosY() - SIZE_Y / 2, SIZE_X, SIZE_Y);
        g.setColor(savedCol);
    }
}
