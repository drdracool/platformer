package at.drdracool.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.*;
import java.util.HashMap;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    FitViewport viewport;
    SpriteBatch spriteBatch;
    SocketClient socketClient;
    GameService gameService;

    @Override
    public void create () {
        viewport = new FitViewport(8, 5);
        spriteBatch = new SpriteBatch();
        socketClient = new SocketClient();
        gameService = new GameService(socketClient);
        socketClient.setMessageHandler(gameService::handleIncomingMessage);
        socketClient.connect("localhost", 8888);
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
        gameService.handleMoveInput();
    }

    private void logic() {
        gameService.handleMoveLogic(viewport);
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        gameService.drawSprites(spriteBatch);
        spriteBatch.end();
    }
}
