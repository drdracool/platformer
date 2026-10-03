package at.drdracool.platformer.screens;

import at.drdracool.platformer.inputHandlers.ButtonInputListener;
import at.drdracool.platformer.interfaces.BasicScreen;
import at.drdracool.platformer.inputHandlers.MoveInputHandler;
import at.drdracool.platformer.models.CustomDialog;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    Skin skinUI;
    Table table;
    Label timeElapsed;
    String finalTime = "";

    String mapContent = "";

    CustomDialog dialog;
    int col_width = Gdx.graphics.getWidth() / 12;
    int row_height = Gdx.graphics.getHeight() / 12;

    public PlayScreen(Platformer game) {
        this.game = game;
    }

    public void handleMessage(String category, String message) {
        switch (category) {
            case "UpdateMapContent":
                mapContent = message;
                break;
            case "UpdateTimer":
                finalTime = message;
                timeElapsed.setText("Time elapsed: " + message + " seconds");
                break;
            case "GAMEOVER":
                System.out.println("game over");
                dialog.addSmallText("Total time: " + finalTime + " seconds");
                dialog.show(stage);
                break;
        }
    }

    @Override
    public void show() {
        fpsLogger = new FPSLogger();
        skin = new Skin(Gdx.files.internal("skin/lgdxs-ui.json"));
        skinUI = new Skin(Gdx.files.internal("ui/uiskin.json"));
        setUpInputProcessor();
        setUpHeaderTable();
        dialog = new CustomDialog("", skinUI, col_width, row_height, skin);
        dialog.text("CONGRATS! You've won");
        dialog.getContentTable().row();

        InputListener listener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setNewScreen(new MainScreen(game));
            }
        };
        dialog.button("Return Home", listener);

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

        timeElapsed = new Label("", skin, "c1");
        table.add(timeElapsed).padTop(row_height * 0.5f).padRight(col_width * 0.5f);

        TextButton mapButton = new TextButton("Go Back", skin, "big1");
        mapButton.getLabel().setAlignment(Align.center);
        mapButton.addListener(new ButtonInputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    game.socketSendClient.sendMessage("PLAY|QUIT|" + game.connectionId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                game.setNewScreen(new SelectScreen(game, "PLAY"));
            }
        });
        table.add(mapButton).width(col_width * 2).height(row_height).padTop(row_height * 0.5f).padRight(col_width * 0.5f);
    }

    @Override
    public void render (float delta) {
        //fpsLogger.log();
        draw();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        game.drawMapService.drawMapContent(mapContent);

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
