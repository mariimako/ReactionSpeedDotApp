package ui;

import model.Being;
import model.Enemy;
import model.SGame;
import model.Bullet;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// a console based input ui, to navigate and perform actions related to the game
public class SurvivalGameApp {
    private SGame game; // gamestate, represents game with 0 enemies, 0 bullets, player
    private final Scanner scanner; //scanner for input

    private static final String JSON_STORE = "./data/gamestate.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;


    /*
    EFFECTS: constructs game, opens menu
    MODIFIES: this
     */
    public SurvivalGameApp() throws InterruptedException {
        game = new SGame(); // new gamestate, with a player instantiated in the center, 0 enemies and 0 bullets
        scanner = new Scanner(System.in); // scanner for user input

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        menu(); // open up game menu when instantiated

    }

    /*
    REQUIRES: user keyboard input to be either p, e, q, r.
    EFFECTS: handles user input for things to do like moving player down, firing bullet, and quiting the game
    MODIFIES: this
     */
    private void menu() throws InterruptedException {
        displayMenu();
        char input = scanner.next().charAt(0);
        if (input == 'p') {
            enemySpawn();
        } else if (input == 'e') {
            handlePlayer();
        } else if (input == 'f') {
            handleBullet();
        } else if (input == 's') {
            saveGameState();
        } else if (input == 'l') {
            loadGameState();
        } else if (input == 'g') {
            showState();
        } else {
            System.out.println("Goodbye"); // if some other input, quit game
        }
    }

    /*
    EFFECTS: shows menu for possible user actions
     */
    private void displayMenu() throws InterruptedException {
        System.out.println("Welcome to the game menu. Press the corresponding key to navigate.");
        Thread.sleep(1500);
        System.out.println("The player is currently at " + game.getPlayer().getPosX()
                + ", " + game.getPlayer().getPosY());
        Thread.sleep(1500);
        System.out.println("Menu:");
        System.out.println("Add New Enemy (p)          Move Player Down (e)");
        System.out.println("Quit  (any other key)          Fire Bullet (f)");
        System.out.println("Save Game       (s)              Load Game (l) ");
        System.out.println("Show Game State (g)");

    }

    /*
    EFFECTS: saves gamestate into JSON
     */

    private void saveGameState() throws InterruptedException {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

        menu();
    }

    // MODIFIES: this
    // EFFECTS: loads gamestate from file
    private void loadGameState() throws InterruptedException {
        try {
            game = jsonReader.read();
            System.out.println("Loaded game from " + JSON_STORE);
            showState();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

        menu();
    }


    private void showState() throws InterruptedException {
        System.out.println("Here is your gamestate:");
        System.out.println("Player position: " + game.getPlayer().getPosX() + "," + game.getPlayer().getPosY());

        System.out.println("Enemies positions:");
        for (Being e : game.getEnemies()) {
            System.out.print(e.getPosX() + "," + e.getPosY());
        }
        System.out.println("Bullet positions:");
        for (Being b : game.getBullets()) {
            System.out.print(b.getPosX() + "," + b.getPosY());
        }
    }

    /*
     EFFECTS: spawns enemy at set location specified by user, outputs how many enemies
     there are and returns to menu for more input
     MODIFIES: this
     */
    private void enemySpawn() throws InterruptedException {
        System.out.println("Enter x position to spawn enemy");
        int initX = Integer.valueOf(scanner.next());
        System.out.println("Enter y position to spawn enemy");
        int initY = Integer.valueOf(scanner.next());
        game.spawnEnemy(initX, initY);
        Thread.sleep(1500);
        System.out.println("You added an enemy at " + initX + ", " + initY);
        Thread.sleep(1500);
        System.out.println("There are now " + game.getEnemies().size() + " enemie(s)!");
        Thread.sleep(1500);
        menu();
    }


    /*
    EFFECTS: fires a bullet in the gamestate by adding a bullet. Shows the postion and velocity, how many bullets
    were fired so far.
    MODIFIES: this
     */
    private void handleBullet() throws InterruptedException {
        game.fireBullet(); // fire a new bullet in gamestate
        Bullet bullet = game.getBullets().get(game.getBullets().size() - 1); // represents the bullet that was fired
        System.out.println("You fired a bullet from the player!");
        Thread.sleep(1500); // for readability, pause in between text
        System.out.print("It is at position " +  bullet.getPosX() + ", " + bullet.getPosY()
                + " and it is travelling"); // outputs position of bullet that has been fired, which should be at player

        if (bullet.getDirection() == -1) { // show which way the bullet is travelling,

            if (bullet.getVerticalMovement()) {
                System.out.print(" downwards"); // at this moment only downwards will output
            } else {
                System.out.print(" leftwards"); // other possibilities, not used in this phase
            }
        } else {
            if (bullet.getVerticalMovement()) {
                System.out.print(" upwards");
            } else {
                System.out.print(" rightwards"); // this may output when fired bullet before moving down
            }
        }
        System.out.println(" with velocity " + bullet.SPEED);
        System.out.println("You have now fired " + game.getBullets().size() + " bullet(s)!"); //gamestate stores bullets
        menu(); // go back to menu for more things to do
    }

    /*
    EFFECTS: handles movement of player
    MODIFIES: this
     */
    private void handlePlayer() throws InterruptedException {
        game.getPlayer().faceDown(); // face the player down
        game.getPlayer().move(); // move the player down
        Thread.sleep(2000);
        System.out.println("You have moved player " + game.getPlayer().SPEED + " units downwards!");
        Thread.sleep(2000);
        menu(); // return back to menu
    }


    /* FUTURE IMPLEMENTATIONS (ignore)
    public void move(KeyEvent e) {
        model.Player player = new model.Player();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                player.faceUp();
                break;
            case KeyEvent.VK_DOWN:
                player.faceDown();
                break;
            case KeyEvent.VK_LEFT:
                player.faceLeft();
                break;
            case KeyEvent.VK_RIGHT:
                player.faceRight();
                break;
            case KeyEvent.VK_SPACE:
                game.fireBullet();
                break;
        }
    }
     */
}
