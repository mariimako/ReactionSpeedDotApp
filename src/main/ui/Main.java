package ui;


import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.Timer;



import model.SGame;
import persistence.JsonReader;
import persistence.JsonWriter;

// class to run the game
public class Main extends JFrame {
    private static final int INTERVAL = 20;
    private SGame game;
    private GamePanel gp;
    private Timer timer;

    private static final String JSON_STORE = "./data/gamestate.json";

    JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    JsonReader jsonReader = new JsonReader(JSON_STORE);

    // EFFECTS: sets up window and adds key listners, panels
    public Main(SGame game) {
        super("Survival Dot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        this.game = game;
        gp = new GamePanel(game);
        add(gp);
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
        addTimer();
        timer.start();
    }


    // EFFECTS:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        timer = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (game.isPlaying()) {
                    game.update();
                    gp.repaint();
                }

            }
        });
    }

    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    /*
     * A key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        /*
        EFFECTS: responds to key event
         */
        @Override
        public void keyPressed(KeyEvent e) {
            keyPress(e.getKeyCode());
        }
    }

    // MODIFIES: this
    // EFFECTS:  changes player direction, fires bullets or pauses game in response to
    //           given key pressed code
    public void keyPress(int keyCode) {
        if (keyCode == KeyEvent.VK_KP_LEFT || keyCode == KeyEvent.VK_LEFT) {
            game.getPlayer().faceLeft();
        } else if (keyCode == KeyEvent.VK_KP_RIGHT || keyCode == KeyEvent.VK_RIGHT) {
            game.getPlayer().faceRight();
        } else if (keyCode == KeyEvent.VK_SPACE) {
            game.fireBullet();
        } else if (keyCode == KeyEvent.VK_KP_UP || keyCode == KeyEvent.VK_UP) {
            game.getPlayer().faceDown();
        } else if (keyCode == KeyEvent.VK_KP_DOWN || keyCode == KeyEvent.VK_DOWN) {
            game.getPlayer().faceUp();
        } else if (keyCode == KeyEvent.VK_ESCAPE) {
            pauseMenu();
        }
    }

    /*
    EFFECTS: opens up the pause menu, which can load or save the game
     */
    private void pauseMenu() {
        timer.stop();
        String input = JOptionPane.showInputDialog(null, "Load or Save (l/s), "
                + "or return to game by pressing any other key");

        if (input.charAt(0) == ('l')) {
            load();
        } else if (input.charAt(0) == ('s')) {
            save();
        } else {
            JOptionPane.showMessageDialog(Main.this, "Returning to Game");
            timer.start();
        }
    }


    /*
    MODIFIES: this
    EFFECTS: saves current gamestate
     */
    private void save() {
        try {
            timer.stop();
            jsonWriter.open();
            jsonWriter.write(game);
            JOptionPane.showMessageDialog(Main.this, "Saved");
            jsonWriter.close();
            timer.start();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(Main.this, "Error saving file: "
                    + ex.getMessage());
        }
    }

    /*
    EFFECTS: loads most recent saved gamestate, and starts a new game with that state
    MODIFIES: this
     */
    private void load() {
        try {
            timer.stop();
            SGame game = jsonReader.read();
            JOptionPane.showMessageDialog(null, "Loaded. Press Enter to Continue");
            new Main(game);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(Main.this, "Error reading file: "
                    + ex.getMessage());
        }
    }


    // Play the game
    public static void main(String[] args) {
        SGame game = new SGame();
        new Main(game);
    }
}
