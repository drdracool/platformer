package at.drdracool.platformer.models;

public class StaticBlock {
    Pair location;
    int height;
    int width;
    float degree;

    public StaticBlock(){}

    public StaticBlock(Pair location, int width, int height, float degree) {
        this.height = height;
        this.location = location;
        this.width = width;
        this.degree = degree;
    }

    public Pair getLocation() {
        return location;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public float getDegree() {
        return degree;
    }
}
