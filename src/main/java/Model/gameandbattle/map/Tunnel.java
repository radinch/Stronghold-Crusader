package Model.gameandbattle.map;

public class Tunnel {
    private int start;
    private int finish;
    private int length;
    private int capacity;
    private int healthPoint;

    public Tunnel(int start, int finish, int length, int capacity, int healthPoint) {
        this.start = start;
        this.finish = finish;
        this.length = length;
        this.capacity = capacity;
        this.healthPoint = healthPoint;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }
}
