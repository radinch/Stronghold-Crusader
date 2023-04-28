package Model.buildings;

import Model.gameandbattle.map.Building;
import Model.gameandbattle.Government;
import Model.gameandbattle.map.Cell;
import Model.gameandbattle.map.Map;
import Model.gameandbattle.map.Texture;
import Model.gameandbattle.stockpile.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CastleBuilding extends Building {
    private Integer capacity;
    private Integer fireRange;
    private Integer defendRange;
    private HashMap<String, Integer> cost;   //if size==2 first number is cost of ladderMan
    private Integer amountOfDecreaseInSpeed;
    private Integer damage;
    private Integer rate;
    public CastleBuilding(Government government, int gold, String name, int hitpoint, Resource resourceRequired, int amountOfResource, int amountOfWorkers, ArrayList<Texture> textures, ArrayList<Cell> occupiedCells, Integer capacity,
                          Integer fireRange, Integer defendRange, HashMap<String, Integer> cost, Integer amountOfDecreaseInSpeed, Integer damage, Integer rate) {
        super(government, gold, name, hitpoint, resourceRequired,amountOfResource, amountOfWorkers,textures,occupiedCells);
        this.capacity = capacity;
        this.fireRange = fireRange;
        this.defendRange = defendRange;
        this.cost = cost;
        this.amountOfDecreaseInSpeed = amountOfDecreaseInSpeed;
        this.damage = damage;
        this.rate = rate;
    }

    @Override
    public void makeAffect(int x, int y, Map map) {
        super.makeAffect(x, y, map);
    }

    @Override
    public void whenBuildingIsSelected(int x, int y, Map map) {
        super.whenBuildingIsSelected(x, y, map);
    }

    private void freeHorse(int x, int y){

    }
    public void freeDogs(int x,int y){

    }
}
