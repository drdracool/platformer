package at.drdracool.platformer.screens;

import at.drdracool.platformer.inputHandlers.BuildInputHandler;
import at.drdracool.platformer.inputHandlers.MoveInputHandler;
import at.drdracool.platformer.models.GameCharacter;
import at.drdracool.platformer.models.MovingBlockDraft;
import at.drdracool.platformer.models.Pair;
import at.drdracool.platformer.models.StaticBlockDraft;
import at.drdracool.platformer.services.BuildService;
import at.drdracool.platformer.socketClients.SocketSendClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class BuildScreen implements Screen {
    Platformer game;
    BuildService buildService;
    SocketSendClient socketSendClient;
    Stage stage;
    ScreenViewport screenViewport;
    Skin skin;
    Skin uiskin;
    Table table;



    String name;
    List<StaticBlockDraft> staticBlocks = new ArrayList<>();
    List<MovingBlockDraft> movingBlocks = new ArrayList<>();

    public BuildScreen(Platformer game, SocketSendClient socketSendClient) {
        this.game = game;
        this.socketSendClient = socketSendClient;
        this.buildService = new BuildService();
    }

    public void handleMessage(String category, String message) {
        switch (category) {
            case ("InitChar"):
                buildService.createNewCharacter(message);
                break;
        }
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("skin/lgdxs-ui.json"));
        uiskin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        setUpInputProcessor();
        setUpHeaderTable();

        try {
            socketSendClient.sendMessage("BUILD|START");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUpInputProcessor() {
        screenViewport = new ScreenViewport();
        stage = new Stage(screenViewport);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);

        MoveInputHandler moveInputHandler = new MoveInputHandler(socketSendClient, false);
        multiplexer.addProcessor(moveInputHandler);

        Gdx.input.setInputProcessor(multiplexer);
    }

    private void setUpHeaderTable() {
        int col_width = Gdx.graphics.getWidth() / 12;
        int row_height = Gdx.graphics.getHeight() / 12;

        table = new Table();
        //table.debug();
        table.setFillParent(true);
        table.top().right();
        table.padTop(row_height * 0.5f).padRight(col_width * 0.5f);
        stage.addActor(table);

        Label label = new Label("Map Name: ", skin, "subtitle-c2");
        table.add(label).width(col_width * 2).height(row_height);
        TextField nameText = new TextField("", uiskin, "spinner");
        table.add(nameText).width(col_width * 2).height(row_height).spaceRight(col_width * 0.3f);

        TextButton saveButton = new TextButton("Save", skin, "big1");
        saveButton.getLabel().setAlignment(Align.center);
        saveButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                name = nameText.getText();
//                try {
//                    socketSendClient.sendMessage("BUILD|" + game.connectionId);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
                game.setSelectScreen();
                dispose();
                return true;
            }
        });
        table.add(saveButton).width(col_width * 1.1f).height(row_height);
    }

    @Override
    public void render(float delta) {
        draw();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        buildService.drawCharacter(game.shape);
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
