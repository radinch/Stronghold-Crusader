package Model.gameandbattle.stockpile;

public class Stockpile {
    private int wood;
    private int pitch;
    private int metal;
    private int stone;
    private int wheat;
    private int floor;
    private int hops;
    private int ale;

    public Stockpile(int wood, int pitch, int metal, int stone, int wheat, int floor, int hops, int ale) {
        this.wood = wood;
        this.pitch = pitch;
        this.metal = metal;
        this.stone = stone;
        this.wheat = wheat;
        this.floor = floor;
        this.hops = hops;
        this.ale = ale;
    }
    /////////////////////setters and getters

    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public int getMetal() {
        return metal;
    }

    public void setMetal(int metal) {
        this.metal = metal;
    }

    public int getStone() {
        return stone;
    }

    public void setStone(int stone) {
        this.stone = stone;
    }

    public int getWheat() {
        return wheat;
    }

    public void setWheat(int wheat) {
        this.wheat = wheat;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getHops() {
        return hops;
    }

    public void setHops(int hops) {
        this.hops = hops;
    }

    public int getAle() {
        return ale;
    }

    public void setAle(int ale) {
        this.ale = ale;
    }
    public int getByName(String name){
        if(name.equals("wood")) return wood;
        if(name.equals("pitch")) return pitch;
        if(name.equals("metal")) return metal;
        if(name.equals("stone")) return stone;
        if(name.equals("wheat")) return wheat;
        if(name.equals("floor")) return floor;
        if(name.equals("hops")) return hops;
        if(name.equals("ale")) return ale;
        return -1;
    }
    public void increaseByName(String name, int amount){
        if(name.equals("wood")) wood=wood+amount;
        if(name.equals("pitch")) pitch=pitch+amount;
        if(name.equals("metal")) metal=metal+amount;
        if(name.equals("stone"))stone=stone+amount;
        if(name.equals("wheat")) wheat+=amount;
        if(name.equals("floor")) floor+=amount;
        if(name.equals("hops")) hops+=amount;
        if(name.equals("ale")) ale+=amount;
    }
}
