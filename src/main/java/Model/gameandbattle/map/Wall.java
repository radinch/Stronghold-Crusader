package Model.gameandbattle.map;

public class Wall {
    private int hitpoint;
    private int length;
    private int price;
    private boolean isGate;
    public Wall(int hitpoint,boolean isGate){

    }

    public int getHitpoint() {
        return hitpoint;
    }

    public void setHitpoint(int hitpoint) {
        this.hitpoint = hitpoint;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
