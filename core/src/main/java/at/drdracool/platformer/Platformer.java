package at.drdracool.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;import com.badlogic.gdx.utils.viewport.FitViewport;

public class Platformer extends Game {
    public ExtendViewport viewport;
    public SpriteBatch batch;
    ShapeRenderer shape;
    OrthographicCamera camera;

    private MainMenuScreen mainMenuScreen;
    private GameScreen gameScreen;

    public void create() {
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(8, 5, camera);
        batch = new SpriteBatch();

        shape = new ShapeRenderer();


        initScreens();

        this.setScreen(mainMenuScreen);
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

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
    }

    public void dispose() {
        mainMenuScreen.dispose();
        batch.dispose();
    }
}
