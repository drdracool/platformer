package at.drdracool.platformer.inputHandlers;

import at.drdracool.platformer.socketClients.SocketSendClient;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import java.io.IOException;

public class KeyInputHandler extends InputAdapter {
    SocketSendClient socketSendClient;

    public KeyInputHandler(SocketSendClient socketSendClient) {
        this.socketSendClient = socketSendClient;
    }

    @Override
    public boolean keyDown(int keycode) {
        try {
            socketSendClient.sendMessage("SERVICE|KEYDOWN|" + keycode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean keyUp (int keycode) {
        try {
            socketSendClient.sendMessage("SERVICE|KEYUP|" + keycode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
