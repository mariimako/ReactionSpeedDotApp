package model;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

// represents game state. updates game to change player, bullet position and spawns enemies sometimes
public class SGame implements Writable {

    private ArrayList<Enemy> enemies; // a list of enemies present in the gamestate that has spawned
    private ArrayList<Bullet> bullets; // a list of bullets that exists in the gamestate
    private Player player; // represents the player

    public static final int HEIGHT = 500; // height of gamestate
    public static final int WIDTH = 500; // width of gamestate

    private static final int COLLISION_DAMAGE = 20; // NOT USED YET collision damage when a player hits an enemy
    private static final int APPEARANCE_RATE = 150; // appearance rate of enemies

    private int counter; //NOT USED YET a counter to keep track when to spawn enemies

    private Boolean playing; //represents if playing or not

    /*
    EFFECTS: instantiates a game, with no enemies and no bullets, with a new player in the middle
     */
    public SGame() {
        this.enemies = new ArrayList<Enemy>();
        this.bullets = new ArrayList<Bullet>();
        player = new Player();
        counter = 0;
        playing = true;
    }

    /*
    MODIFIES: this
    EFFECTS: spawns enemy in specified location, does not require x and y to be within game state as it would adjust
     */
    public void spawnEnemy(int x, int y) {
        enemies.add(new Enemy(x, y));
    }


    /*
    MODIFIES: this
    EFFECTS: checks every bullet that exists in gamestate and removes it if it has collided with
    an enemy or the walls. It should remove only one bullet as a bullet cannot delete
    multiple enemies in one call.
     */
    protected void checkBullets() {
        outer:
        for (Bullet b: bullets) {
            if (b.getPosX() <= 0 | b.getPosX() >= WIDTH | b.getPosY() >= HEIGHT | b.getPosY() <= 0) {
                bullets.remove(b); // remove if at boundary
                break; // once boundary is removed, exit loop, only one bullet should be removed per call
            }
            for (Enemy e: enemies) {
                if (b.collidedWith(e)) {
                    bullets.remove(b);
                    enemies.remove(e);
                    break outer; //once collided enemy found, exit loop, as only one should be removed
                }
            }
        }
    }

    /*
    MODIFIES: this
    EFFECTS: create a new bullet from player position that is travelling in direction accordingly
             to the player.
     */
    public void fireBullet() {
        Bullet b = new Bullet(player);
        bullets.add(b);
    }


    // EFFECTS: convert whole gamestate object to json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray enemyArray = new JSONArray();
        JSONArray bulletArray = new JSONArray();
        JSONArray beingArray = new JSONArray();

        beingArray.put(player.toJson());

        // Add each enemy to the JSON array
        for (Enemy enemy : enemies) {
            JSONObject enemyJson = enemy.toJson();
            enemyArray.put(enemyJson);
        }

        // Add each bullet to the JSON array
        for (Bullet bullet : bullets) {
            JSONObject bulletJson = bullet.toJson();
            bulletArray.put(bulletJson);
        }

        // Add player, enemies, and bullets to the main JSON object
        json.put("player", beingArray);
        json.put("enemies", enemyArray);
        json.put("bullets", bulletArray);

        return json;
    }


    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    public Boolean isPlaying() {
        return playing;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void stopPlaying() {
        this.playing = false;
    }





    // THE FOLLOWING METHODS ARE NOT USED TO SATIDY USER STORIES,
    // BUT WILL BE USED LATER IN THE PROJECT AS GRAPHICAL INTERFACE IS
    // IMPLEMENTED
    // IGNORE ALL IMPLEMENTATIONS AS THEY DO NOT CONTRIBUTE TO CURRENT USER STORIES



    /*
    MODIFIES: this
    EFFECTS: game stops if player health is 0
    */
    protected void checkGameOver() {
        if (player.health <= 0) {
            playing = false;
        }
    }

    /*
    MODIFIES: this
    EFFECTS: moves all bullets in their predetermined ways
    */
    protected void moveBullets() {
        for (Bullet b : bullets) {
            b.move();
        }
    }

    /*
    MODIFIES: this
    EFFECTS: if player is collided with enemy, damage the player
    NOT USED, FOR FUTURE IMPLEMENTATION
    */

//    private void checkPlayer() {
//        for (Enemy e: enemies) {
//            if (player.collidedWith(e)) {
//                player.decreaseHealth(COLLISION_DAMAGE);
//                enemies.remove(e);
//                break;
//            }
//        }
//    }

    /*
    MODIFIES: this
    EFFECTS: calls all methods related to object collisions
    NOT USED, FOR FUTURE IMPLEMENTATION
    */

//    private void checkCollisions() {
//        checkBullets();
//        checkPlayer();
//    }

    /*
    MODIFIES: this
    EFFECTS: updates gamestate to move all objects and sometimes spawn enemies

    NOT USED, FUTURE IMPLEMENTATION
     */

//    public void update() {
//        player.move();
//        moveBullets();
//        checkCollisions();
//        checkGameOver();
//        counter++;
//        //if (counter % APPEARANCE_RATE == 0) { NOT USED
//            //spawnEnemy();
//        //}
//
//    }

}
