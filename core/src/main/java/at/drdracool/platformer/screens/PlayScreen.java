package at.drdracool.platformer.screens;

import at.drdracool.platformer.interfaces.BasicScreen;
import at.drdracool.platformer.inputHandlers.MoveInputHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;

public class PlayScreen implements BasicScreen {
    Platformer game;
    FPSLogger fpsLogger;
    Stage stage;
    ScreenViewport screenViewport;
    Skin skin;
    Table table;

    String charactersLocation = "";
    String blocksLocation = "";

    public PlayScreen(Platformer game) {
        this.game = game;
    }

    public void handleMessage(String category, String message) {
        switch (category) {
            case("UpdateAllCharacterLocations"):
                charactersLocation = message;
                break;
            case("UpdateAllMovingBlockLocations"):
                blocksLocation = message;
                break;
        }
    }

    @Override
    public void show() {
        fpsLogger = new FPSLogger();
        skin = new Skin(Gdx.files.internal("skin/lgdxs-ui.json"));

        setUpInputProcessor();
        setUpHeaderTable();
    }

    private void setUpInputProcessor() {
        screenViewport = new ScreenViewport();
        stage = new Stage(screenViewport);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);

        MoveInputHandler moveInputHandler = new MoveInputHandler(game.socketSendClient, true);
        multiplexer.addProcessor(moveInputHandler);

        Gdx.input.setInputProcessor(multiplexer);
    }

    private void setUpHeaderTable() {
        table = new Table();
        table.setFillParent(true);
        table.top().right();
        stage.addActor(table);

        int col_width = Gdx.graphics.getWidth() / 12;
        int row_height = Gdx.graphics.getHeight() / 12;
        TextButton mapButton = new TextButton("Go Back", skin, "big1");
        mapButton.getLabel().setAlignment(Align.center);
        mapButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    game.socketSendClient.sendMessage("PLAY|QUIT|" + game.connectionId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                game.setNewScreen(new SelectScreen(game, "PLAY"));
                return true;
            }
        });
        table.add(mapButton).width(col_width * 2).height(row_height).padTop(row_height * 0.5f).padRight(col_width * 0.5f);
    }

    @Override
    public void render (float delta) {
        fpsLogger.log();
        draw();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        game.drawMapService.drawCharacters(charactersLocation);
        game.drawMapService.drawBlocks(blocksLocation);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        stage.dispose();
        skin.dispose();
    }
}
