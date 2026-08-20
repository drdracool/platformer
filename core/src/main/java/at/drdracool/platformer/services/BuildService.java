package at.drdracool.platformer.services;

import at.drdracool.platformer.models.Block;
import at.drdracool.platformer.models.GameCharacter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class BuildService {
    GameCharacter character = new GameCharacter();
    List<Block> allBlockList = new ArrayList<>();

    public void createNewCharacter(String message) {
        character = json.fromJson(GameCharacter.class, message);
    }

    public void drawCharacter(ShapeRenderer shape) {
        //updateLocation();
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0.42f, 0.52f, 1.19f, 0.6f));
        shape.rect(character.getLocationX(), character.getLocationY(), character.getWidth(), character.getHeight());
        shape.end();
    }

    public void drawCharactersAndBlocks(ShapeRenderer shape) {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0.42f, 0.52f, 1.19f, 0.6f));
        shape.rect(character.getLocationX(), character.getLocationY(), character.getWidth(), character.getHeight());
        shape.end();
        for (var block : allBlockList) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(new Color(0.52f, 1.52f, 2.19f, 1));
            shape.rect(block.getLocation().x, block.getLocation().y, block.getWidth(), block.getHeight());
            shape.end();
        }
    }

    public void updateCharacterLocations(String message) {
        GameCharacter[] characters = json.fromJson(GameCharacter[].class, message);
        GameCharacter updatedCharacter = List.of(characters).get(0);
        character.setLocationX(updatedCharacter.getLocationX());
        character.setLocationY(updatedCharacter.getLocationY());
    }

    public void updateAllBlockLocations(String message) {
        Block[] allBlocks = json.fromJson(Block[].class, message);
        allBlockList = List.of(allBlocks);
    }
}
