package at.drdracool.platformer.models;

import java.util.List;

public class MapDraft {
    String name;
    List<StaticBlockDraft> staticBlocks;
    List<MovingBlockDraft> movingBlocks;

    public MapDraft(String name, List<StaticBlockDraft> staticBlocks, List<MovingBlockDraft> movingBlocks) {
        this.name = name;
        this.staticBlocks = staticBlocks;
        this.movingBlocks = movingBlocks;
    }


}
