package ui;

import model.SGame;
import model.Bullet;

import java.util.Scanner;

public class SurvivalGameApp {
    private final SGame game;
    private final Scanner scanner;


    /*
    EFFECTS: constructs game, opens menu
    MODIFIES: this
     */
    public SurvivalGameApp() throws InterruptedException {
        game = new SGame();
        scanner = new Scanner(System.in);
        menu();

        /*

        game = new SGame();
        while (game.isPlaying()) {
            game.update();
        }
         */
    }

    /*
    REQUIRES: input to be either p, e, q, r.
    EFFECTS: handles user input for things to do
     */

    private void menu() throws InterruptedException {
        System.out.println("Welcome to the game menu. Press the corresponding key to naviagte.");
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
                System.out.println("Goodbye");
        }
    }

    /*
    EFFECTS: spawns enemy at set location
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


    private void handleBullet() throws InterruptedException {
        game.fireBullet();
        Bullet bullet = game.getBullets().get(game.getBullets().size() - 1);
        System.out.println("You fired a bullet from the player!");
        Thread.sleep(1500);
        System.out.print("It is at position " +  bullet.getPosX() + ", " + bullet.getPosY()
                + " and it is travelling");

        switch (bullet.getDirection()) {
            case -1:
                if (bullet.getVerticalMovement()) {
                    System.out.print(" downwards");
                } else {
                    System.out.print(" leftwards");
                }
            case 1:
                if (bullet.getVerticalMovement()) {
                    System.out.print(" upwards");
                } else {
                    System.out.print(" rightwards");
                }
        }
        System.out.println(" with velocity " + bullet.SPEED);
        System.out.println("You have now fired " + game.getBullets().size() + " bullet(s)!");
        menu();
    }

    private void handlePlayer() throws InterruptedException {
        game.getPlayer().faceDown();
        game.getPlayer().move();
        Thread.sleep(2000);
        System.out.println("You have moved player " + game.getPlayer().SPEED + " units downwards!");
        Thread.sleep(2000);
        menu();
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
