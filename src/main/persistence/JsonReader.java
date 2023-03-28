package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads gamestate from JSON data stored in file taken from JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameState(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private SGame parseGameState(JSONObject jsonObject) {
        SGame gs = new SGame();
        getObjects(gs, jsonObject, "enemies");
        getObjects(gs, jsonObject, "bullets");
        getObjects(gs, jsonObject, "player");
        return gs;
    }

    // MODIFIES: gs
    // EFFECTS: parses beings from JSON object and adds them to gamestate
    public void getObjects(SGame gs, JSONObject jsonObject, String beingType) {
        JSONArray jsonArray = jsonObject.getJSONArray(beingType);
        for (Object json : jsonArray) {
            JSONObject nextBeing = (JSONObject) json;
            addBeing(gs, nextBeing, beingType);
        }
    }

    // MODIFIES: gs
    // EFFECTS: parses being from JSON object and adds it to gamestate, adds to appropiate objects depedning on type
    private void addBeing(SGame gs, JSONObject jsonObject, String beingType) {
        int posX = jsonObject.getInt("positionX");
        int posY = jsonObject.getInt("positionY");
        Boolean verticalMovement = jsonObject.getBoolean("verticalMovement");
        int direction = jsonObject.getInt("direction");
        int speed = jsonObject.getInt("speed");
        addToGame(gs,beingType,direction, verticalMovement,posX,posY,speed);
    }

    private void addToGame(SGame gs, String beingType, int direction, Boolean verticalMovement,
                           int posX, int posY, int speed) {
        if (beingType == "enemies") {
            Enemy e = new Enemy(posX, posY);
            e.setVerticalMovement(verticalMovement);
            e.setDirection(direction);
            e.setSpeed(speed);
            gs.getEnemies().add(e);
            gs.getBeings().add(e);
        } else if (beingType == "bullets") {
            Bullet b = new Bullet(posX, posY);
            b.setVerticalMovement(verticalMovement);
            b.setDirection(direction);
            b.setSpeed(speed);
            gs.getBullets().add(b);
            gs.getBeings().add(b);
        } else {
            Player p = new Player(posX, posY);
            p.setVerticalMovement(verticalMovement);
            p.setSpeed(speed);
            p.setDirection(direction);
            gs.setPlayer(p);
        }
    }
}
