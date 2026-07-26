package at.drdracool.platformer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class GameService {
    SocketSendClient socketSendClient;
    HashMap<String, GameCharacter> connectionCharacterMap;
    List<Block> blockList;
    List<Block> movingBlockList;

    public GameService(SocketSendClient socketSendClient) {
        this.socketSendClient = socketSendClient;
        connectionCharacterMap = new HashMap<>();
        blockList = new ArrayList<>();
        movingBlockList = new ArrayList<>();
    }

    public void drawCharactersAndBlocks(ShapeRenderer shape) {
        System.out.println("drawing moving blocks. size: " + movingBlockList.get(0).getWidth() + " " + movingBlockList.get(0).getHeight());
        for (var character : connectionCharacterMap.values()) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(new Color(0.42f, 0.52f, 1.19f, 0.6f));
            shape.rect(character.getLocationX(), character.getLocationY(), character.getWidth(), character.getHeight());
            shape.end();
        }
        for (var block : blockList) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(new Color(0.52f, 1.52f, 2.19f, 1));
            shape.rect(block.getLocation().x, block.getLocation().y, block.getWidth(), block.getHeight());
            shape.end();
        }
        for (var block : movingBlockList) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(new Color(0.32f, 1.32f, 1.19f, 1));
            shape.rect(block.getLocation().x, block.getLocation().y, block.getWidth(), block.getHeight());
            shape.end();
        }
    }

    public void updateCharacterLocations(String message) {
        GameCharacter[] characters = json.fromJson(GameCharacter[].class, message);
        List<GameCharacter> gameCharacters = List.of(characters);
        for (var character : gameCharacters) {
            var connectionId = character.getConnectionId();
            var alreadyExistCharacter = connectionCharacterMap.get(connectionId);
            if (alreadyExistCharacter != null) {
//                System.out.println("passed in location: " + character.getLocationX());
                alreadyExistCharacter.setLocationX(character.getLocationX());
                alreadyExistCharacter.setLocationY(character.getLocationY());
            } else {
                System.out.println("creating new character: " + connectionId);
                connectionCharacterMap.put(connectionId, character);
            }
        }
    }

    public void updateAllMovingBlockLocations(String message) {
        Block[] movingBlocks = json.fromJson(Block[].class, message);
        movingBlockList = List.of(movingBlocks);
    }

    public void createOwnCharacter(String connectionId, String characterMessage) {
        GameCharacter ownCharacter = json.fromJson(GameCharacter.class, characterMessage);
        connectionCharacterMap.put(connectionId, ownCharacter);
    }

    public void initiateMap(String message) {
        Block[] blocks = json.fromJson(Block[].class, message);
        blockList = List.of(blocks);
    }

    public void removeDisconnectedConnection(String connectionId) {
        connectionCharacterMap.remove(connectionId);
    }
}
