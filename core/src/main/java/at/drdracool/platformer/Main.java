package at.drdracool.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.Net.Protocol;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    ShapeRenderer shape;

    @Override
    public void create () {
        shape = new ShapeRenderer();

        Protocol protocol = Protocol.TCP;
        Socket socket = Gdx.net.newClientSocket(protocol, "127.0.0.1", 8888, null);

    }

    @Override
    public void render () {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.circle(50, 50, 50);
        shape.end();
    }
}
