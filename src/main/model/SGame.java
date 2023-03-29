package model;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


// represents game state. updates game to change player, bullet position and spawns enemies sometimes
public class SGame implements Writable {

    protected ArrayList<Enemy> enemies; // a list of enemies present in the gamestate that has spawned
    protected ArrayList<Bullet> bullets; // a list of bullets that exists in the gamestate
    protected ArrayList<Being> beings;

    private Player player; // represents the player

    protected static final int SIZE_X = 45;
    protected static final int SIZE_Y = 24;
    protected Color color = new Color(250, 128, 20);

    public static final int HEIGHT = 500; // height of gamestate
    public static final int WIDTH = 500; // width of gamestate

    private static final int COLLISION_DAMAGE = 20; // NOT USED YET collision damage when a player hits an enemy
    protected int apperancerate = 50; // appearance rate of enemies

    protected static final int SPEED_UP_RATE = 200;
    protected static final int SPAWN_RATE = 150;


    protected int counter; // a counter to keep track when to spawn enemies

    private Boolean playing; //represents if playing or not


    /*
    EFFECTS: instantiates a game, with no enemies and no bullets, with a new player in the middle
     */
    public SGame() {
        this.enemies = new ArrayList<Enemy>();
        this.bullets = new ArrayList<Bullet>();
        this.beings = new ArrayList<Being>();
        player = new Player();
        beings.addAll(enemies);
        beings.addAll(bullets);
        beings.add(player);
        playing = true;
    }

    /*
    MODIFIES: this
    EFFECTS: updates gamestate to move all objects and sometimes spawn enemies, speed up beings
    and increase spawn rate
     */

    public void update() {
        if (playing) {
            player.move();
            moveBullets();
            checkCollisions();
            checkGameOver();
            for (Enemy e : enemies) {
                e.enemyUpdate(player);
            }
            if (counter % SPAWN_RATE == apperancerate) {
                spawnEnemy();
                apperancerate -= 1;
            }
            if (counter % SPEED_UP_RATE == 0) {
                speedUp();
            }
            counter++;
        }
    }

    /*
    EFFECTS: speeds up game and bullets, has minimal effect on enemies
     */
    public void speedUp() {
        for (Being b : beings) {
            int newSpeed = b.getSpeed() + 1;
            b.setSpeed(newSpeed);
        }
    }

    /*
    MODIFIES: this
    EFFECTS: spawns enemy in specified location, does not require x and y to be within game state as it would adjust
     */
    public void spawnEnemy() {
        Random x = new Random();
        Random y = new Random();

        int randomX = x.nextInt(WIDTH);
        int randomY = y.nextInt(HEIGHT);
        Enemy e = new Enemy(randomX, randomY);
        enemies.add(e);
        beings.add(e);
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
                beings.remove(b); // remove if at boundary
                b.setColor(Color.GRAY);
                break; // once boundary is removed, exit loop, only one bullet should be removed per call
            }
            for (Enemy e: enemies) {
                if (b.collidedWith(e)) {
                    bullets.remove(b);
                    enemies.remove(e);
                    beings.remove(b);
                    beings.remove(e);
                    e.setColor(Color.GRAY);
                    b.setColor(Color.GRAY);
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
        beings.add(b);
    }


    // EFFECTS: convert whole gamestate object to json object, taken influence from JsonSerializationDemo
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
    public void moveBullets() {
        for (Bullet b : bullets) {
            b.move();
        }
    }

    /*
    MODIFIES: this
    EFFECTS: if player is collided with enemy, damage the player, only one enemy can collide at once
    */

    protected void checkPlayer() {
        for (Enemy e: enemies) {
            if (player.collidedWith(e)) {
                player.decreaseHealth(COLLISION_DAMAGE);
                enemies.remove(e);
                beings.remove(e);
                break;
            }
        }
    }

    /*
    MODIFIES: this
    EFFECTS: calls all methods related to object collisions
    */
    private void checkCollisions() {
        checkBullets();
        checkPlayer();
    }

    public void setCounter(int counter) {
        this.counter = counter;
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

    public ArrayList<Being> getBeings() {
        return beings;
    }


    public void stopPlaying() {
        this.playing = false;
    }

}
