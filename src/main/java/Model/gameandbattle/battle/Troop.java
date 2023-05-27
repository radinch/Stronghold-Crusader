package Model.gameandbattle.battle;

import Model.gameandbattle.map.Building;
import Model.gameandbattle.Government;
import Model.gameandbattle.map.Texture;

import java.util.ArrayList;

public class Troop extends Person{
    private int state; //1 for defensive 2 for standing 3 for offensive
    private int attackRange;
    private int attackStrength;
    private final ArrayList<Weapon> weapons;
    private int speed;
    private int defenseStrength;
    private ArrayList<Texture> notAllowedTextures;
    private final int cost;
    private boolean isVisible;
    private boolean canClimb;
    private boolean canThrowLadder;
    private boolean canDigDitch;
    private boolean hasOil;


    public Troop(String name, int hp, Government government, boolean isBusy, Building building, int attackStrength, int speed, int defenseStrength, int attackRange,int cost,ArrayList<Weapon> weapons) {
        super(name,hp, government, isBusy, building);
        this.attackStrength = attackStrength;
        this.speed = speed;
        this.defenseStrength = defenseStrength;
        this.attackRange=attackRange;
        this.cost = cost;
        this.weapons= weapons;
        hasOil = false;
        state = 2;
    }


    public int getAttackStrength() {
        return attackStrength;
    }

    public void setAttackStrength(int attackStrength) {
        this.attackStrength = attackStrength;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDefenseStrength() {
        return defenseStrength;
    }

    public void setDefenseStrength(int defenseStrength) {
        this.defenseStrength = defenseStrength;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }



    public ArrayList<Texture> getNotAllowedTextures() {
        return notAllowedTextures;
    }

    public void setNotAllowedTextures(ArrayList<Texture> notAllowedTextures) {
        this.notAllowedTextures = notAllowedTextures;
    }

    public void move(int x, int y) {

    }

    public int getCost() {
        return cost;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public boolean hasOil() {
        return hasOil;
    }

    public void equipWithOil() {
        hasOil = true;
    }

    public void pourOil() {
        hasOil = false;
    }
}
