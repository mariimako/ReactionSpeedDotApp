package model;



// Represents a user controlled player, with positions and health, extending the Being class

public class Player extends Being {
    protected int health; // remaining health of player

    /*
     * EFFECTS: player is initialized at the initial position of x and y, with 100 health
     */
    public Player() {
        super();
        this.posX = SGame.HEIGHT / 2;
        this.posY = SGame.WIDTH / 2;
        this.health = 100;
    }

    public Player(int initX, int initY) {
        super(initX, initY);
    }

    /*
    EFFECTS: moves player, doesn't move if at boundary
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

    /*
     * MODIFIES: this
     * EFFECTS: decreases health of player by the damage input
     *
     */
    public void decreaseHealth(int damage) {
        health -= damage;
    }


    /* MODIFIES: this
     * EFFECTS: stops player if at boundary
     */
    public void handleBoundary() {
        if (posX < 0) {
            posX = 0;
        } else if (posX >= SGame.WIDTH) {
            posX = SGame.WIDTH;
        }

        if (posY <= 0) {
            posY = 0;
        } else if (posY > SGame.HEIGHT) {
            posY = SGame.HEIGHT;
        }
    }

}
