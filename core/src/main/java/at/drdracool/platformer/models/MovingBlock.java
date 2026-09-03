package at.drdracool.platformer.models;

public class MovingBlock {

    Pair location;
    int width;
    int height;

    public MovingBlock() {

    }

    public MovingBlock(Pair location, int width, int height) {
        this.location = location;
        this.width = width;
        this.height = height;
    }

    public Pair getLocation() {
        return location;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
