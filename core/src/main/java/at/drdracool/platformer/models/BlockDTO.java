package at.drdracool.platformer.models;

public class BlockDTO {
    Pair location;
    Pair size;
    float degree;

    public BlockDTO(Pair location, Pair size, float degree) {
        this.location = location;
        this.size = size;
        this.degree = degree;
    }

    public BlockDTO() {
    }

    public Pair getSize() {
        return size;
    }

    public float getDegree() {
        return degree;
    }

    public Pair getLocation() {
        return location;
    }
}
