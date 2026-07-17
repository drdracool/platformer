package at.drdracool.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameService {
    HashMap<String, Sprite> connectionSpriteMap;
    List<Sprite> blockSpriteList;
    HashMap<String, Texture> texturePathMap;
    Texture knightTexture;
    Texture wallTexture;
    String connectionId;


    public GameService() {
        knightTexture = new Texture("knight.png");
        wallTexture = new Texture("wall.png");
        connectionSpriteMap = new HashMap<>();
        texturePathMap = new HashMap<>();
        texturePathMap.put("knight.png", knightTexture);
        texturePathMap.put("wall.png", wallTexture);
        blockSpriteList = new ArrayList<>();
    }

    public void createInitialSprite() {
        Sprite sprite = new Sprite(knightTexture);
        sprite.setSize(1, 1);
        connectionSpriteMap.put(connectionId, sprite);
    }

    public void createBlockSprite(Block block) {
        Sprite sprite = new Sprite(wallTexture, block.getWidth(), block.getHeight());
        sprite.setPosition(block.getLocationX(), block.getLocationY());
        blockSpriteList.add(sprite);
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
        for (var sprite : blockSpriteList) {
            sprite.draw(spriteBatch);
        }
    }

    public void updateSpriteLocations(List<GameCharacter> gameCharacters) {
        for (var character : gameCharacters) {
            var connectionId = character.getConnectionId();
            var alreadyExistSprite = connectionSpriteMap.get(connectionId);
            if (alreadyExistSprite != null) {
                if (alreadyExistSprite.getX() != character.getLocationX()) {
                    alreadyExistSprite.setX(character.getLocationX());
                }
                if (alreadyExistSprite.getY() != character.getLocationY()) {
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
        Json json = new Json();
        switch (fullMessage[0]) {
            case("OnConnectionOpen"):
                Gdx.app.log("Nework-MainThread", "Received new connectionId from socket server: " + message);
                this.connectionId = fullMessage[1];
                createInitialSprite();
                break;
            case("OnConnectionClose"):
                Gdx.app.log("Nework-MainThread", "Received connection close request from socket server: " + message);
                String connectionId = fullMessage[1];
                connectionSpriteMap.remove(connectionId);
                break;
            case("UpdateAllLocations"):
                GameCharacter[] characters = json.fromJson(GameCharacter[].class, fullMessage[1]);
                updateSpriteLocations(List.of(characters));
                break;
            case("SendAllBlocks"):
                Block[] blocks = json.fromJson(Block[].class, fullMessage[1]);
                for (var block : blocks) {
                    createBlockSprite(block);
                }
        }
    }
}
