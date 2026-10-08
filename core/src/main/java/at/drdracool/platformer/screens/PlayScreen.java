package at.drdracool.platformer.screens;

import at.drdracool.platformer.inputHandlers.ButtonInputListener;
import at.drdracool.platformer.interfaces.BasicScreen;
import at.drdracool.platformer.inputHandlers.KeyInputHandler;
import at.drdracool.platformer.models.CustomDialog;
import at.drdracool.platformer.models.MapContent;
import at.drdracool.platformer.socketClients.SocketSendClient;
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

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

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
    SocketSendClient socketSendClient;

    MapContent mapContent;

    CustomDialog dialog;
    int col_width = Gdx.graphics.getWidth() / 12;
    int row_height = Gdx.graphics.getHeight() / 12;

    public PlayScreen(Platformer game, SocketSendClient socketSendClient) {
        this.game = game;
        this.socketSendClient = socketSendClient;
    }

    public void handleMessage(String category, String message) {
        switch (category) {
            case "UpdateMapContent":
                mapContent = json.fromJson(MapContent.class, message);
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
                try {
                    socketSendClient.sendMessage("SETSERVICE|MAIN");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                game.setNewScreen(new MainScreen(game, socketSendClient));
            }
        };
        dialog.button("Return Home", listener);

    }

    private void setUpInputProcessor() {
        screenViewport = new ScreenViewport();
        stage = new Stage(screenViewport);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);

        KeyInputHandler moveInputHandler = new KeyInputHandler(game.socketSendClient);
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
                    game.socketSendClient.sendMessage("SERVICE|QUIT|" + game.connectionId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                game.setNewScreen(new SelectScreen(game, socketSendClient));
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

        if (mapContent != null) {
            game.drawMapService.drawMapContent(mapContent);
        }

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
        skinUI.dispose();
    }
}
