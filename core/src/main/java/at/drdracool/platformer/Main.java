package at.drdracool.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.SocketHints;

import java.io.DataOutputStream;
import java.io.IOException;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    ShapeRenderer shape;

    @Override
    public void create () {
        shape = new ShapeRenderer();

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
    public void render () {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.circle(50, 50, 50);
        shape.end();
    }
}
