package at.drdracool.platformer;

import java.awt.*;

public class Block {

    Pair location;
    Integer width;
    Integer height;

    public Block() {

    }

    public Block(Pair location, Integer width, Integer height) {
        this.location = location;
        this.width = width;
        this.height = height;
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


}
