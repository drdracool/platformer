package at.drdracool.platformer.inputHandlers;

import at.drdracool.platformer.services.BuildService;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class BuildInputHandler extends InputAdapter {
    BuildService buildService;

    public BuildInputHandler(BuildService buildService) {
        this.buildService = buildService;
    }

    @Override
    public boolean keyDown(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) {
            case Input.Keys.LEFT:
                System.out.println("Pressed Left");
                buildService.updateMovementIntention("LEFT");
                keyProcessed = true;
                break;
            case Input.Keys.RIGHT:
                System.out.println("Pressed Right");
                buildService.updateMovementIntention("RIGHT");
                keyProcessed = true;
                break;
            case Input.Keys.UP:
                System.out.println("Pressed Up");
                buildService.updateMovementIntention("UP");
                keyProcessed = true;
                break;
            case Input.Keys.DOWN:
                System.out.println("Pressed Up");
                buildService.updateMovementIntention("DOWN");
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
                buildService.updateMovementIntention("STOP_LEFT");
                keyProcessed = true;
                break;
            case Input.Keys.RIGHT:
                System.out.println("Finished pressing right");
                buildService.updateMovementIntention("STOP_RIGHT");
                keyProcessed = true;
                break;
            case Input.Keys.UP:
                System.out.println("Finished pressing up");
                buildService.updateMovementIntention("STOP_UP");
                keyProcessed = true;
                break;
            case Input.Keys.DOWN:
                System.out.println("Finished pressing up");
                buildService.updateMovementIntention("STOP_DOWN");
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }
}
