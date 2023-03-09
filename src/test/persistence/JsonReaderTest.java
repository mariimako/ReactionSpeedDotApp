package persistence;


import model.Bullet;
import model.Enemy;
import model.SGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

// test class for json reader. Credit to JsonSerializationDemo for inspiration
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
        JsonReader reader = new JsonReader("./data/testReaderGeneralGameState.json");
        try {
            SGame gs = reader.read();
            assertEquals(250, gs.getPlayer().getPosX());
            assertEquals(250, gs.getPlayer().getPosY());
            assertEquals(1, gs.getPlayer().getDirection());
            assertTrue(gs.getPlayer().getVerticalMovement());

            checkLists(gs);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    void checkLists(SGame gs) {
        List<Bullet> testBullets = gs.getBullets();
        assertEquals(2, testBullets.size());
        assertEquals(10, testBullets.get(0).getPosX());
        assertEquals(20, testBullets.get(0).getPosY());
        assertFalse(testBullets.get(0).getVerticalMovement());
        assertEquals(-1, testBullets.get(0).getDirection());

        assertEquals(30, testBullets.get(1).getPosX());
        assertEquals(40, testBullets.get(1).getPosY());
        assertTrue(testBullets.get(1).getVerticalMovement());
        assertEquals(1, testBullets.get(1).getDirection());


        List<Enemy> testEnemies = gs.getEnemies();
        assertEquals(2, testEnemies.size());
        assertEquals(1, testEnemies.get(0).getPosX());
        assertEquals(1, testEnemies.get(0).getPosY());
        assertFalse(testBullets.get(0).getVerticalMovement());
        assertEquals(-1, testBullets.get(0).getDirection());

        assertEquals(100, testEnemies.get(1).getPosX());
        assertEquals(100, testEnemies.get(1).getPosY());
        assertTrue(testBullets.get(1).getVerticalMovement());
        assertEquals(1, testBullets.get(1).getDirection());

    }

}
