package at.drdracool.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;

public class GameScreen implements Screen {
    Platformer game;
    FPSLogger fpsLogger;
    SocketClient socketClient;
    GameService gameService;
    InputHandler inputHandler;

    public GameScreen(Platformer game) {
        this.game = game;
        fpsLogger = new FPSLogger();
        socketClient = new SocketClient();
        gameService = new GameService();
        socketClient.setMessageHandler(gameService::handleIncomingMessage);
        socketClient.connect("localhost", 8888);
        inputHandler = new InputHandler(socketClient);
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void show() {

    }

    @Override
    public void render (float delta) {
        fpsLogger.log();
        draw();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        gameService.drawBlocks(game.shape, game.camera);
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}
