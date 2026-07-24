package at.drdracool.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.io.IOException;

public class Platformer extends Game {
    public ExtendViewport viewport;
    public SpriteBatch batch;
    ShapeRenderer shape;
    OrthographicCamera camera;
    SocketClient socketClient;
    InputHandler inputHandler;
    String connectionId;

    private MainMenuScreen mainMenuScreen;
    private GameScreen gameScreen;

    GameService gameService;

    public void create() {
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(8, 5, camera);
        batch = new SpriteBatch();
        shape = new ShapeRenderer();

        socketClient = new SocketClient();
        socketClient.setMessageHandler(this::distributeServerMessage);
        socketClient.connect("localhost", 8888);
        inputHandler = new InputHandler(socketClient);
        Gdx.input.setInputProcessor(inputHandler);

        initScreens();

        this.setScreen(mainMenuScreen);
    }

    private void distributeServerMessage(String message) {
        String[] fullMessage = message.split("\\|");
        switch (fullMessage[0]) {
            case("OnConnectionOpen"):
                Gdx.app.log("Nework-MainThread", "Received new connectionId from socket server: " + message);
                this.connectionId = fullMessage[1];
                break;
            case("OnConnectionClose"):
                Gdx.app.log("Nework-MainThread", "Received connection close request from socket server: " + message);
                String connectionId = fullMessage[1];
                gameScreen.gameService.removeDisconnectedConnection(connectionId);
                break;
            case("PLAY"):
                gameScreen.handlePlayMessage(fullMessage[1], fullMessage[2]);
        }
    }

    public void startGame() throws IOException {
        socketClient.sendMessage("START");
    }

    private void initScreens() {
        gameService = new GameService();
        mainMenuScreen = new MainMenuScreen(this);
        gameScreen = new GameScreen(this, gameService);
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
