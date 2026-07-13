package at.drdracool.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.Json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class SocketClient {
    private Socket socket;
    private BufferedReader bufferedReader;
    private OutputStreamWriter outputStreamWriter;
    private boolean connected = false;
    private GameService gameService;
    private String connectionId;

    public SocketClient() {
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public boolean connect(String ip, int port) {
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
                return connected;
            }
        } catch (Exception e) {
            Gdx.app.error("Network", "Error when trying to connect to socket server " + serverString);
        }
        return false;
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

    private void handleMessageOnMainThread(String message) {
        String[] fullMessage = message.split("\\|");
        switch (fullMessage[0]) {
            case("OnConnection"):
                Gdx.app.log("Nework-MainThread", "Received connectionId from socket server: " + message);
                this.connectionId = fullMessage[1];
                break;
            case("UpdateAllLocations"):
                Json json = new Json();

                List<Character> characters = json.fromJson(List.class, fullMessage[1]);
                break;
        }

    }

    public String getConnectionId() {
        return connectionId;
    }

    public void sendMessage(String message) throws IOException {
        outputStreamWriter.write(message + "\r\n");
        outputStreamWriter.flush();
    }
}
