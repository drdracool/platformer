package at.drdracool.platformer;

public class MovingBlock {
    Pair startLocation;
    Pair endLocation;

    public MovingBlock(Pair startLocation, Pair endLocation) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public Pair getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Pair startLocation) {
        this.startLocation = startLocation;
    }

    public Pair getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Pair endLocation) {
        this.endLocation = endLocation;
    }
}
