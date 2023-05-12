package Model.gameandbattle.battle;

import Model.gameandbattle.Government;
import Model.gameandbattle.map.Building;
import Model.gameandbattle.map.Cell;
import Model.gameandbattle.map.Texture;

import java.util.ArrayList;

public class Catapult extends Troop{
    private boolean isFiery;
    private Cell occupiedCell;
    private boolean canMove;
    public Catapult(String name, int hp, Government government, boolean isBusy, Building building, int attackStrength, int speed,
                    int defenseStrength, int attackRange,int cost,ArrayList<Weapon> weapons) {
        super(name, hp, government, isBusy, building, attackStrength, speed, defenseStrength, attackRange,cost,weapons);
        isFiery = false;
        canMove = true;
    }


    public boolean isFiery() {
        return isFiery;
    }

    public void setFiery(boolean fiery) {
        isFiery = fiery;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean canMove () {
        return canMove;
    }


    public Cell getOccupiedCell() {
        return occupiedCell;
    }

    public void setOccupiedCell(Cell occupiedCell) {
        this.occupiedCell = occupiedCell;
    }
}
