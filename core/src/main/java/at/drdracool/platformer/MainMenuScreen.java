package at.drdracool.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final Platformer game;
    private final Stage stage;
    TextButton textButton;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;

    public MainMenuScreen(final Platformer game) {
        this.game = game;
        stage = new Stage(game.viewport);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/lgdxs-ui.json"));

        int col_width = Gdx.graphics.getWidth() / 12;
        int row_height = Gdx.graphics.getWidth() / 12;

        TextButton buildMapButton = new TextButton("Build New Map", skin, "oval3");
        buildMapButton.getLabel().setAlignment(Align.right);
        buildMapButton.setSize(col_width * 2, row_height);
        buildMapButton.setPosition(col_width * 3, row_height * 3);
        stage.addActor(buildMapButton);

        TextButton playButton = new TextButton("Start New Game", skin, "oval4");
        playButton.getLabel().setAlignment(Align.right);
        playButton.getLabel().setAlignment(Align.right);
        playButton.setSize(col_width * 2, row_height);
        playButton.setPosition(col_width * 3, row_height * 4);
        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setGameScreen();
                dispose();
                return true;
            }
        });
        stage.addActor(playButton);
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
        skin.dispose();
        stage.dispose();
    }
}
