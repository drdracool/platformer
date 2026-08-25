package at.drdracool.platformer.services;

import at.drdracool.platformer.models.Block;
import at.drdracool.platformer.models.GameCharacter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class DrawMapService {
    ShapeRenderer shape;

    public DrawMapService(ShapeRenderer shape) {
        this.shape = shape;
    }
    public void drawCharacters(String message) {
        if (message.isEmpty()) return;
        GameCharacter[] characters = json.fromJson(GameCharacter[].class, message);
        for (var character : characters) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(new Color(0.42f, 0.52f, 1.19f, 0.6f));
            shape.rect(character.getLocationX(), character.getLocationY(), character.getWidth(), character.getHeight());
            shape.end();
        }
    }

    public void drawBlocks(String message) {
        if (message.isEmpty()) return;
        Block[] blocks = json.fromJson(Block[].class, message);
        for (var block : blocks) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            if (block.isMoving()) {
                shape.setColor(new Color(0.32f, 1.32f, 1.19f, 1));
            } else {
                shape.setColor(new Color(0.52f, 1.52f, 2.19f, 1));
            }
            shape.rect(block.getLocation().x, block.getLocation().y, block.getWidth(), block.getHeight());
            shape.end();
        }
    }
}
