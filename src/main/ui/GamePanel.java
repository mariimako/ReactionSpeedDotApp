package ui;

import model.SGame;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.drawer.GameDraw;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel {
    private static final String OVER = "Game Over!";
    private SGame game;

    private static final String JSON_STORE = "./data/gamestate.json";

    GameDraw gd;

    // EFFECTS:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(SGame g) {
        super();
        setPreferredSize(new Dimension(SGame.WIDTH, SGame.HEIGHT));
        setBackground(Color.WHITE);
        this.game = g;
        gd = new GameDraw(game);
        repaint();
        game.isPlaying();
    }

    /*
    EFFECTS: draws the gamestate, if gameover, dont draw anything else
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (game.isPlaying()) {
            drawGame(g);
        }

        if (!game.isPlaying()) {
            gameOver(g);
        }
    }


    // MODIFIES: g
    // EFFECTS:  the game is drawn onto the Graphics object g
    private void drawGame(Graphics g) {
        gd.draw(g);
    }

    // MODIFIES: g
    // EFFECTS:  draws "game over" and pops up replay option
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(OVER, g, fm, SGame.HEIGHT / 2);
        g.setColor(saved);
        String input = JOptionPane.showInputDialog(GamePanel.this,
                "Press R to restart, or exit");
        if (input.charAt(0) == ('r')) {
            SGame newGame = new SGame(); // start new game
            new Main(newGame);
        } else {
            System.exit(0);
        }

    }


    // MODIFIES: g
    // EFFECTS:  centres the string str horizontally onto g at vertical position ycoord
    private void centreString(String str, Graphics g, FontMetrics fm, int ycoord) {
        int width = fm.stringWidth(str);
        g.drawString(str, (SGame.WIDTH - width) / 2, ycoord);
    }
}
