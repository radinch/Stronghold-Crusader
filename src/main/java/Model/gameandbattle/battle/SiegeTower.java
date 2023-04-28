package Model.gameandbattle.battle;

import Model.gameandbattle.Government;
import Model.gameandbattle.map.Building;
import Model.gameandbattle.map.Cell;
import Model.gameandbattle.map.Texture;
import Model.gameandbattle.map.Wall;

import java.util.ArrayList;

public class SiegeTower extends Troop{
    private int EngineerNeeded;
    private Wall attachWall;
    private boolean isStair;
    private ArrayList<Cell> occupiedCells;
    public SiegeTower(String name, int hp, Government government, boolean isBusy, Building building, int attackStrength
            , int speed, int defenseStrength, ArrayList<Texture> textures, int attackRange) {
        super(name, hp, government, isBusy, building, attackStrength, speed, defenseStrength, textures, attackRange);
    }

    @Override
    public void move(int x, int y) {

    }

    public int getEngineerNeeded() {
        return EngineerNeeded;
    }

    public void setEngineerNeeded(int engineerNeeded) {
        EngineerNeeded = engineerNeeded;
    }

    public Wall getAttachWall() {
        return attachWall;
    }

    public void setAttachWall(Wall attachWall) {
        this.attachWall = attachWall;
    }

    public boolean isStair() {
        return isStair;
    }

    public void setStair(boolean stair) {
        isStair = stair;
    }

    public ArrayList<Cell> getOccupiedCells() {
        return occupiedCells;
    }

    public void setOccupiedCells(ArrayList<Cell> occupiedCells) {
        this.occupiedCells = occupiedCells;
    }
}
