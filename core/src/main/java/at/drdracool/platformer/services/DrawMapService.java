package at.drdracool.platformer.services;

import at.drdracool.platformer.models.BlockDTO;
import at.drdracool.platformer.models.CircleDTO;
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
        CircleDTO[] characters = json.fromJson(CircleDTO[].class, message);
        for (var character : characters) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(Color.valueOf("54786A"));
            shape.circle(character.getLocation().x, character.getLocation().y, character.getRadius());
            shape.end();
        }
    }

    public void drawMovingBlocks(String message) {
        if (message.isEmpty()) return;
        BlockDTO[] movingBlocks = json.fromJson(BlockDTO[].class, message);
        for (var block : movingBlocks) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(Color.valueOf("AA5725"));
            shape.rect(block.getLocation().x, block.getLocation().y, block.getSize().x, block.getSize().y);
            shape.end();
        }
    }

    public void drawStaticBlocks(String message) {
        if (message.isEmpty()) return;
        BlockDTO[] staticBlocks = json.fromJson(BlockDTO[].class, message);
        for (var block : staticBlocks) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            Color color = Color.valueOf("624B1C");

            shape.rect(block.getLocation().x, block.getLocation().y, 0, 0, block.getSize().x, block.getSize().y, 1, 1, block.getDegree(), color, color, color, color);
            shape.end();
        }
    }

    public void drawDoors(String message) {
        if (message.isEmpty()) return;
        BlockDTO[] staticBlocks = json.fromJson(BlockDTO[].class, message);
        for (var block : staticBlocks) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            Color color = Color.valueOf("EFCF83");
            shape.rect(block.getLocation().x, block.getLocation().y, 0, 0, block.getSize().x, block.getSize().y, 1, 1, block.getDegree(), color, color, color, color);
            shape.end();
        }
    }

    public void drawKeys(String message) {
        if (message.isEmpty()) return;
        CircleDTO[] keys = json.fromJson(CircleDTO[].class, message);
        for (var key : keys) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(Color.valueOf("DED18D"));
            shape.circle(key.getLocation().x, key.getLocation().y, key.getRadius());
            shape.end();
        }
    }


}
