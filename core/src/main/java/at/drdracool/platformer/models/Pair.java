package at.drdracool.platformer.models;

public class Pair {
    public int x;
    public int y;

    public Pair() {

    }

    public Pair(int x, int y) {
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

    public void updateX(int delta) {
        x += delta;
    }

    public void updateY(int delta) {
        y += delta;
    }
}
