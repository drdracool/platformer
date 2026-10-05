package at.drdracool.platformer.services;

import at.drdracool.platformer.models.BlockDTO;
import at.drdracool.platformer.models.CircleDTO;
import at.drdracool.platformer.models.MapContent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class DrawMapService {
    ShapeRenderer shape;
    Color blue = new Color(0.21176471f, 0.5254902f, 1, 1);
    Color darkBlue = new Color(0.035f, 0.282f, 0.922f, 1);
    Color orange = new Color(1, 0.6f, 0.204f, 1);
    Color yellowGreen = new Color(0.659f, 0.616f, 0.204f, 1);
    Color yellow = new Color(0.945f, 0.769f, 0.067f, 1);
    Color purple = new Color(0.58f, 0.627f, 1, 1);
    Color beige = new Color(0.922f, 0.906f, 0.757f, 1);

    float SCALE = 2.2f;
    float OFFSETX = 5.3f;
    float OFFSETY = 4;

    int col_width = Gdx.graphics.getWidth() / 12;
    int row_height = Gdx.graphics.getHeight() / 12;

    public DrawMapService(ShapeRenderer shape) {
        this.shape = shape;
    }

    public void drawMapContent(MapContent contents) {
        shape.begin(ShapeRenderer.ShapeType.Filled);

        BlockDTO[] movingBlocks = json.fromJson(BlockDTO[].class, contents.getMovingBlockLocations());
        for (var block : movingBlocks) {
            shape.setColor(purple);
            shape.rect(block.getLocation().x, block.getLocation().y, block.getSize().x, block.getSize().y);
        }

        BlockDTO[] staticBlocks = json.fromJson(BlockDTO[].class, contents.getStaticBlockLocations());
        for (var block : staticBlocks) {
            shape.rect(block.getLocation().x, block.getLocation().y, 0, 0, block.getSize().x, block.getSize().y, 1, 1, block.getDegree(), blue, blue, blue, blue);
        }

        BlockDTO[] doors = json.fromJson(BlockDTO[].class, contents.getDoorLocations());
        for (var block : doors) {
            shape.rect(block.getLocation().x, block.getLocation().y, 0, 0, block.getSize().x, block.getSize().y, 1, 1, block.getDegree(), yellow, yellow, yellow, yellow);
        }

        CircleDTO[] keys = json.fromJson(CircleDTO[].class, contents.getKeyLocations());
        for (var key : keys) {
            shape.setColor(yellow);
            shape.circle(key.getLocation().x, key.getLocation().y, key.getRadius());
        }

        CircleDTO exit = json.fromJson(CircleDTO.class, contents.getExitLocation());
        shape.setColor(beige);
        shape.circle(exit.getLocation().x, exit.getLocation().y, exit.getRadius());

        CircleDTO[] characters = json.fromJson(CircleDTO[].class, contents.getCharacterLocations());
        for (var character : characters) {
            shape.setColor(yellowGreen);
            shape.circle(character.getLocation().x, character.getLocation().y, character.getRadius());
        }
        shape.end();
    }

    public void drawMiniMap(MapContent contents) {
        shape.begin(ShapeRenderer.ShapeType.Filled);

        BlockDTO[] movingBlocks = json.fromJson(BlockDTO[].class, contents.getMovingBlockLocations());
        for (var block : movingBlocks) {
            shape.rect(block.getLocation().x / SCALE + col_width  * OFFSETX, block.getLocation().y / SCALE + row_height * OFFSETY, 0, 0, block.getSize().x / SCALE, block.getSize().y / SCALE, 1, 1, block.getDegree(), purple, purple, purple, purple);
        }

        BlockDTO[] staticBlocks = json.fromJson(BlockDTO[].class, contents.getStaticBlockLocations());
        for (var block : staticBlocks) {
            if (block.getSize().x == 9999 || block.getLocation().x == -5) continue;
            shape.rect(block.getLocation().x / SCALE + col_width  * OFFSETX, block.getLocation().y / SCALE + row_height * OFFSETY, 0, 0, block.getSize().x / SCALE, block.getSize().y / SCALE, 1, 1, block.getDegree(), blue, blue, blue, blue);
        }

        BlockDTO[] doors = json.fromJson(BlockDTO[].class, contents.getDoorLocations());
        for (var block : doors) {
            shape.rect(block.getLocation().x / SCALE + col_width  * OFFSETX, block.getLocation().y / SCALE + row_height * OFFSETY, 0, 0, block.getSize().x / SCALE, block.getSize().y / SCALE, 1, 1, block.getDegree(), yellow, yellow, yellow, yellow);
        }

        CircleDTO[] keys = json.fromJson(CircleDTO[].class, contents.getKeyLocations());
        for (var key : keys) {
            shape.setColor(yellow);
            shape.circle(key.getLocation().x / SCALE + col_width  * OFFSETX, key.getLocation().y / SCALE + row_height * OFFSETY, key.getRadius() / SCALE);
        }

        CircleDTO exit = json.fromJson(CircleDTO.class, contents.getExitLocation());
        if (exit.getLocation().x != -100) {
            shape.setColor(beige);
            shape.circle(exit.getLocation().x / SCALE + col_width  * OFFSETX, exit.getLocation().y / SCALE + row_height * OFFSETY, exit.getRadius() / SCALE);
        }


        CircleDTO[] characters = json.fromJson(CircleDTO[].class, contents.getCharacterLocations());
        for (var character : characters) {
            shape.setColor(yellowGreen);
            shape.circle(character.getLocation().x / SCALE + col_width  * OFFSETX, character.getLocation().y / SCALE, character.getRadius());
        }
        shape.end();
    }

}
