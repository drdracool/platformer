package at.drdracool.platformer.inputHandlers;

import at.drdracool.platformer.models.BuildCommand;
import at.drdracool.platformer.models.GameCharacter;
import at.drdracool.platformer.screens.BuildScreen;
import at.drdracool.platformer.services.BuildService;
import at.drdracool.platformer.socketClients.SocketSendClient;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BuildInputHandler extends InputAdapter {
    SocketSendClient socketSendClient;
    BuildService buildService;

    public BuildInputHandler(SocketSendClient socketSendClient, BuildService buildService) {
        this.socketSendClient = socketSendClient;
        this.buildService = buildService;
    }

    @Override
    public boolean keyTyped(char input) {
        List<Character> validCharacters = Arrays.asList('1', '2', '3', '4');
        if (validCharacters.contains(input)) {
            try {
                System.out.println("character location x: " + buildService.getCharacterLocation().x + " character location y: " + buildService.getCharacterLocation().y);
                BuildCommand buildCommand = new BuildCommand(input - '0', buildService.getCharacterLocation().x, buildService.getCharacterLocation().y);
                Json json = new Json();
                json.setOutputType(JsonWriter.OutputType.json);
                socketSendClient.sendMessage("BUILD|PLACE|" + json.toJson(buildCommand));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }

}
