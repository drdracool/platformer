package at.drdracool.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GameService {
    HashMap<String, Sprite> connectionSpriteMap;
    HashMap<String, Texture> texturePathMap;
    Texture knightTexture;
    String connectionId;
    SocketClient socketClient;

    public GameService(SocketClient socketClient) {
        knightTexture = new Texture("knight.png");
        connectionSpriteMap = new HashMap<>();
        texturePathMap = new HashMap<>();
        texturePathMap.put("knight.png", knightTexture);
        this.socketClient = socketClient;
    }

    public void createInitialSprite() {
        Sprite sprite = new Sprite(knightTexture);
        sprite.setSize(1, 1);
        connectionSpriteMap.put(connectionId, sprite);
    }

    public void handleMoveLogic(FitViewport viewport) {
        float worldWidth = viewport.getWorldWidth();

        for (var sprite : connectionSpriteMap.values()) {
            float spriteWidth = sprite.getWidth();
            sprite.setX(MathUtils.clamp(sprite.getX(), 0, worldWidth - spriteWidth));
        }
    }

    public void drawSprites(SpriteBatch spriteBatch) {
        System.out.println("the number of sprites: " + connectionSpriteMap.values().size());
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

    public void updateSpriteLocations(List<GameCharacter> gameCharacters) {
        for (var character : gameCharacters) {
            var connectionId = character.getConnectionId();
            var alreadyExistSprite = connectionSpriteMap.get(connectionId);
            if (alreadyExistSprite != null) {
                System.out.println("this character already exist: " + connectionId);
                if (alreadyExistSprite.getX() != character.getLocationX()) {
                    alreadyExistSprite.setX(character.getLocationX());
                } else if (alreadyExistSprite.getY() != character.getLocationY()) {
                    alreadyExistSprite.setY(character.getLocationY());
                }
            } else {
                System.out.println("creating new character: " + connectionId);
                Texture texture = texturePathMap.get(character.getAssetName());
                Sprite sprite = new Sprite(texture);
                sprite.setSize(1, 1);
                sprite.setPosition(character.getLocationX(), character.getLocationY());
                connectionSpriteMap.put(connectionId, sprite);
            }
        }
    }

    public void handleIncomingMessage(String message) {
        String[] fullMessage = message.split("\\|");
        switch (fullMessage[0]) {
            case("OnConnection"):
                Gdx.app.log("Nework-MainThread", "Received connectionId from socket server: " + message);
                this.connectionId = fullMessage[1];
                createInitialSprite();
                break;
            case("UpdateAllLocations"):
                Json json = new Json();
                GameCharacter[] characters = json.fromJson(GameCharacter[].class, fullMessage[1]);
                updateSpriteLocations(List.of(characters));
                break;
        }
    }
}
