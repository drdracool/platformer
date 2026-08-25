package at.drdracool.platformer.models;

public class Block {

    Pair location;
    Integer width;
    Integer height;
    boolean isMoving;

    public Block() {

    }

    public Block(Pair location, Integer width, Integer height, boolean isMoving) {
        this.location = location;
        this.width = width;
        this.height = height;
        this.isMoving = isMoving;
    }

    public Pair getLocation() {
        return location;
    }

    public void setLocation(Pair location) {
        this.location = location;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public boolean isMoving() {return isMoving;}


}
