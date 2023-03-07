package model;

import org.json.JSONArray;
import org.json.JSONObject;

// represents parent type of Enemy, Player and Bullet. Implements duplicate methods like move, collision and getters
// does not currently have abstract methods, but plan to in the future, hence the class type
public abstract class Being {
    protected int posX;  // x position of player
    protected int posY;  // y position of player
    protected boolean verticalMovement; // when true, Being is moving up or down
    protected int direction = 1; // positive 1 represents right or up, negative 1 represents left or down

    public static final int SPEED = 3; // speed of all subtypes



    /*
    EFFECTS: instantiates a being in a game that is facing right
     */
    public Being() {
        this.verticalMovement = false;
    }

    /*
    MODIFIES: this
    EFFECTS: instantiates a being in a game with specified position, does not require coordinates to be within game
    as it will be updated
     */
    public Being(int posX, int posY) {
        this.posX = posX;     // overloading, another instantiation to specify spawn location
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

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("positionX", this.posX);
        obj.put("positionY", this.posY);
        obj.put("direction", this.direction);
        obj.put("verticalMovement", this.verticalMovement);
        return obj;
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

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setVerticalMovement(boolean verticalMovement) {
        this.verticalMovement = verticalMovement;
    }
}
