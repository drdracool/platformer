package at.drdracool.platformer;

public class Pair {
    public float x;
    public float y;

    public Pair(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean isEqualTo(Pair pair) {
        if (this.x != pair.x) {
            return false;
        } else if (this.y != pair.y) {
            return false;
        }
        return true;
    }
}
