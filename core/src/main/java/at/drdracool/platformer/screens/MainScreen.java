package at.drdracool.platformer.screens;

import at.drdracool.platformer.inputHandlers.ButtonInputListener;
import at.drdracool.platformer.interfaces.BasicScreen;
import at.drdracool.platformer.socketClients.SocketSendClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.io.IOException;


public class MainScreen implements BasicScreen {
    final Platformer game;
    Stage stage;
    Skin skin;
    ScreenViewport screenViewport;
    Table table;
    SocketSendClient socketSendClient;

    public MainScreen(final Platformer game, SocketSendClient socketSendClient) {
        this.game = game;
        this.socketSendClient = socketSendClient;
    }

    public void handleMessage(String category, String message){}

    @Override
    public void show() {
        screenViewport = new ScreenViewport();

        stage = new Stage(screenViewport);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/lgdxs-ui.json"));

        int col_width = Gdx.graphics.getWidth() / 12;
        int row_height = Gdx.graphics.getHeight() / 12;

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton playButton = new TextButton("PLAY", skin, "oval4");
        playButton.getLabel().setAlignment(Align.right);
        playButton.addListener(new ButtonInputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    socketSendClient.sendMessage("SETSERVICE|PLAY");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                game.setNewScreen(new SelectScreen(game, socketSendClient));
            }
        });
        table.add(playButton).padLeft(col_width * 0.8f).width(col_width * 2).height(row_height);

        TextButton buildMapButton = new TextButton("BUILD", skin, "oval3");
        buildMapButton.getLabel().setAlignment(Align.right);
        buildMapButton.addListener(new ButtonInputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    socketSendClient.sendMessage("SETSERVICE|BUILD");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                game.setNewScreen(new SelectScreen(game, socketSendClient));
            }
        });
        table.row();
        table.add(buildMapButton).padLeft(col_width * 0.8f).width(col_width * 2).height(row_height).padBottom(row_height);

        table.left().bottom();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

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
