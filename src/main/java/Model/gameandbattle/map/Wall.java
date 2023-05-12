package Model.gameandbattle.map;

import java.util.ArrayList;
import java.util.List;

public class Wall {

    private String name;
    private int hitpoint;
    private int length;// 10 , 15 , 20
    private boolean isGate;
    private String directionForGate;
    private int requiredStone; //1 for low wall 2 for normal wall 3 for crenelation
    private boolean isAccessible;

    public Wall(String name,int hitpoint, int length, int requiredStone) {
        this.hitpoint = hitpoint;
        this.length = length;
        this.requiredStone = requiredStone;
        this.name = name;
        isGate = false;
        isAccessible = false;
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

    public boolean isGate() {
        return isGate;
    }

    public void setGate(boolean gate) {
        isGate = gate;
    }

    public int getRequiredStone() {
        return requiredStone;
    }

    public String getName() {
        return name;
    }

    public String getDirectionForGate() {
        return directionForGate;
    }

    public void setDirectionForGate(String directionForGate) {
        this.directionForGate = directionForGate;
    }

    public void setAccessible(boolean accessible) {
        isAccessible = accessible;
    }

    public boolean isAccessible() {
        return isAccessible;
    }
}
