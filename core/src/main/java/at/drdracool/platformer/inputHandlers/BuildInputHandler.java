package at.drdracool.platformer.inputHandlers;


import at.drdracool.platformer.socketClients.SocketSendClient;
import com.badlogic.gdx.InputAdapter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BuildInputHandler extends InputAdapter {
    SocketSendClient socketSendClient;

    public BuildInputHandler(SocketSendClient socketSendClient) {
        this.socketSendClient = socketSendClient;
    }

    @Override
    public boolean keyTyped(char input) {
        List<Character> validCharacters = Arrays.asList('1', '2', '3', '4');
        if (validCharacters.contains(input)) {
            try {
                socketSendClient.sendMessage("BUILD|PLACE|" + (input - '0'));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }

}
