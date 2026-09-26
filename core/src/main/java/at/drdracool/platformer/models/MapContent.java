package at.drdracool.platformer.models;

public class MapContent {
    String characterLocations;
    String movingBlockLocations;
    String staticBlockLocations;
    String doorLocations;
    String keyLocations;

    public MapContent(String characterLocations, String movingBlockLocations, String staticBlockLocations, String doorLocations, String keyLocations) {
        this.characterLocations = characterLocations;
        this.movingBlockLocations = movingBlockLocations;
        this.staticBlockLocations = staticBlockLocations;
        this.doorLocations = doorLocations;
        this.keyLocations = keyLocations;
    }

    public MapContent() {
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
}
