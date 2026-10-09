package at.drdracool.platformer.inputHandlers;

import at.drdracool.platformer.socketClients.SocketSendClient;
import com.badlogic.gdx.InputAdapter;

import java.io.IOException;

public class KeyInputHandler extends InputAdapter {
    SocketSendClient socketSendClient;
    UIManager uiManager;

    public KeyInputHandler(SocketSendClient socketSendClient, UIManager uiManager) {
        this.socketSendClient = socketSendClient;
        this.uiManager = uiManager;
    }

    @Override
    public boolean keyDown(int keycode) {
        try {
            socketSendClient.sendMessage("SERVICE|KEYDOWN|" + keycode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("KEYDOWN");
        uiManager.handleHotKey(keycode, true);
        return true;
    }

    @Override
    public boolean keyUp (int keycode) {
        try {
            socketSendClient.sendMessage("SERVICE|KEYUP|" + keycode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("KEYUP");

        uiManager.handleHotKey(keycode, false);
        return true;
    }
}
