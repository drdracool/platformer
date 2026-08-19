package at.drdracool.platformer.services;

import at.drdracool.platformer.models.GameCharacter;
import at.drdracool.platformer.models.Pair;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class BuildService {
    float SPEED = 5;

    GameCharacter character = new GameCharacter();

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
}
