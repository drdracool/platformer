package at.drdracool.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

import java.io.IOException;

public class InputHandler extends InputAdapter {
    SocketClient socketClient;

    public InputHandler(SocketClient socketClient) {
        this.socketClient = socketClient;
    }
    public boolean keyDown(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) {
            case Input.Keys.LEFT:
                System.out.println("Pressed Left");
                notifyServerMovement("LEFT");
                keyProcessed = true;
                break;
            case Input.Keys.RIGHT:
                System.out.println("Pressed Right");
                notifyServerMovement("RIGHT");
                keyProcessed = true;
                break;
            case Input.Keys.UP:
                System.out.println("Pressed Up");
                notifyServerMovement("JUMP");
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }

    public boolean keyUp (int keycode) {
        return false;
    }

    public void handleKeyPressed(){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            notifyServerMovement("LEFT");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            notifyServerMovement("RIGHT");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            notifyServerMovement("UP");
        }
    }

    public void notifyServerMovement(String command) {
        try {
            socketClient.sendMessage(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
