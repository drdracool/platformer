package at.drdracool.platformer;

import java.awt.*;

public class Block {

    float locationX;
    float locationY;
    Integer width;
    Integer height;
    Rectangle bounds = new Rectangle();

    public Block() {

    }

    public Block(float locationX, float locationY, Integer width, Integer height) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.width = width;
        this.height = height;
        this.bounds.width = width;
        this.bounds.height = height;
    }

    public float getLocationX() {
        return locationX;
    }

    public float getLocationY() {
        return locationY;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

}
