package ui;

import model.SGame;
import model.Bullet;

import java.util.Scanner;

// a console based input ui, to navigate and perform actions related to the game
public class SurvivalGameApp {
    private final SGame game; // gamestate, represents game with 0 enemies, 0 bullets, player
    private final Scanner scanner; //scanner for input

    /*
    EFFECTS: constructs game, opens menu
    MODIFIES: this
     */
    public SurvivalGameApp() throws InterruptedException {
        game = new SGame(); // new gamestate, with a player instantiated in the center, 0 enemies and 0 bullets
        scanner = new Scanner(System.in); // scanner for user input
        menu(); // open up game menu when instantiated

        /* // THIS PART IS NOT USED YET, I kept it because I will use it in future phases

        game = new SGame();
        while (game.isPlaying()) {
            game.update();
        }
         */
    }

    /*
    REQUIRES: user keyboard input to be either p, e, q, r.
    EFFECTS: handles user input for things to do like moving player down, firing bullet, and quiting the game
     */
    private void menu() throws InterruptedException {
        System.out.println("Welcome to the game menu. Press the corresponding key to navigate.");
        Thread.sleep(1500);
        System.out.println("The player is currently at " + game.getPlayer().getPosX()
                + ", " + game.getPlayer().getPosY());
        Thread.sleep(1500);
        System.out.println("Menu:");
        System.out.println("Add New Enemy (p)          Move Player (e)");
        System.out.println("Quit  (q)                  Fire Bullet (f)");
        char input = scanner.next().charAt(0);
        switch (input) {
            case 'p':
                enemySpawn();
            case 'e':
                handlePlayer();
            case 'f':
                handleBullet();
            case 'q':
                System.out.println("Goodbye"); // if some other input not included, it will fire bullet
        }
    }

    /*
    EFFECTS: spawns enemy at set location specified by user, outputs how many enemies
     there are and returns to menu for more input
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
