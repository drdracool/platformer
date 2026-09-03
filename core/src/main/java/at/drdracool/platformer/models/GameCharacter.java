package at.drdracool.platformer.models;

public class GameCharacter {
    String connectionId;
    Pair location;
    float radius;

    public GameCharacter() {

    }

    public GameCharacter(String connectionId, Pair location, float radius) {
        this.connectionId = connectionId;
        this.location = location;
        this.radius = radius;
    }

    public float getLocationX() {
        return location.x;
    }

    public float getLocationY() {
        return location.y;
    }

    public float getRadius() {
        return radius;
    }
}
