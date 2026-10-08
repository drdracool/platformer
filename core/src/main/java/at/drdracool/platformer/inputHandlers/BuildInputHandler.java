package at.drdracool.platformer.inputHandlers;


import at.drdracool.platformer.socketClients.SocketSendClient;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BuildInputHandler extends InputAdapter {
    SocketSendClient socketSendClient;
    ImageTextButton big1;
    List<Character> validCharacters = Arrays.asList('1', '2', '3', '4', '5','6');

    public BuildInputHandler(SocketSendClient socketSendClient, ImageTextButton big1) {
        this.socketSendClient = socketSendClient;
        this.big1 = big1;
    }

    @Override
    public boolean keyTyped(char input) {
        if (validCharacters.contains(input)) {
            try {

                socketSendClient.sendMessage("SERVICE|PLACE|" + (input - '0'));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean keyDown(int input) {
        System.out.println("input keydown: " + input);
        if (input == 8) {
            System.out.println("keydown");
            big1.setDisabled(true);
            System.out.println("big 1 disabled status:" +  big1.isDisabled());
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int input) {
        System.out.println("input keyup: " + input);
        if (input == 8) {
            System.out.println("keyup");
            big1.setDisabled(false);
            System.out.println("big 1 disabled status:" +  big1.isDisabled());
            return true;
        }
        return false;
    }

}
