package at.drdracool.platformer.inputHandlers;

import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.HashMap;

public class UIManager {
    HashMap<Integer, Table> keyTableMap;

    public UIManager (HashMap<Integer, Table> keyTableMap) {
        this.keyTableMap = keyTableMap;
    }

    public HashMap<Integer, Table> getKeyTableMap() {
        return keyTableMap;
    }

    public void handleHotKey(int keyCode, boolean isKeyDown) {
        System.out.println("keyTableMap size:" + keyTableMap.size());
        if (!keyTableMap.containsKey(keyCode)) return;
        Table table = keyTableMap.get(keyCode);
        ((ImageTextButton) table).setDisabled(isKeyDown);
        System.out.println("button disabled status: " + ((ImageTextButton) table).isDisabled());
    }
}
