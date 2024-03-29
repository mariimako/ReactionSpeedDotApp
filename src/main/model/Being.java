package model;

import org.json.JSONObject;
import ui.drawer.GameDraw;

import java.awt.*;


// represents parent type of Enemy, Player and Bullet. Implements duplicate methods like move, collision and getters
// does not currently have abstract methods, but plan to in the future, hence the class type
public abstract class Being {
    protected double posX;  // x position of player
    protected double posY;  // y position of player
    protected boolean verticalMovement; // when true, Being is moving up or down
    protected int direction = 1; // positive 1 represents right or up, negative 1 represents left or down

    protected Color color = new Color(250, 128, 20);


    private int speed = 3; // speed of all subtypes

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
            posY = posY + direction * this.speed;
        } else {
            posX = posX + direction * this.speed;
        }
    }

    /*
    EFFECTS: checks it this has collided with another being
     */
    public boolean collidedWith(Being e) {

        if ((int) e.getPosX() >= (int) posX - GameDraw.ENEMY_SIZE_X / 2
                && (int) e.getPosX() <= (int) posX + GameDraw.ENEMY_SIZE_X  / 2
                && (int) e.getPosY() >= (int) posY - GameDraw.ENEMY_SIZE_Y  / 2
                && (int) e.getPosY() <= (int) posY + GameDraw.ENEMY_SIZE_Y  / 2) {
            return true;
        } else {
            return false;
        }
    }

    /*
    EFFECTS: turns all objects to a json object, storing all relavant information
     */
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("positionX", this.posX);
        obj.put("positionY", this.posY);
        obj.put("direction", this.direction);
        obj.put("verticalMovement", this.verticalMovement);
        obj.put("speed", this.speed);
        return obj;
    }


    public double getPosX() {
        return posX;
    }

    public double getPosY() {
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

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
