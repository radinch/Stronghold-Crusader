package Model.gameandbattle.battle;

import Model.gameandbattle.Government;
import Model.gameandbattle.map.Building;
import Model.gameandbattle.map.Cell;
import Model.gameandbattle.map.Texture;

import java.util.ArrayList;

public class Catapult extends Troop{
    private boolean isFiery;
    private int EngineerNeeded;
    private boolean hasBalanceWeight;
    private Cell occupiedCell;
    public Catapult(String name, int hp, Government government, boolean isBusy, Building building, int attackStrength, int speed, int defenseStrength, ArrayList<Texture> textures, int attackRange,int cost,ArrayList<Weapon> weapons) {
        super(name, hp, government, isBusy, building, attackStrength, speed, defenseStrength, attackRange,cost,weapons);
    }


    public boolean isFiery() {
        return isFiery;
    }

    public int getEngineerNeeded() {
        return EngineerNeeded;
    }

    public boolean isHasBalanceWeight() {
        return hasBalanceWeight;
    }

    public void setHasBalanceWeight(boolean hasBalanceWeight) {
        this.hasBalanceWeight = hasBalanceWeight;
    }

    public Cell getOccupiedCell() {
        return occupiedCell;
    }

    public void setOccupiedCell(Cell occupiedCell) {
        this.occupiedCell = occupiedCell;
    }
}
