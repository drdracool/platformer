package at.drdracool.platformer;

public class Character {
    String id;
    float locationX;
    float locationY;
    String assetName;

    public Character(String id, float locationX, float locationY, String assetName) {
        this.id = id;
        this.locationX = locationX;
        this.locationY = locationY;
        this.assetName = assetName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
