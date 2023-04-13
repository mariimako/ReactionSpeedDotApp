package ui.drawer;

import model.*;

import java.awt.*;

// class to draw all game objects currently in the game state such as the player, enemy or bullets
public class GameDraw {

    SGame playingGame;

    public static final int PLAYER_SIZE_X = 30;
    public static final int PLAYER_SIZE_Y = 30;

    public static final int BULLET_SIZE_X = 10;
    public static final int BULLET_SIZE_Y = 10;

    public static final int ENEMY_SIZE_X = 30;
    public static final int ENEMY_SIZE_Y = 30;

    public GameDraw(SGame game) {
        this.playingGame = game;
    }

    /*
    EFFECTS: draws the game state
    MODFIES: g
    */
    public void draw(Graphics g) {
        for (Being sprite : playingGame.getBeings()) {
            if (!(sprite instanceof Player)) {
                drawBeing(g, sprite);
            }
        }

        drawBeing(g, playingGame.getPlayer());
    }


    /*
    EFFECTS: draw a singular given element in the game, depending on the object draw different shapes
    MODIFIES: g
     */
    public void drawBeing(Graphics g, Being e) {
        Color savedCol = g.getColor();
        if (e instanceof Enemy) {
            g.fillOval((int) e.getPosX() - ENEMY_SIZE_X / 2, (int) e.getPosY() - ENEMY_SIZE_Y / 2,
                    ENEMY_SIZE_X, ENEMY_SIZE_Y);
        } else if (e instanceof Bullet) {
            g.fillOval((int) e.getPosX() - BULLET_SIZE_X / 2, (int) e.getPosY() - BULLET_SIZE_Y / 2,
                    BULLET_SIZE_X, BULLET_SIZE_Y);
        } else if (e instanceof Player) {
            g.fill3DRect((int) e.getPosX() - PLAYER_SIZE_X / 2, (int) e.getPosY() - PLAYER_SIZE_Y / 2,
                    PLAYER_SIZE_X, PLAYER_SIZE_Y, false);
        }
        g.setColor(e.getColor());
        g.setColor(savedCol);
    }

}
