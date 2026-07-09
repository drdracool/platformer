package at.drdracool.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.net.Socket;
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

    @Override
    public void create () {
        viewport = new FitViewport(8, 5);
        spriteBatch = new SpriteBatch();
        knightTexture = new Texture("knight.png");
        knightSprite = new Sprite(knightTexture);
        knightSprite.setSize(1, 1);

        SocketHints hints = new SocketHints();
        Protocol protocol = Protocol.TCP;

        Socket socket = Gdx.net.newClientSocket(protocol, "localhost", 8888, hints);

        DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());

        try {
            dOut.writeByte(1);
            dOut.write("This is the first type of message.\r\n".getBytes());
            dOut.flush(); // Send off the data
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            socket.dispose();
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render () {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        knightSprite.draw(spriteBatch);
        spriteBatch.end();
    }
}
