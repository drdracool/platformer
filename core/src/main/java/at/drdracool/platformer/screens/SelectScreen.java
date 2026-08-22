package at.drdracool.platformer.screens;

import at.drdracool.platformer.interfaces.BasicScreen;
import at.drdracool.platformer.socketClients.SocketSendClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;
import java.util.Objects;

public class SelectScreen implements BasicScreen {
    Platformer game;
    Stage stage;
    Skin skin;
    ScreenViewport screenViewport;
    Table table;

    String[] mapNames;

    public SelectScreen(Platformer game) {
        this.game = game;
    }

    @Override
    public void show() {
        try {
            game.socketSendClient.sendMessage("SELECT");
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

    public void handleMessage(String category, String message) {
        int col_width = Gdx.graphics.getWidth() / 12;
        int row_height = Gdx.graphics.getHeight() / 12;

        if (!Objects.equals(category, "SELECT")) return;

        mapNames = message.split(",");
        for (var mapName : mapNames) {
            TextButton mapButton = new TextButton(mapName, skin, "oval5");
            mapButton.getLabel().setAlignment(Align.center);
            mapButton.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    try {
                        game.socketSendClient.sendMessage("PLAY|START|" + mapName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    game.setNewScreen(new PlayScreen(game));
                    return true;
                }
            });
            table.row();
            table.add(mapButton).width(col_width * 6).height(row_height * 1.5f);
        }

        TextButton mapButton = new TextButton("Go Back", skin, "oval3");
        mapButton.getLabel().setAlignment(Align.center);
        mapButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setNewScreen(new MainScreen(game));
                return true;
            }
        });

        table.row();
        table.add(mapButton).width(col_width * 6).height(row_height * 1.5f);
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
