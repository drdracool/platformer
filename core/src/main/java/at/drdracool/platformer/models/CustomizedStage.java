package at.drdracool.platformer.models;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CustomizedStage extends Stage {
    public CustomizedStage(ScreenViewport screenViewport) {
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.unfocusAll();
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
