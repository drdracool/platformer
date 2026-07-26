package at.drdracool.platformer;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import java.io.IOException;

public class InputHandler extends InputAdapter {
    SocketSendClient socketSendClient;

    public InputHandler(SocketSendClient socketSendClient) {
        this.socketSendClient = socketSendClient;
    }

    @Override
    public boolean keyDown(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) {
            case Input.Keys.LEFT:
                System.out.println("Pressed Left");
                notifyServerMovement("MOVE|LEFT");
                keyProcessed = true;
                break;
            case Input.Keys.RIGHT:
                System.out.println("Pressed Right");
                notifyServerMovement("MOVE|RIGHT");
                keyProcessed = true;
                break;
            case Input.Keys.UP:
                System.out.println("Pressed Up");
                notifyServerMovement("MOVE|JUMP");
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }

    public boolean keyUp (int keycode) {
        boolean keyProcessed = false;
        switch (keycode) {
            case Input.Keys.LEFT:
                System.out.println("Finished pressing left");
                notifyServerMovement("MOVE|STOP_LEFT");
                keyProcessed = true;
                break;
            case Input.Keys.RIGHT:
                System.out.println("Finished pressing right");
                notifyServerMovement("MOVE|STOP_RIGHT");
                keyProcessed = true;
                break;
            case Input.Keys.UP:
                System.out.println("Finished pressing up");
                notifyServerMovement("MOVE|STOP_JUMPING");
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }

    public void notifyServerMovement(String command) {
        try {
            socketSendClient.sendMessage(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
