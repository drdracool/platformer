package at.drdracool.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameService {
    HashMap<String, Character> characterHashMap;
    HashMap<String, Sprite> connectionSpriteMap;
    HashMap<String, Texture> texturePathMap;
    Texture knightTexture;
    String connectionId;
    SocketClient socketClient;

    public GameService(HashMap<String, Character> characterHashMap, String connectionId) {
        this.characterHashMap = characterHashMap;
        knightTexture = new Texture("knight.png");
        connectionSpriteMap = new HashMap<>();
        texturePathMap = new HashMap<>();
        texturePathMap.put("knight.png", knightTexture);
        this.connectionId = connectionId;
    }

    public void setSocketClient(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    public void createInitialSprite() {
        Sprite sprite = new Sprite(knightTexture);
        sprite.setSize(1, 1);
        connectionSpriteMap.put(connectionId, sprite);
        characterHashMap.put(connectionId, new Character(connectionId, 0f, 0f, "knight.png"));
    }

    public void updateCurrentConnectionId(String connectionId) {
        this.connectionId = connectionId;
        characterHashMap.put(connectionId, characterHashMap.get(""));
        characterHashMap.remove("TEMP");
    }

    public void handleMoveLogic(FitViewport viewport) {
        float worldWidth = viewport.getWorldWidth();

        for (var sprite : connectionSpriteMap.values()) {
            float spriteWidth = sprite.getWidth();
            sprite.setX(MathUtils.clamp(sprite.getX(), 0, worldWidth - spriteWidth));
        }
    }

    public void drawSprites(SpriteBatch spriteBatch) {
        for (var sprite : connectionSpriteMap.values()) {
            sprite.draw(spriteBatch);
        }
    }

    public void handleMoveInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            notifyServerMovement("RIGHT");
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            notifyServerMovement("LEFT");
        }
    }

    private void notifyServerMovement(String command) {
        try {
            socketClient.sendMessage(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCharacterLocations(List<Character> characters) {
        for (var character : characters) {
            var alreadyExist = characterHashMap.putIfAbsent(character.connectionId, character);
        }
    }
}
