package at.drdracool.platformer;

public class GameCharacter {
    String connectionId;
    float locationX;
    float locationY;
    float width;
    float height;

    public GameCharacter() {

    }

    public GameCharacter(String connectionId, float locationX, float locationY, float width, float height) {
        this.connectionId = connectionId;
        this.locationX = locationX;
        this.locationY = locationY;
        this.width = width;
        this.height = height;
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

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

}
