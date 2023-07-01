package Model.gameandbattle.map;

public class Stair {
    private int level;
    private int hitpoint;

    public Stair(int level, int hitpoint) {
        this.level = level;
        this.hitpoint = hitpoint;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHitpoint() {
        return hitpoint;
    }

    public void setHitpoint(int hitpoint) {
        this.hitpoint = hitpoint;
    }
}
