package at.drdracool.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class SocketReceiveClient {
    private BufferedReader bufferedReader;
    private Consumer<String> messageHandler;
    private Socket socket;

    public SocketReceiveClient(Socket socket) {
        this.socket = socket;
    }

    public void startListeningThread() {
        new Thread(() -> {
            try {
                String line;
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (bufferedReader != null && (line = bufferedReader.readLine()) != null) {
                    final String messageFromServer = line;
                    Gdx.app.postRunnable(() -> handleMessageOnMainThread(messageFromServer));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void handleMessageOnMainThread(String message) {
        if (messageHandler != null) {
            messageHandler.accept(message);
        }
    }

    public void setMessageHandler(Consumer<String> handler) {
        this.messageHandler = handler;
    }
}
