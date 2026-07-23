package at.drdracool.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Platformer extends Game {
    public FitViewport viewport;
    public SpriteBatch batch;
    public BitmapFont font;
    ShapeRenderer shape;
    OrthographicCamera camera;

    private MainMenuScreen mainMenuScreen;
    private GameScreen gameScreen;

    public void create() {
        viewport = new FitViewport(8, 5);
        batch = new SpriteBatch();
        font = new BitmapFont();

        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
        shape = new ShapeRenderer();
        camera = new OrthographicCamera();

        initScreens();

        this.setScreen(gameScreen);
    }

    private void initScreens() {
        mainMenuScreen = new MainMenuScreen(this);
        gameScreen = new GameScreen(this);
    }

    public void setGameScreen() {
        setScreen(gameScreen);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        mainMenuScreen.dispose();
        batch.dispose();
        font.dispose();
    }
}
