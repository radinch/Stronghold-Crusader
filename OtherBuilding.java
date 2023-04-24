package Model.buildings;

import Model.gameandbattle.map.Building;
import Model.gameandbattle.Government;
import Model.gameandbattle.map.Cell;
import Model.gameandbattle.map.Map;
import Model.gameandbattle.map.Texture;
import Model.gameandbattle.stockpile.Resource;

import java.util.ArrayList;

public class OtherBuilding extends Building {
    private Integer rate;
    private Integer capacity;


    public OtherBuilding(Government government, int gold, String name, int hitpoint, Resource resourceRequired,int amountOfResource, int amountOfWorkers, ArrayList<Texture> textures, ArrayList<Cell> occupiedCells, Integer rate, Integer capacity) {
        super(government, gold, name, hitpoint, resourceRequired,amountOfResource, amountOfWorkers,textures,occupiedCells);
        this.rate = rate;
        this.capacity = capacity;
    }

    @Override
    public void whenBuildingIsSelected(int x, int y, Map map) {
        super.whenBuildingIsSelected(x, y, map);
    }

    @Override
    public void makeAffect(int x, int y, Map map) {
        super.makeAffect(x, y, map);
    }
}
