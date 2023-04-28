package Model.gameandbattle.battle;

import Model.gameandbattle.map.Building;
import Model.gameandbattle.Government;
import Model.gameandbattle.map.Texture;

import java.util.ArrayList;

public class Troop extends Person{
    private String state;
    private int attackRange;
    private int attackStrength;
    private int speed;
    private int defenseStrength;
    private ArrayList<Texture> notAllowedTextures;
    private boolean isVisible;
    private boolean canClimb;
    private boolean canThrowLadder;
    private boolean canDigDitch;

    public Troop(String name, int hp, Government government, boolean isBusy, Building building, int attackStrength, int speed, int defenseStrength, ArrayList<Texture> textures, int attackRange) {
        super(name,hp, government, isBusy, building);
        this.attackStrength = attackStrength;
        this.speed = speed;
        this.defenseStrength = defenseStrength;
        notAllowedTextures=textures;
        this.attackRange=attackRange;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
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


}
