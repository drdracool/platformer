package at.drdracool.platformer.screens;

import at.drdracool.platformer.inputHandlers.BuildInputHandler;
import at.drdracool.platformer.inputHandlers.MoveInputHandler;
import at.drdracool.platformer.interfaces.BasicScreen;
import at.drdracool.platformer.models.GameCharacter;
import at.drdracool.platformer.services.BuildService;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;

public class BuildScreen implements BasicScreen {
    Platformer game;
    BuildService buildService;
    Stage stage;
    ScreenViewport screenViewport;
    Skin skin;
    Skin uiskin;
    Table table;

    GameCharacter character = new GameCharacter();

    public BuildScreen(Platformer game) {
        this.game = game;
    }

    public void handleMessage(String category, String message) {
        switch (category) {
            case ("InitChar"):
                buildService.createNewCharacter(message);
                break;
            case("UpdateAllCharacterLocations"):
                buildService.updateCharacterLocations(message);
                break;
            case("UpdateAllBlockLocations"):
                buildService.updateAllBlockLocations(message);
                break;
        }
    }

    @Override
    public void show() {
        buildService = new BuildService(character);
        skin = new Skin(Gdx.files.internal("skin/lgdxs-ui.json"));
        uiskin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        setUpInputProcessor();
        setUpHeaderTable();

        try {
            game.socketSendClient.sendMessage("BUILD|START");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUpInputProcessor() {
        screenViewport = new ScreenViewport();
        stage = new Stage(screenViewport);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);

        MoveInputHandler moveInputHandler = new MoveInputHandler(game.socketSendClient, false);
        multiplexer.addProcessor(moveInputHandler);

        BuildInputHandler buildInputHandler = new BuildInputHandler(game.socketSendClient, buildService);
        multiplexer.addProcessor(buildInputHandler);

        Gdx.input.setInputProcessor(multiplexer);
    }

    private void setUpHeaderTable() {
        int col_width = Gdx.graphics.getWidth() / 12;
        int row_height = Gdx.graphics.getHeight() / 12;

        table = new Table();
        //table.debug();
        table.setFillParent(true);
        table.top().right();
        table.padTop(row_height * 0.5f).padRight(col_width * 0.5f).padLeft(col_width * 0.5f);
        stage.addActor(table);

        table.top().right();
        TextButton backButton = new TextButton("Go Back", skin, "big4");
        backButton.getLabel().setAlignment(Align.center);
        backButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    game.socketSendClient.sendMessage("BUILD|QUIT");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                game.setNewScreen(new MainScreen(game));
                return true;
            }
        });
        table.add(backButton).width(col_width * 1.5f).height(row_height).expandX().left();

        Label label = new Label("Map Name: ", skin, "subtitle-c2");
        table.add(label).width(col_width * 2).height(row_height);
        TextField nameText = new TextField("", uiskin, "spinner");
        table.add(nameText).width(col_width * 2).height(row_height).spaceRight(col_width * 0.3f);

        TextButton saveButton = new TextButton("Save", skin, "big1");
        saveButton.getLabel().setAlignment(Align.center);
        saveButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    game.socketSendClient.sendMessage("BUILD|SAVE|" + nameText.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                return true;
            }
        });
        table.add(saveButton).width(col_width * 1.1f).height(row_height);

        table.row();
        String instructionText1 = "1/Place a still block 2/Place a moving block start";
        Label instruction1 = new Label(instructionText1, skin, "c1");
        table.add(instruction1).spaceTop(row_height * 0.3f).colspan(4).right();

        table.row();
        String instructionText2 = "3/Place a moving block end 4/Revert";
        Label instruction2 = new Label(instructionText2, skin, "c1");
        table.add(instruction2).spaceTop(row_height * 0.1f).colspan(4).right();
    }

    @Override
    public void render(float delta) {
        draw();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        buildService.drawCharactersAndBlocks(game.shape);

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

    }
}
