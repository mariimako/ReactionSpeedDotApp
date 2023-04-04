package ui;

import model.SGame;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

// StatePanel is a new panel at the top to represent the health, enemies, and bullets fired
public class StatePanel extends JPanel {
    private static final String ENEMIES_PRESENT = "Enemies: ";
    private static final String BEING_FIRED = "Missiles ";
    private static final String HEALTH = "Health: ";
    private static final String SPEED = "Speed: ";
    private static final int LBL_WIDTH = 100;
    private static final int LBL_HEIGHT = 30;
    private SGame game;
    private JLabel invadersLbl;
    private JLabel missilesLbl;
    private JLabel healthLbl;
    private JLabel speedLbl;

    // Constructs a score panel
    // effects: sets the background colour and draws the initial labels;
    //          updates this with the game whose score is to be displayed
    public StatePanel(SGame g) {
        game = g;
        setBackground(new Color(180, 180, 180));
        invadersLbl = new JLabel(ENEMIES_PRESENT + game.getEnemies().size());
        invadersLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        missilesLbl = new JLabel(BEING_FIRED + g.getBullets().size());
        missilesLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        healthLbl = new JLabel(HEALTH + g.getPlayer().getHealth());
        healthLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        speedLbl = new JLabel(SPEED + g.getPlayer().getSpeed());
        healthLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        add(invadersLbl);
        add(Box.createHorizontalStrut(3));
        add(missilesLbl);
        add(Box.createHorizontalStrut(3));
        add(speedLbl);
        add(Box.createHorizontalStrut(3));
        add(healthLbl);
    }

    // Updates the score panel
    // modifies: this
    // effects:  updates number of invaders shot and number of missiles
    //           remaining to reflect current state of game
    public void update() {
        invadersLbl.setText(ENEMIES_PRESENT + game.getEnemies().size());
        missilesLbl.setText(BEING_FIRED + game.getBullets().size());
        speedLbl.setText(SPEED + game.getPlayer().getSpeed());
        healthLbl.setText(HEALTH + game.getPlayer().getHealth());
        repaint();
    }

}
