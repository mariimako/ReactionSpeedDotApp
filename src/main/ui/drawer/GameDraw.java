package ui.drawer;

import model.*;

import java.awt.*;

public class GameDraw {

    SGame playingGame;

    public static final int PLAYER_SIZE_X = 30;
    public static final int PLAYER_SIZE_Y = 30;

    public static final int BULLET_SIZE_X = 5;
    public static final int BULLET_SIZE_Y = 8;

    public static final int ENEMY_SIZE_X = 30;
    public static final int ENEMY_SIZE_Y = 50;


    public GameDraw(SGame game) {
        playingGame = game;
    }

    /*
    EFFECTS: draws the game state
    MODFIES: g
    */
    public void draw(Graphics g) {
        for (Being sprite : playingGame.getBeings()) {
            drawBeing(g, sprite);
        }

        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        String str = "Current Enemies: " + playingGame.getEnemies().size();
        g.drawString(str, SGame.WIDTH - fm.stringWidth(str), SGame.HEIGHT);
        str = "Current Health: " + playingGame.getPlayer().getHealth();
        g.drawString(str,  0, SGame.HEIGHT);
        g.setColor(saved);

    }


    public void drawBeing(Graphics g, Being e) {
        Color savedCol = e.getColor();
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
