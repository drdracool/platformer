package at.drdracool.platformer.services;

import at.drdracool.platformer.models.BlockDTO;
import at.drdracool.platformer.models.CircleDTO;
import at.drdracool.platformer.models.MapContent;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class DrawMapService {
    ShapeRenderer shape;

    public DrawMapService(ShapeRenderer shape) {
        this.shape = shape;
    }

    public void drawMapContent(String message) {
        if (message.isEmpty()) return;
        MapContent contents = json.fromJson(MapContent.class, message);

        shape.begin(ShapeRenderer.ShapeType.Filled);

        CircleDTO[] characters = json.fromJson(CircleDTO[].class, contents.getCharacterLocations());
        for (var character : characters) {
            shape.setColor(Color.valueOf("54786A"));
            shape.circle(character.getLocation().x, character.getLocation().y, character.getRadius());
        }

        BlockDTO[] movingBlocks = json.fromJson(BlockDTO[].class, contents.getMovingBlockLocations());
        for (var block : movingBlocks) {
            shape.setColor(Color.valueOf("AA5725"));
            shape.rect(block.getLocation().x, block.getLocation().y, block.getSize().x, block.getSize().y);
        }

        BlockDTO[] staticBlocks = json.fromJson(BlockDTO[].class, contents.getStaticBlockLocations());
        for (var block : staticBlocks) {
            Color color = Color.valueOf("624B1C");
            shape.rect(block.getLocation().x, block.getLocation().y, 0, 0, block.getSize().x, block.getSize().y, 1, 1, block.getDegree(), color, color, color, color);
        }

        BlockDTO[] doors = json.fromJson(BlockDTO[].class, contents.getDoorLocations());
        for (var block : doors) {
            Color color = Color.valueOf("EFCF83");
            shape.rect(block.getLocation().x, block.getLocation().y, 0, 0, block.getSize().x, block.getSize().y, 1, 1, block.getDegree(), color, color, color, color);
        }

        CircleDTO[] keys = json.fromJson(CircleDTO[].class, contents.getKeyLocations());
        for (var key : keys) {
            shape.setColor(Color.valueOf("DED18D"));
            shape.circle(key.getLocation().x, key.getLocation().y, key.getRadius());
        }

        shape.end();
    }
}
