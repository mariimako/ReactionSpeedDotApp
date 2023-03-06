package persistence;

import model.Bullet;
import model.Enemy;
import model.SGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            SGame gs = new SGame();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            SGame gs = new SGame();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(gs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            gs = reader.read();
            assertEquals(0, gs.getEnemies().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            SGame gs = new SGame();
            ArrayList<Bullet> testBullets = new ArrayList<Bullet>();
            testBullets.add(new Bullet(10, 20));
            testBullets.add(new Bullet(30, 40));

            ArrayList<Enemy> testEnemies = new ArrayList<Enemy>();
            testEnemies.add(new Enemy(1, 1));
            testEnemies.add(new Enemy(100, 100));

            gs.getBullets().add(testBullets.get(0));
            gs.getBullets().add(testBullets.get(1));

            gs.getEnemies().add(testEnemies.get(0));
            gs.getEnemies().add(testEnemies.get(1));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(gs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            gs = reader.read();
            checkLists(gs, testEnemies, testBullets);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // check the lists of bullets and enemies
    void checkLists(SGame gs, ArrayList<Enemy> testEnemies, ArrayList<Bullet> testBullets) {

        assertEquals(2, gs.getBullets().size());

        assertEquals(testBullets.get(0).getPosX(), gs.getBullets().get(0).getPosX());
        assertEquals(testBullets.get(0).getPosY(), gs.getBullets().get(0).getPosY());

        assertEquals(testBullets.get(1).getPosX(), gs.getBullets().get(1).getPosX());
        assertEquals(testBullets.get(1).getPosY(), gs.getBullets().get(1).getPosY());

        assertEquals(testEnemies.get(0).getPosX(), gs.getEnemies().get(0).getPosX());
        assertEquals(testEnemies.get(0).getPosY(), gs.getEnemies().get(0).getPosY());

        assertEquals(testEnemies.get(1).getPosX(), gs.getEnemies().get(1).getPosX());
        assertEquals(testEnemies.get(1).getPosY(), gs.getEnemies().get(1).getPosY());
    }

}