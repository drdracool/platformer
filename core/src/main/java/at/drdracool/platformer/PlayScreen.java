package at.drdracool.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;

import java.io.IOException;

public class PlayScreen implements Screen {
    Platformer game;
    FPSLogger fpsLogger;
    SocketSendClient socketSendClient;
    GameService gameService;

    public PlayScreen(Platformer game, SocketSendClient socketSendClient) {
        this.game = game;
        this.socketSendClient = socketSendClient;
    }

    public void handlePlayMessage(String category, String message) {
        switch (category) {
            case("InitChar"):
                gameService.createOwnCharacter(game.connectionId, message);
                break;
            case("UpdateAllCharacterLocations"):
                gameService.updateCharacterLocations(message);
                break;
            case("InitMap"):
                gameService.initiateMap(message);
                break;
            case("UpdateAllMovingBlockLocations"):
                gameService.updateAllMovingBlockLocations(message);
                break;
            case("RemoveCharacter"):
                Gdx.app.log("Network-MainThread", "Received connection close request from socket server: " + message);
                gameService.removeDisconnectedConnection(message);
                break;
        }
    }

    @Override
    public void show() {
        gameService = new GameService();
        fpsLogger = new FPSLogger();
        InputHandler inputHandler = new InputHandler(socketSendClient);
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void render (float delta) {
        fpsLogger.log();
        draw();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        gameService.drawCharactersAndBlocks(game.shape);
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
