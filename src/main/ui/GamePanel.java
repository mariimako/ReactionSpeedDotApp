package ui;

import model.SGame;
import ui.drawer.GameDraw;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class GamePanel extends JPanel {

    private SGame game;
    private GameDraw gd;

    // EFFECTS:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(SGame g) {
        setPreferredSize(new Dimension(SGame.WIDTH, SGame.HEIGHT));
        setBackground(Color.WHITE);
        this.game = g;
        gd = new GameDraw(game);
        repaint();
    }

    /*
    EFFECTS: draws the gamestate, if gameover, dont draw anything else
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (game.isPlaying()) {
            gd.draw(g);
        } else {
            gameOver(g);
        }

    }



    // MODIFIES: g
    // EFFECTS:  draws "game over" and pops up replay or quit option
    private void gameOver(Graphics g) {
        List<String> optionList = new ArrayList<>();
        optionList.add("Restart");
        optionList.add("Quit");

        Object[] options = optionList.toArray();

        int value = JOptionPane.showOptionDialog(
                null,
                "Game Over! Restart or Quit the Game",
                "Options",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                optionList.get(0));

        String opt = optionList.get(value);
        if (opt.charAt(0) == ('R')) {
            SGame newGame = new SGame(); // start new game
            new Main(newGame);
        } else {
            System.exit(0);
        }

    }
}
