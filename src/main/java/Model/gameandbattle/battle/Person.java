package Model.gameandbattle.battle;

import Model.gameandbattle.map.Building;
import Model.gameandbattle.Government;

public class Person {

    private String name;
    private int hp;
    private Government government;
    private boolean isBusy;
    private Building building;

    public Person(String name, int hp, Government government, boolean isBusy, Building building) {
        this.name=name;
        this.hp = hp;
        this.government = government;
        this.isBusy = isBusy;
        this.building = building;
    }

    public String getName() {
        return name;
    }

    public Government getGovernment() {
        return government;
    }
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
