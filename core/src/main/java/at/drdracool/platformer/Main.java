package at.drdracool.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.DataOutputStream;
import java.io.IOException;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    FitViewport viewport;
    SpriteBatch spriteBatch;
    Texture knightTexture;
    Sprite knightSprite;
    Socket socket;
    DataOutputStream dOut;

    @Override
    public void create () {
        viewport = new FitViewport(8, 5);
        spriteBatch = new SpriteBatch();
        knightTexture = new Texture("knight.png");
        knightSprite = new Sprite(knightTexture);
        knightSprite.setSize(1, 1);

        SocketHints hints = new SocketHints();
        Protocol protocol = Protocol.TCP;

        socket = Gdx.net.newClientSocket(protocol, "localhost", 8888, hints);

        dOut = new DataOutputStream(socket.getOutputStream());

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render () {
        input();
        logic();
        draw();
    }

    private void input() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            knightSprite.translateX(speed * delta);
            notifyServerMovement();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            knightSprite.translateX(-speed * delta);
            notifyServerMovement();
        }
    }

    private void notifyServerMovement() {
        float knightX = knightSprite.getX();
        float knightY = knightSprite.getY();
        try {
            dOut.write(String.format("%.2f %.2f\r\n", knightX,knightY).getBytes());
            dOut.flush(); // Send off the data
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float knightWidth = knightSprite.getWidth();
        float knightHeight = knightSprite.getHeight();

        knightSprite.setX(MathUtils.clamp(knightSprite.getX(), 0, worldWidth - knightWidth));



    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        knightSprite.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        socket.dispose();
    }
}
