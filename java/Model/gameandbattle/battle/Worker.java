package Model.gameandbattle.battle;

import Model.gameandbattle.map.Building;
import Model.gameandbattle.Government;

public class Worker extends Person{
    private int workSpeed;

    public Worker(String name, int hp, Government government, boolean isBusy, Building building, int workSpeed) {
        super(name,hp, government, isBusy, building);
        this.workSpeed = workSpeed;
    }
    public int calculateWorkSpeed(){
        return 0;
    }
}
