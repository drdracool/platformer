package at.drdracool.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.function.Consumer;

public class SocketClient {
    private Socket socket;
    private BufferedReader bufferedReader;
    private OutputStreamWriter outputStreamWriter;
    private boolean connected = false;
    private Consumer<String> messageHandler;

    public SocketClient() {
    }

    public void connect(String ip, int port) {
        String serverString = "[" + ip + ":" + String.valueOf(port) + "]";
        try {
            SocketHints hints = new SocketHints();

            socket = Gdx.net.newClientSocket(Net.Protocol.TCP, ip, port, hints);

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            if (bufferedReader != null) connected = true;

            startListeningThread();

            if (connected) {
                Gdx.app.log("Network", "Connected to socket server " + serverString + " successfully");
            }
        } catch (Exception e) {
            Gdx.app.error("Network", "Error when trying to connect to socket server " + serverString);
        }
    }

    private void startListeningThread() {
        new Thread(() -> {
            try {
                String line;
                while (connected && (line = bufferedReader.readLine()) != null) {
                    final String messageFromServer = line;
                    Gdx.app.postRunnable(() -> handleMessageOnMainThread(messageFromServer));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void setMessageHandler(Consumer<String> handler) {
        this.messageHandler = handler;
    }

    private void handleMessageOnMainThread(String message) {
        if (messageHandler != null) {
            messageHandler.accept(message);
        }
    }

    public void sendMessage(String message) throws IOException {
        outputStreamWriter.write(message + "\r\n");
        outputStreamWriter.flush();
    }
}
