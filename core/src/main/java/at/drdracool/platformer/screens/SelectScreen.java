package at.drdracool.platformer.screens;

import at.drdracool.platformer.inputHandlers.ButtonInputListener;
import at.drdracool.platformer.interfaces.BasicScreen;
import at.drdracool.platformer.models.MapContent;
import at.drdracool.platformer.socketClients.SocketSendClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class SelectScreen implements BasicScreen {
    Platformer game;
    Stage stage;
    Skin skin;
    Skin skinUI;
    ScreenViewport screenViewport;
    Table table;
    SocketSendClient socketSendClient;

    MapContent[] contents;
    MapContent currentMap;

    int col_width = Gdx.graphics.getWidth() / 12;
    int row_height = Gdx.graphics.getHeight() / 12;

    public SelectScreen(Platformer game, SocketSendClient socketSendClient) {
        this.game = game;
        this.socketSendClient = socketSendClient;
    }

    public void handleMessage(String category, String message) {
        if (category.equals("SELECT")) {
            contents = json.fromJson(MapContent[].class, message);
            addMapOptionsToTable();
        } else if (category.equals("GETSERVICE")) {
            if (message.equals("PLAY")) {
                game.setNewScreen(new PlayScreen(game, socketSendClient));
            } else if (message.equals("BUILD")) {
                game.setNewScreen(new BuildScreen(game, socketSendClient));
            }
        }
    }

    @Override
    public void show() {
        try {
            game.socketSendClient.sendMessage("SERVICE|SELECT");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        screenViewport = new ScreenViewport();
        stage = new Stage(screenViewport);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/lgdxs-ui.json"));
        skinUI = new Skin(Gdx.files.internal("ui/uiskin.json"));

        table = new Table();
        table.setFillParent(true);
        table.top().padTop(row_height).padBottom(row_height);
        table.left().padLeft(col_width);
        stage.addActor(table);
    }

    private void addMapOptionsToTable() {
        TextButton backButton = new TextButton("Go Back", skin, "oval3");
        backButton.getLabel().setAlignment(Align.center);
        backButton.addListener(new ButtonInputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    socketSendClient.sendMessage("SETSERVICE|MAIN");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                game.setNewScreen(new MainScreen(game, socketSendClient));
            }
        });

        table.add(backButton).width(col_width * 3.4f).height(row_height).left().row();

        Table scrollableTable = new Table();

        for (var content : contents) {
            TextButton mapButton = new TextButton(content.getName(), skin, "oval5");
            mapButton.getLabel().setAlignment(Align.center);
            mapButton.addListener(new ButtonInputListener(){
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    try {
                        game.socketSendClient.sendMessage("SERVICE|START|" + content.getName());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
                    currentMap = content;
                }
                @Override
                public void exit(InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
                    currentMap = null;
                }
            });
            scrollableTable.add(mapButton).size(col_width * 3.4f, row_height).row();
        }

        scrollableTable.pack();
        scrollableTable.setTransform(true);  //clipping enabled

        Skin sgx = new Skin(Gdx.files.internal("default/uiskin.json"));
        final ScrollPane scroll = new ScrollPane(scrollableTable, sgx);
        scroll.addListener(new InputListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                stage.setScrollFocus(scroll);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                stage.setScrollFocus(null);
            }
        });
        scroll.setFadeScrollBars(false);
        table.add(scroll).width(col_width * 4).height(row_height * 7f).expandY().bottom();

    }

    @Override
    public void render(float delta) {

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if (currentMap != null) {
            game.drawMapService.drawMiniMap(currentMap);
        }
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
