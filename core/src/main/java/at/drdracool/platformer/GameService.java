package at.drdracool.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameService {
    HashMap<String, GameCharacter> connectionCharacterMap;
    List<Block> blockList;
    List<Block> movingBlockList;
    String connectionId;


    public GameService() {
        connectionCharacterMap = new HashMap<>();
        blockList = new ArrayList<>();
        movingBlockList = new ArrayList<>();
    }

    public void drawBlocks(ShapeRenderer shape, OrthographicCamera camera) {
        for (var character : connectionCharacterMap.values()) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(new Color(0.42f, 0.52f, 1.19f, 0.6f));
            shape.rect(character.getLocationX(), character.getLocationY(), character.getWidth(), character.getHeight());
            shape.end();
        }
        for (var block : blockList) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(new Color(0.52f, 1.52f, 2.19f, 1));
            shape.rect(block.getLocationX(), block.getLocationY(), block.getWidth(), block.getHeight());
            shape.end();
        }
        for (var block : movingBlockList) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(new Color(0.32f, 1.32f, 1.19f, 1));
            shape.rect(block.getLocationX(), block.getLocationY(), block.getWidth(), block.getHeight());
            shape.end();
        }
    }

    public void updateCharacterLocations(List<GameCharacter> gameCharacters) {
        for (var character : gameCharacters) {
            var connectionId = character.getConnectionId();
            var alreadyExistCharacter = connectionCharacterMap.get(connectionId);
            if (alreadyExistCharacter != null) {
                System.out.println("passed in location: " + character.getLocationX());
                alreadyExistCharacter.setLocationX(character.getLocationX());
                alreadyExistCharacter.setLocationY(character.getLocationY());
            } else {
                System.out.println("creating new character: " + connectionId);
                connectionCharacterMap.put(connectionId, character);
            }
        }
    }

    public void handleIncomingMessage(String message) {
        String[] fullMessage = message.split("\\|");
        Json json = new Json();
        switch (fullMessage[0]) {
            case("OnConnectionOpen"):
                Gdx.app.log("Nework-MainThread", "Received new connectionId from socket server: " + message);
                this.connectionId = fullMessage[1];
                GameCharacter ownCharacter = json.fromJson(GameCharacter.class, fullMessage[2]);
                connectionCharacterMap.put(connectionId, ownCharacter);
                break;
            case("OnConnectionClose"):
                Gdx.app.log("Nework-MainThread", "Received connection close request from socket server: " + message);
                String connectionId = fullMessage[1];
                connectionCharacterMap.remove(connectionId);
                break;
            case("UpdateAllCharacterLocations"):
                GameCharacter[] characters = json.fromJson(GameCharacter[].class, fullMessage[1]);
                updateCharacterLocations(List.of(characters));
                break;
            case("SendAllBlocks"):
                Block[] blocks = json.fromJson(Block[].class, fullMessage[1]);
                blockList = List.of(blocks);
            case("UpdateAllMovingBlockLocations"):
                Block[] movingBlocks = json.fromJson(Block[].class, fullMessage[1]);
                movingBlockList = List.of(movingBlocks);
        }
    }
}
