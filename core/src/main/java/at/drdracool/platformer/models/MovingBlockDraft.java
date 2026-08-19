package at.drdracool.platformer.models;

public class MovingBlockDraft {
    Pair startLocation;
    Pair endLocation;

    public MovingBlockDraft(Pair startLocation, Pair endLocation) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }
}
