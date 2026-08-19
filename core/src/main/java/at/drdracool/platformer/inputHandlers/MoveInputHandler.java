package at.drdracool.platformer.inputHandlers;

import at.drdracool.platformer.socketClients.SocketSendClient;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import java.io.IOException;

public class MoveInputHandler extends InputAdapter {
    SocketSendClient socketSendClient;
    boolean hasGravity;

    public MoveInputHandler(SocketSendClient socketSendClient, boolean hasGravity) {
        this.socketSendClient = socketSendClient;
        this.hasGravity = hasGravity;
    }

    @Override
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
                notifyServerMovement("UP");
                keyProcessed = true;
                break;
            case Input.Keys.DOWN:
                System.out.println("Pressed Down");
                notifyServerMovement("DOWN");
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
                notifyServerMovement("STOP_LEFT");
                keyProcessed = true;
                break;
            case Input.Keys.RIGHT:
                System.out.println("Finished pressing right");
                notifyServerMovement("STOP_RIGHT");
                keyProcessed = true;
                break;
            case Input.Keys.UP:
                System.out.println("Finished pressing up");
                notifyServerMovement("STOP_UP");
                keyProcessed = true;
                break;
            case Input.Keys.DOWN:
                System.out.println("Finished pressing up");
                notifyServerMovement("STOP_DOWN");
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }

    public void notifyServerMovement(String command) {
        try {
            if (hasGravity) {
                socketSendClient.sendMessage("PLAY|MOVE|" + command);
            } else {
                socketSendClient.sendMessage("BUILD|MOVE|" + command);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
