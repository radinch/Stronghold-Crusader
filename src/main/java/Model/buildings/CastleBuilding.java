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
import java.util.Scanner;

public class CastleBuilding extends Building {
    private Integer capacity;
    private Integer fireRange;
    private Integer defendRange;
    private HashMap<String, Integer> cost;   //if size==2 first number is cost of ladderMan
    private Integer amountOfDecreaseInSpeed;
    private Integer damage;
    private Integer rate;
    public CastleBuilding(Government government, double gold, String name, int hitpoint, Resource resourceRequired, int amountOfResource, int amountOfWorkers, ArrayList<Texture> textures, ArrayList<Cell> occupiedCells, Integer capacity,
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
    public void whenBuildingIsSelected(int x, int y, Map map, Scanner scanner) {
        super.whenBuildingIsSelected(x, y, map,scanner);
    }

    private void freeHorse(int x, int y){

    }
    public void freeDogs(int x,int y){

    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getFireRange() {
        return fireRange;
    }

    public void setFireRange(Integer fireRange) {
        this.fireRange = fireRange;
    }

    public Integer getDefendRange() {
        return defendRange;
    }

    public void setDefendRange(Integer defendRange) {
        this.defendRange = defendRange;
    }

    public HashMap<String, Integer> getCost() {
        return cost;
    }

    public void setCost(HashMap<String, Integer> cost) {
        this.cost = cost;
    }

    public Integer getAmountOfDecreaseInSpeed() {
        return amountOfDecreaseInSpeed;
    }

    public void setAmountOfDecreaseInSpeed(Integer amountOfDecreaseInSpeed) {
        this.amountOfDecreaseInSpeed = amountOfDecreaseInSpeed;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getCapacity() {
        return capacity;
    }
}
