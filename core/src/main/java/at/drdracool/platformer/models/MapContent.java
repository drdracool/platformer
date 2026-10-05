package at.drdracool.platformer.models;

public class MapContent {
    String name;
    String characterLocations;
    String movingBlockLocations;
    String staticBlockLocations;
    String doorLocations;
    String keyLocations;
    String exitLocation;

    public MapContent(String name, String characterLocations, String movingBlockLocations, String staticBlockLocations, String doorLocations, String keyLocations, String exitLocation) {
        this.name = name;
        this.characterLocations = characterLocations;
        this.movingBlockLocations = movingBlockLocations;
        this.staticBlockLocations = staticBlockLocations;
        this.doorLocations = doorLocations;
        this.keyLocations = keyLocations;
        this.exitLocation = exitLocation;
    }

    public MapContent() {
    }

    public String getName() {
        return name;
    }

    public String getCharacterLocations() {
        return characterLocations;
    }

    public String getMovingBlockLocations() {
        return movingBlockLocations;
    }

    public String getStaticBlockLocations() {
        return staticBlockLocations;
    }

    public String getDoorLocations() {
        return doorLocations;
    }

    public String getKeyLocations() {
        return keyLocations;
    }

    public String getExitLocation() {
        return exitLocation;
    }

}
