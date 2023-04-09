package ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.Timer;

import model.Event;
import model.EventLog;
import model.SGame;
import persistence.JsonReader;
import persistence.JsonWriter;


// class to run the game
public class Main extends JFrame {
    private static final int INTERVAL = 20;
    private SGame game;
    private GamePanel gp;
    private StatePanel sp;
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
        gp = new GamePanel(this.game);
        sp = new StatePanel(this.game);
        add(gp);
        add(sp, BorderLayout.NORTH);
        gp.repaint();
        addKeyListener(new KeyHandler());
        pack();
        setVisible(true);
        centreOnScreen();
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
                    sp.update();
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
    MODIFIES: this
     */
    private void pauseMenu() {
        timer.stop();
        game.stopPlaying();
        String opt = options();
        if (opt.equals("Load")) {
            load();
        } else if (opt.equals("Save")) {
            save();
        } else if (opt.equals("Set Speed!")) {
            speedUp();
        } else {
            JOptionPane.showMessageDialog(Main.this, "Returning to Game");
            timer.start();
        }
        game.startPlaying();
    }

    /*
    EFFECTS: opens the options available to the user, such as speeding up, loading or saving and returns input
     */
    private String options() {
        List<String> optionList = new ArrayList<>();
        optionList.add("Save");
        optionList.add("Load");
        optionList.add("Set Speed!");
        Object[] options = optionList.toArray();
        int value = JOptionPane.showOptionDialog(
                null,
                "Save, Load or Set Speed of Bullet and Player",
                "Options",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                optionList.get(0));
        return optionList.get(value);

    }


    /*
    EFFECTS: speed up game as much as the user specified, but recommend not to go over 20
    MODIFIES: game
     */
    private void speedUp() {
        String input = JOptionPane.showInputDialog(Main.this,
                "To what?");
        int speedUp = Integer.parseInt(input);

        while (game.getPlayer().getSpeed() < speedUp) {
            game.speedUp();
        }
        timer.start();
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
            JOptionPane.showMessageDialog(null, "Loaded");
            reset(jsonReader.read());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(Main.this, "Error reading file: "
                    + ex.getMessage());
        }
    }

    /*
    EFFECTS: resets game and creates a new one with the loaded game to be the gamstate
    MODIFIES: this
     */
    public void reset(SGame game) {
        remove(gp);
        this.game = game;
        gp = new GamePanel(this.game);
        sp = new StatePanel(this.game);
        add(gp);
        add(sp, BorderLayout.NORTH);
        gp.repaint();
        timer.restart();
        pack();
    }

    // Play the game
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            EventLog myObj = EventLog.getInstance();
            for (Event e: myObj) {
                System.out.println(e.toString());
            }
        }));
        SGame game = new SGame();
        game.startPlaying();
        new Main(game);
    }
}
