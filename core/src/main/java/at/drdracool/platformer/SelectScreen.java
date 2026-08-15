package at.drdracool.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;

public class SelectScreen implements Screen {
    Platformer game;
    private final Stage stage;
    Skin skin;
    ScreenViewport screenViewport;
    Table table;
    SocketSendClient socketSendClient;

    public SelectScreen(Platformer game, SocketSendClient socketSendClient) {
        this.game = game;
        screenViewport = new ScreenViewport();
        this.socketSendClient = socketSendClient;

        stage = new Stage(screenViewport);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/lgdxs-ui.json"));

        int col_width = Gdx.graphics.getWidth() / 12;
        int row_height = Gdx.graphics.getHeight() / 12;

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void show() {
        try {
            socketSendClient.sendMessage("SELECT");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void renderAllMaps(String message) {
        System.out.println(message);
    }

    @Override
    public void render(float delta) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
        skin.dispose();
        stage.dispose();
    }
}
