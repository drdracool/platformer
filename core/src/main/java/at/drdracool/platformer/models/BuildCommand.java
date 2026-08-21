package at.drdracool.platformer.models;

public class BuildCommand {
    int input;
    float locationX;
    float locationY;

    public BuildCommand(int input, float locationX, float locationY) {
        this.input = input;
        this.locationX = locationX;
        this.locationY = locationY;
    }
}
