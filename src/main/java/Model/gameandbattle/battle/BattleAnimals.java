package Model.gameandbattle.battle;

import Model.gameandbattle.map.Map;

public class BattleAnimals {
    private String name;
    private int hitpoint;
    private int damage;
    private Integer blockLimits;
    public BattleAnimals(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHitpoint() {
        return hitpoint;
    }

    public void setHitpoint(int hitpoint) {
        this.hitpoint = hitpoint;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Integer getBlockLimits() {
        return blockLimits;
    }

    public void setBlockLimits(Integer blockLimits) {
        this.blockLimits = blockLimits;
    }
    public void move(Map map){

    }
}
