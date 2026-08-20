package at.drdracool.platformer.interfaces;

import com.badlogic.gdx.Screen;

public interface BasicScreen extends Screen {
    void handleMessage(String category, String message);
}
