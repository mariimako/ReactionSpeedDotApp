package model;

// represents parent type of Enemy, Player and Bullet. Implements similar methods like move, collision and gets
public abstract class Being {
    protected int posX;  // x position of player
    protected int posY;  // y position of player
    protected boolean verticalMovement; // when true, Being is moving up or down
    protected int direction; // positive 1 represents right or up, negative 1 represents left or down

    public static final int SPEED = 3;

    //

    public Being() {
        this.direction = 1;
        this.verticalMovement = false;
    }

    // overloading, another instantiation to specifify spawn location
    public Being(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /*
    MODIFIES: this
    EFFECTS: moves itself according to its direction and speed
     */
    public void move() {
        if (verticalMovement) {
            posY = posY + direction * SPEED;
        } else {
            posX = posX + direction * SPEED;
        }
    }

    /*
    EFFECTS: checks it this has collided with another being
     */
    public boolean collidedWith(Being e) {
        if (e.getPosX() == posX && e.getPosY() == posY) {
            return true;
        } else {
            return false;
        }
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean getVerticalMovement() {
        return verticalMovement;
    }

    public int getDirection() {
        return direction;
    }


}
