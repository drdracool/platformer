package at.drdracool.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.net.Socket;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SocketSendClient {
    private final Socket socket;
    private OutputStreamWriter outputStreamWriter;

    public SocketSendClient(Socket socket){
        this.socket = socket;
    }

    public void sendMessage(String message) throws IOException {
        outputStreamWriter.write(message + "\r\n");
        outputStreamWriter.flush();
    }

    public void connect() {
        try {
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        } catch (Exception e) {
            Gdx.app.error("Network", "Error when trying to connect to socket server");
        }
    }
}
