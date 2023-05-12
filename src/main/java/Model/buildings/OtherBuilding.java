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
        super(government, gold, name, hitpoint, resourceRequired, amountOfResource, amountOfWorkers, textures, occupiedCell);
        this.rate = rate;
        this.capacity = capacity;
    }

    @Override
    public void whenBuildingIsSelected(int x, int y, Map map, Scanner scanner) {
        super.whenBuildingIsSelected(x, y, map, scanner);
    }

    @Override
    public void makeAffect(int x, int y, Map map) {
        super.makeAffect(x, y, map);
        extract();
        processFood();
    }

    private void extract() {
        //todo get tree for woodcutter
        switch (getName()) {
            case "Iron mine" ->
                    this.getGovernment().getStockpile().setMetal(this.getGovernment().getStockpile().getMetal() + getStockpileRate(getRate()));
            case "Quarry" ->
                    this.getGovernment().getStockpile().setStone(this.getGovernment().getStockpile().getStone() + getStockpileRate(getRate()));
            case "Woodcutter" ->
                    this.getGovernment().getStockpile().setWood(this.getGovernment().getStockpile().getWood() + getStockpileRate(getRate()));
            case "Apple garden" -> {
                this.getGovernment().getGranary().setApple(getGovernment().getGranary().getApple() + getGranaryRate(rate));
                getGovernment().getFoods()[2] += getGranaryRate(rate);
            }
            case "Hop garden" ->
                    this.getGovernment().getStockpile().setHops(getGovernment().getStockpile().getHops() + getStockpileRate(getRate()));
            case "Wheat garden" ->
                    this.getGovernment().getStockpile().setWheat(getGovernment().getStockpile().getWheat() + getStockpileRate(getRate()));
            case "Dairy products" -> {
                this.getGovernment().getGranary().setCheese(getGovernment().getGranary().getCheese() + getGranaryRate(rate));
                this.getGovernment().getWeapons()[6] += rate;
                getGovernment().getFoods()[1] += getGranaryRate(rate);
            }
            case "hunting post" -> {
                this.getGovernment().getGranary().setMeat(getGovernment().getGranary().getMeat() + getGranaryRate(rate));
                getGovernment().getFoods()[0] += getGranaryRate(rate);
            }
            case "Pitch rig" -> {
                this.getGovernment().getStockpile().setPitch(getGovernment().getStockpile().getPitch() + getStockpileRate(getRate()));
            }
            case "oil smelter" -> {
                this.getGovernment().getStockpile().setOil(getGovernment().getStockpile().getOil() + getStockpileRate(getRate()));
            }
        }
    }

    public double getGranaryRate(int rate) {
        if(getGovernment().getGranary().getTotalFood() + rate > getGovernment().getMaxFoodCapacity())
            return getGovernment().getGranary().getTotalFood() - getGovernment().getMaxFoodCapacity();
        return rate;
    }

    public int getStockpileRate(int rate)
    {
        if(getGovernment().getStockpile().getTotalResource() + rate >= getGovernment().getMaxResourceCapacity())
            return getGovernment().getStockpile().getTotalResource() - getGovernment().getMaxResourceCapacity();
        else
            return rate;
        // + -> more than allowed
        // - -> OK
    }

    private void processFood() {
        //todo maximum food and resource
        switch (getName()) {
            case "Mill" -> {
                if (this.getGovernment().getStockpile().getWheat() - getRate() >= 0) {
                    this.getGovernment().getStockpile().setFloor(this.getGovernment().getStockpile().getFloor() + getStockpileRate(getRate()));
                    this.getGovernment().getStockpile().setWheat(this.getGovernment().getStockpile().getWheat() - getStockpileRate(getRate()));
                }
            }
            case "Bakery" -> {
                if (this.getGovernment().getStockpile().getFloor() - getRate() >= 0) {
                    this.getGovernment().getGranary().setBread(this.getGovernment().getGranary().getBread() + getGranaryRate(rate));
                    this.getGovernment().getFoods()[3] += getGranaryRate(rate);
                    this.getGovernment().getStockpile().setFloor(this.getGovernment().getStockpile().getFloor() - getStockpileRate(getRate()));
                }
            }
            case "Brewery" -> {
                if (this.getGovernment().getStockpile().getHops() - getRate() >= 0) {
                    this.getGovernment().getStockpile().setAle(this.getGovernment().getStockpile().getAle() + getStockpileRate(getRate()));
                    this.getGovernment().getStockpile().setHops(this.getGovernment().getStockpile().getHops() - getStockpileRate(getRate()));
                }
            }
        }
    }
}
