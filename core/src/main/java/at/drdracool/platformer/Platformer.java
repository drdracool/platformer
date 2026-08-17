package at.drdracool.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Platformer extends Game {
    public ExtendViewport viewport;
    public SpriteBatch batch;
    ShapeRenderer shape;
    OrthographicCamera camera;
    Socket socket;
    SocketReceiveClient socketReceiveClient;
    SocketSendClient socketSendClient;
    InputHandler inputHandler;
    String connectionId;

    private MainScreen mainScreen;
    private SelectScreen selectScreen;
    private PlayScreen playScreen;


    public void create() {
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(8, 5, camera);
        batch = new SpriteBatch();
        shape = new ShapeRenderer();

        initConnection();
        initScreens();

        this.setScreen(mainScreen);
    }

    private void distributeServerMessage(String message) {
        System.out.println("received message: " + message);
        String[] fullMessage = message.split("\\|");
        switch (fullMessage[0]) {
            case("OnConnectionOpen"):
                Gdx.app.log("Network-MainThread", "Received new connectionId from socket server: " + message);
                this.connectionId = fullMessage[1];
                break;
            case("SELECT"):
                selectScreen.receiveMapNames(fullMessage[1]);
                break;
            case("PLAY"):
                playScreen.handlePlayMessage(fullMessage[1], fullMessage[2]);
                break;
        }
    }

    private void initConnection() {
        SocketHints hints = new SocketHints();
        socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "localhost", 8888, hints);

        socketSendClient = new SocketSendClient(socket);
        socketSendClient.connect();

        socketReceiveClient = new SocketReceiveClient(socket);
        socketReceiveClient.setMessageHandler(this::distributeServerMessage);
        socketReceiveClient.startListeningThread();

        Gdx.app.log("Network", "Connected to socket server successfully");

        inputHandler = new InputHandler(socketSendClient);
        Gdx.input.setInputProcessor(inputHandler);
    }

    private void initScreens() {
        mainScreen = new MainScreen(this);
        selectScreen = new SelectScreen(this, socketSendClient);
        playScreen = new PlayScreen(this, socketSendClient);
    }

    public void setSelectScreen() {
        setScreen(selectScreen);
    }

    public void setPlayScreen() {
        setScreen(playScreen);
    }

    public void setMainScreen() {setScreen(mainScreen);}

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
        mainScreen.dispose();
        //selectScreen.dispose();
        //playScreen.dispose();
        batch.dispose();
    }
}
