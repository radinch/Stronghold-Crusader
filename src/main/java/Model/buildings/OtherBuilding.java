package Model.buildings;

import Model.gameandbattle.map.Building;
import Model.gameandbattle.Government;
import Model.gameandbattle.map.Cell;
import Model.gameandbattle.map.Map;
import Model.gameandbattle.map.Texture;
import Model.gameandbattle.stockpile.Resource;

import java.util.ArrayList;
import java.util.Scanner;

public class OtherBuilding extends Building {
    private Integer rate;
    private Integer capacity;


    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public OtherBuilding(Government government, double gold, String name, int hitpoint, Resource resourceRequired, int amountOfResource, int amountOfWorkers, ArrayList<Texture> textures, Cell occupiedCell, Integer rate, Integer capacity) {
        super(government, gold, name, hitpoint, resourceRequired,amountOfResource, amountOfWorkers,textures,occupiedCell);
        this.rate = rate;
        this.capacity = capacity;
    }

    @Override
    public void whenBuildingIsSelected(int x, int y, Map map, Scanner scanner) {
        super.whenBuildingIsSelected(x, y, map,scanner);
    }

    @Override
    public void makeAffect(int x, int y, Map map) {
        super.makeAffect(x, y, map);
    }
}
