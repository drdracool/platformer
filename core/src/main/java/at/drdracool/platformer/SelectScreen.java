package at.drdracool.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;

public class SelectScreen implements Screen {
    Platformer game;
    Stage stage;
    Skin skin;
    ScreenViewport screenViewport;
    Table table;
    SocketSendClient socketSendClient;

    String[] mapNames;

    public SelectScreen(Platformer game, SocketSendClient socketSendClient) {
        this.game = game;
        this.socketSendClient = socketSendClient;
    }

    @Override
    public void show() {
        try {
            socketSendClient.sendMessage("SELECT");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        screenViewport = new ScreenViewport();
        stage = new Stage(screenViewport);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/lgdxs-ui.json"));



        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
    }

    public void receiveMapNames(String message) {
        int col_width = Gdx.graphics.getWidth() / 12;
        int row_height = Gdx.graphics.getHeight() / 12;

        mapNames = message.split(",");
        for (var mapName : mapNames) {
            TextButton mapButton = new TextButton(mapName, skin, "oval3");
            mapButton.getLabel().setAlignment(Align.center);

            table.add(mapButton).padLeft(col_width).width(col_width * 8).height(row_height * 1.5f);
        }
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
