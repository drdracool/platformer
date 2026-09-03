package at.drdracool.platformer.services;

import at.drdracool.platformer.models.MovingBlock;
import at.drdracool.platformer.models.GameCharacter;
import at.drdracool.platformer.models.StaticBlock;
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
            shape.circle(character.getLocationX(), character.getLocationY(), character.getRadius());
            shape.end();
        }
    }

    public void drawMovingBlocks(String message) {
        if (message.isEmpty()) return;
        MovingBlock[] movingBlocks = json.fromJson(MovingBlock[].class, message);
        for (var block : movingBlocks) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(new Color(0.32f, 1.32f, 1.19f, 1));
            shape.rect(block.getLocation().x, block.getLocation().y, block.getWidth(), block.getHeight());
            shape.end();
        }
    }

    public void drawStaticBlocks(String message) {
        if (message.isEmpty()) return;
        StaticBlock[] staticBlocks = json.fromJson(StaticBlock[].class, message);
        for (var block : staticBlocks) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            Color color = new Color(0.52f, 1.52f, 2.19f, 1);
            shape.rect(block.getLocation().x, block.getLocation().y, 0, 0, block.getWidth(), block.getHeight(), 1, 1, block.getDegree(), color, color, color, color);
            shape.end();
        }
    }
}
