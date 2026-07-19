package at.drdracool.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    FitViewport viewport;
    SocketClient socketClient;
    GameService gameService;
    InputHandler inputHandler;
    ShapeRenderer shape;
    OrthographicCamera camera;

    @Override
    public void create () {
        shape = new ShapeRenderer();
        camera = new OrthographicCamera();
        viewport = new FitViewport(5, 3.8f);
        socketClient = new SocketClient();
        gameService = new GameService();
        socketClient.setMessageHandler(gameService::handleIncomingMessage);
        socketClient.connect("localhost", 8888);
        inputHandler = new InputHandler(socketClient);
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render () {
        input();
        logic();
        draw();
    }

    private void input() {
    }

    private void logic() {
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        gameService.drawBlocks(shape, camera);
    }
}
