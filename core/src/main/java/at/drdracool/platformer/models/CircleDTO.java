package at.drdracool.platformer.models;

public class CircleDTO {
    Pair location;
    float radius;

    public CircleDTO(Pair location, float radius) {
        this.location = location;
        this.radius = radius;
    }

    public CircleDTO() {
    }

    public Pair getLocation() {
        return location;
    }

    public float getRadius() {
        return radius;
    }
}
