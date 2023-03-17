package model;



// Represents a user controlled player, with positions and health, extending the Being class

import java.awt.*;

public class Player extends Being {

    protected int health; // remaining health of player
    private static final int SIZE_X = 15;
    private static final int SIZE_Y = 8;
    private static final int TANK_Y = SGame.HEIGHT - 40;
    private static final Color COLOR = new Color(250, 128, 20);

    /*
     * EFFECTS: player is initialized at the initial position in the middle of screen, with 100 health
     */
    public Player() {
        super();
        this.posX = SGame.HEIGHT / 2;
        this.posY = SGame.WIDTH / 2;
        this.health = 100;
    }

    /*
     * EFFECTS: player is initialized at the initial position of x and y and health. Mainly Used for testing purposes
     * does not require x and y to be within boundary, as it is updated
     */
    public Player(int initX, int initY) {
        super(initX, initY);
        this.health = 100;
    }

    /*
    EFFECTS: moves player, doesn't move if at boundary
    MODIFIES: this
     */
    @Override
    public void move() {
        super.move();
        handleBoundary();
    }


    /*
    MODIFIES: this
    EFFECTS: faces the player left, allows horizontal movement
     */
    public void faceLeft() {
        verticalMovement = false;
        direction = -1;
    }

    /*
    MODIFIES: this
    EFFECTS: faces the player right, allows horizontal movement
     */
    public void faceRight() {
        verticalMovement = false;
        direction = 1;

    }

    /*
    MODIFIES: this
    EFFECTS: faces the player upwards, allows vertical movement
     */
    public void faceUp() {
        verticalMovement = true;
        direction = 1;
    }

    /*
    MODIFIES: this
    EFFECTS: faces the player downwards, allows vertical movement
     */
    public void faceDown() {
        verticalMovement = true;
        direction = -1;
    }

    // not used for current user stories
    /*
     * MODIFIES: this
     * EFFECTS: decreases health of player by the damage input
     */
    public void decreaseHealth(int damage) {
        health -= damage;
    }


    // not required for current user stories
    /* MODIFIES: this
     * EFFECTS: stops player if at boundary, for both top/bottom and right/left
     */
    public void handleBoundary() {
        if (posX < 0) { //at the left boundary
            posX = 0; // don't move player anymore
        } else if (posX >= SGame.WIDTH) { // at right boundary
            posX = SGame.WIDTH; // don't move player anymore
        }

        if (posY <= 0) { // at bottom
            posY = 0;
        } else if (posY > SGame.HEIGHT) { //at top
            posY = SGame.HEIGHT;
        }
    }

}
