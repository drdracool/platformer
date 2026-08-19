package at.drdracool.platformer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainScreen implements Screen {
    final Platformer game;
    private final Stage stage;
    TextButton textButton;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
    ScreenViewport screenViewport;
    Table table;


    public MainScreen(final Platformer game) {
        this.game = game;
        screenViewport = new ScreenViewport();

        stage = new Stage(screenViewport);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/lgdxs-ui.json"));

        int col_width = Gdx.graphics.getWidth() / 12;
        int row_height = Gdx.graphics.getHeight() / 12;

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton buildMapButton = new TextButton("BUILD", skin, "oval3");
        buildMapButton.getLabel().setAlignment(Align.right);
        buildMapButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setBuildScreen();
                dispose();
                return true;
            }
        });
        table.add(buildMapButton).padLeft(col_width).width(col_width * 2).height(row_height);

        TextButton playButton = new TextButton("PLAY", skin, "oval4");
        playButton.getLabel().setAlignment(Align.right);
        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setSelectScreen();
                dispose();
                return true;
            }
        });
        table.row();
        table.add(playButton).padBottom(row_height).padLeft(col_width).width(col_width * 2).height(row_height);
        table.left().bottom();
        table.setDebug(true);
    }

    @Override
    public void render(float delta) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void show() {

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
