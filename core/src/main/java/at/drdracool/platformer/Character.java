package at.drdracool.platformer;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Character {
    String connectionId;
    float locationX;
    float locationY;
    String assetName;

    public Character(String connectionId, float locationX, float locationY, String assetName) {
        this.connectionId = connectionId;
        this.locationX = locationX;
        this.locationY = locationY;
        this.assetName = assetName;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public float getLocationX() {
        return locationX;
    }

    public void setLocationX(float locationX) {
        this.locationX = locationX;
    }

    public float getLocationY() {
        return locationY;
    }

    public void setLocationY(float locationY) {
        this.locationY = locationY;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

}
