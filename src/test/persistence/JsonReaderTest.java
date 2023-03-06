package persistence;

import model.Being;
import model.Bullet;
import model.Enemy;
import model.SGame;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            SGame wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            SGame gs = reader.read();
            assertEquals(400, gs.getPlayer().getPosX());
            assertEquals(20, gs.getPlayer().getPosY());
            checkLists(gs);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    void checkLists(SGame gs) {
        List<Bullet> testBullets = gs.getBullets();
        assertEquals(2, testBullets.size());
        assertEquals(400, testBullets.get(0).getPosX());
        assertEquals(20, testBullets.get(0).getPosY());
        assertEquals(50, testBullets.get(1).getPosX());
        assertEquals(50, testBullets.get(1).getPosY());

        List<Enemy> testEnemies = gs.getEnemies();
        assertEquals(2, testEnemies.size());
        assertEquals(10, testEnemies.get(0).getPosX());
        assertEquals(20, testEnemies.get(0).getPosY());
        assertEquals(30, testEnemies.get(1).getPosX());
        assertEquals(50, testEnemies.get(1).getPosY());
    }

}
