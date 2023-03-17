package ui;

import model.SGame;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final String OVER = "Game Over!";
    private static final String REPLAY = "R to replay";
    private SGame game;

    // Constructs a game panel
    // effects:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(SGame g) {
        setPreferredSize(new Dimension(SGame.WIDTH, SGame.HEIGHT));
        setBackground(Color.GRAY);
        this.game = g;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);

        if (!game.isPlaying()) {
            gameOver(g);
        }
    }

    // Draws the game
    // modifies: g
    // effects:  the game is drawn onto the Graphics object g
    private void drawGame(Graphics g) {
        game.draw(g);
    }

    // Draws the "game over" message and replay instructions
    // modifies: g
    // effects:  draws "game over" and replay instructions onto g
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(OVER, g, fm, SGame.HEIGHT / 2);
        centreString(REPLAY, g, fm, SGame.HEIGHT / 2 + 50);
        g.setColor(saved);
    }

    // Centres a string on the screen
    // modifies: g
    // effects:  centres the string str horizontally onto g at vertical position ycoord
    private void centreString(String str, Graphics g, FontMetrics fm, int ycoord) {
        int width = fm.stringWidth(str);
        g.drawString(str, (SGame.WIDTH - width) / 2, ycoord);
    }
}
