package at.drdracool.platformer.screens;

import at.drdracool.platformer.inputHandlers.ButtonInputListener;
import at.drdracool.platformer.inputHandlers.KeyInputHandler;
import at.drdracool.platformer.inputHandlers.UIManager;
import at.drdracool.platformer.interfaces.BasicScreen;
import at.drdracool.platformer.models.CustomStage;
import at.drdracool.platformer.models.MapContent;
import at.drdracool.platformer.socketClients.SocketSendClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

public class BuildScreen implements BasicScreen {
    Platformer game;
    CustomStage stage;
    ScreenViewport screenViewport;
    public Skin skin;
    Skin uiskin;
    Table table;
    SocketSendClient socketSendClient;

    MapContent mapContent;

    Label message;
    TextField nameTextField;

    int col_width = Gdx.graphics.getWidth() / 12;
    int row_height = Gdx.graphics.getHeight() / 12;
    Color orange = new Color(1, 0.6f, 0.204f, 1);
    Color yellowGreen = new Color(0.659f, 0.616f, 0.204f, 1);

    UIManager uiManager = new UIManager(new HashMap<>());
    Label toolTip;

    public BuildScreen(Platformer game, SocketSendClient socketSendClient) {
        this.game = game;
        this.socketSendClient = socketSendClient;
    }

    public void handleMessage(String category, String message) {
        switch (category) {
            case("UpdateMapContent"):
                mapContent = json.fromJson(MapContent.class, message);
                break;
            case("GetTip"):
                toolTip.setText(message);
                break;
            case("UpdateMapName"):
                nameTextField.setText(message);
                break;
            case("ReturnFailResult"):
                setMessage(message, orange);
                break;
            case("ReturnSuccessResult"):
                setMessage(message, yellowGreen);
                break;
        }
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("skin/lgdxs-ui.json"));
        uiskin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        screenViewport = new ScreenViewport();
        stage = new CustomStage(screenViewport);
        setUpHeaderTable();
        setUpInputProcessor();
        toolTip = new Label("", skin, "c1");
        toolTip.setWidth(100f);
        toolTip.setWrap(true);
        stage.addActor(toolTip);
    }

    private void setUpInputProcessor() {
        InputMultiplexer multiplexer = new InputMultiplexer();

        KeyInputHandler keyInputHandler = new KeyInputHandler(game.socketSendClient, uiManager);
        multiplexer.addProcessor(keyInputHandler);

        multiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(multiplexer);
    }

    private void setUpHeaderTable() {
        table = new Table();
        //table.debug();
        table.setFillParent(true);
        table.top().right();
        table.padTop(row_height * 0.5f).padRight(col_width * 0.5f).padLeft(col_width * 0.5f);
        stage.addActor(table);

        addBackButton();
        addSaveButton();
        addMessageCell();
        addHotKeys();
    }

    private void addBackButton() {
        table.top().right();
        TextButton backButton = new TextButton("Go Back", skin, "big4");
        backButton.getLabel().setAlignment(Align.center);
        backButton.addListener(new ButtonInputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    game.socketSendClient.sendMessage("SERVICE|QUIT");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                game.setNewScreen(new SelectScreen(game, socketSendClient));
            }
        });
        table.add(backButton).width(col_width * 1.5f).height(row_height).expandX().left();
    }

    private void addSaveButton() {
        Label label = new Label("Map Name: ", skin, "subtitle-c2");
        table.add(label).width(col_width * 2).height(row_height);
        nameTextField = new TextField("", uiskin, "spinner");
        table.add(nameTextField).width(col_width * 2).height(row_height).spaceRight(col_width * 0.3f);
        TextButton saveButton = new TextButton("Save", skin, "big1");
        saveButton.getLabel().setAlignment(Align.center);
        saveButton.addListener(new ButtonInputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                setMessage("", yellowGreen);
                if (Objects.equals(nameTextField.getText(), "")) {
                    setMessage("Please input map name", orange);
                } else {
                    try {
                        game.socketSendClient.sendMessage("SERVICE|SAVE|" + nameTextField.getText());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        table.add(saveButton).width(col_width * 1.1f).height(row_height);
    }

    private void addMessageCell() {
        table.row();
        message = new Label("", skin, "c2");
        message.setScale(0);
        table.add(message).spaceTop(row_height * 0.3f).colspan(4).right();
    }

    private void addHotKeys() {
        String[] assetName = new String[]{"block", "movingBlock", "keydoor", "exit", "revert", "remove"};
        table.row();
        Table hotKeysTable = new Table();
        table.add(hotKeysTable).colspan(4).expandY().bottom();

        table.debug();
        hotKeysTable.debug();

        for (var i = 0; i < assetName.length; i++) {
            ImageTextButton hotKey = new ImageTextButton(String.valueOf(i + 1), skin);
            TextureRegionDrawable asset = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("img/" + assetName[i] + ".png"))));
            System.out.println("asset path: " + "img/" + assetName[i] + ".png");
            hotKey.getStyle().imageUp = asset;
            hotKey.getStyle().imageDown = asset;
            hotKey.clearChildren();
            hotKey.add(hotKey.getLabel());
            hotKey.add(hotKey.getImage());
            hotKey.addListener(new InputListener(){
                @Override
                public void enter (InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
                    try {
                        game.socketSendClient.sendMessage("SERVICE|GETTIP|" + hotKey.getText());
                        toolTip.setPosition((hotKey.getX() + hotKey.getX() + hotKey.getWidth()) / 2 - 25, hotKey.getY() + hotKey.getHeight() + 25);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                @Override
                public void exit (InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
                    toolTip.setText("");
                }
            });
            hotKeysTable.add(hotKey).size(col_width, row_height).expandY().bottom().spaceRight(20f);
        }
    }

    private void setMessage(String text, Color color) {
        message.setText(text);
        message.setScale(1);
        message.setColor(color);
    }

    @Override
    public void render(float delta) {
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
        uiskin.dispose();
    }
}
