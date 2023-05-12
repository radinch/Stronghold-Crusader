package Controller;

import Model.buildings.CastleBuilding;
import Model.buildings.OtherBuilding;
import Model.buildings.WeaponBuilding;
import Model.gameandbattle.Government;
import Model.gameandbattle.battle.Person;
import Model.gameandbattle.map.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenuController {
    private final Map map;
    private final Government government;

    public String dropBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));
        String type = matcher.group("type");
        if (!doIHaveAKingPlace() && !type.equals("Small stone gatehouse"))
            return "you have to place a small stone gatehouse first";
        else if (isCoordinateValid(x, y) != null)
            return isCoordinateValid(x, y);
        else if (!DataBank.getBuildingName().containsKey(type))
            return "invalid name of building";
        else if (isThereABuilding(x, y))
            return "can't put building on building";
        else if (!isThisCellMaterialValid(x, y, DataBank.getBuildingName().get(type)))
            return "can't put building on this texture";
        else if (!isResourceEnough(DataBank.getBuildingName().get(type), government))
            return "resource is not enough";
        else if (!isGoldEnough(DataBank.getBuildingName().get(type), government))
            return "gold is not enough";
        else if (!isWorkerEnough(DataBank.getBuildingName().get(type).getAmountOfWorkers()))
            return "Workers are not enough";
        else {
            if(type.equals("Stockpile") || type.equals("Food StockPile")) {
                if(!isStockPilePlaceOK(x,y,type))
                    return "Stockpiles should be next to each other";
            }
            Building tempBuilding = DataBank.getBuildingName().get(type);
            Building newBuilding = getNewBuilding(tempBuilding, x, y);
            map.getACell(x, y).setBuilding(newBuilding);
            map.getACell(x, y).setDetail('B');
            government.getBuildings().add(newBuilding);
            government.getStockpile().increaseByName(tempBuilding.getResourceRequired().getName(), (-1) * tempBuilding.getAmountOfResource());
            government.setCoin(government.getCoin() - tempBuilding.getGold());
            if (!newBuilding.getName().equals("Small stone gatehouse"))
                government.addWorker(newBuilding, newBuilding.getAmountOfWorkers());
            if (tempBuilding.getName().equals("Hovel")) government.setMaxPopulation(government.getMaxPopulation() + 8);
            return "success";
        }
    }

    public String dropWall(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));
        String type = matcher.group("type");
        if(DataBank.getWallByName(type) == null)
            return "invalid type of wall";
        if (isCoordinateValid(x, y) != null)
            return isCoordinateValid(x, y);
        if(!isCellProperForWall(x,y))
            return "you cant drop wall in this cell";
        if(!isStoneEnough(Objects.requireNonNull(DataBank.getWallByName(type)).getRequiredStone()))
            return "stone is not enough";
        Wall tempWall = DataBank.getWallByName(type);
        government.getStockpile().setStone(government.getStockpile().getStone() - Objects.requireNonNull(DataBank.getWallByName(type)).getRequiredStone());
        assert tempWall != null;
        map.getACell(x,y).setWall(new Wall(tempWall.getName(),tempWall.getHitpoint(),tempWall.getLength(),tempWall.getRequiredStone()));
        return "success";
    }

    public String dropGate(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));
        String direction = matcher.group("direction");
        if (isCoordinateValid(x, y) != null)
            return isCoordinateValid(x, y);
        if(!isCellProperForWall(x,y))
            return "you cant drop gate in this cell";
        if(!isStoneEnough(Objects.requireNonNull(DataBank.getWallByName("gate")).getRequiredStone()))
            return "stone is not enough";
        Wall tempWall = DataBank.getWallByName("gate");
        government.getStockpile().setStone(government.getStockpile().getStone() - Objects.requireNonNull(DataBank.getWallByName("gate")).getRequiredStone());
        assert tempWall != null;
        map.getACell(x,y).setWall(new Wall(tempWall.getName(),tempWall.getHitpoint(),tempWall.getLength(),tempWall.getRequiredStone()));
        map.getACell(x,y).getWall().setGate(true);
        map.getACell(x,y).getWall().setDirectionForGate(direction);
        return "success";
    }
    public String dropStair(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));
        if (isCoordinateValid(x, y) != null)
            return isCoordinateValid(x, y);
        if(!isCellProperForWall(x,y))
            return "you cant drop wall in this cell";
        if(isNearWall(x,y) == 0)
            return "there is no wall near here";
        if(!isStoneEnough(isNearWall(x,y)))
            return "stone is not enough";
        Stair tempStair = new Stair(isNearWall(x,y),isNearWall(x,y)*5);
        government.getStockpile().setStone(government.getStockpile().getStone() - isNearWall(x,y));
        changeAccessibilityOfWall(x,y);
        map.getACell(x,y).setStair(tempStair);
        return "success";
    }
    private boolean isCellProperForWall(int x, int y) {
        if(isThereABuilding(x,y))
            return false;
        if(map.getACell(x,y).getPeople().size() != 0)
            return false;
        if(!DataBank.getCastleBuildingTextures().contains(map.getACell(x,y).getTexture()))
            return false;
        if(isEnemyCloseToCell(x,y))
            return false;
        if(map.getACell(x,y).hasStair())
            return false;
        if(map.getACell(x,y).isDitch())
            return false;
        return !map.getACell(x, y).hasWall();
    }

    private boolean isEnemyCloseToCell(int x,int y) {
        int range = 5; //this is for rage
        for (int i = x-range; i <x+range ; i++) {
            for (int j = y-range; j < y+range ; j++) {
                if(x>= 0 && x< map.getSize() && y>=0 && y< map.getSize()) {
                    for (Person person : map.getACell(x, y).getPeople()) {
                        if(!person.getGovernment().equals(government))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public String selectBuilding(Matcher matcher, Scanner scanner) {
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));
        if (!isThereABuilding(x, y))
            return "there is no building at this location";
        else if (!doIHaveABuilding(x, y))
            return "this building is not for you";
        map.getACell(x, y).getBuilding().whenBuildingIsSelected(x, y, map, scanner);
        return null;
    }

    public boolean isStockPilePlaceOK(int x, int y,String StockPileType) {
        int StockPileX;
        int StockPileY;
        switch (StockPileType) {
            case "StockPile":
                if(government.getBuildingByName("StockPile") != null) {
                    StockPileX = government.getBuildingByName("StockPile").getOccupiedCell().getCellCoordinate(map).get(0);
                    StockPileY = government.getBuildingByName("StockPile").getOccupiedCell().getCellCoordinate(map).get(1);
                    if(StockPileX!= x - 1 && StockPileX != x+1)
                        return false;
                    if(StockPileY!= y - 1 && StockPileX != y+1)
                        return false;
                }
                break;
            case "Food StockPile":
                if(government.getBuildingByName("Food StockPile") != null) {
                    StockPileX = government.getBuildingByName("Food StockPile").getOccupiedCell().getCellCoordinate(map).get(0);
                    StockPileY = government.getBuildingByName("Food StockPile").getOccupiedCell().getCellCoordinate(map).get(1);
                    if(StockPileX!= x - 1 && StockPileX != x+1)
                        return false;
                    if(StockPileY!= y - 1 && StockPileX != y+1)
                        return false;
                }
                break;
        }
        return true;
    }

    private String isCoordinateValid(int x, int y) {
        if (x < 0 || y < 0)
            return "invalid x,y (less than zero)";
        else if (x >= map.getSize() || y >= map.getSize())
            return "invalid x,y (bigger that map size)";
        return null;
    }

    private boolean isThisCellMaterialValid(int x, int y, Building building) {
        for (Texture allowedTexture : building.getAllowedTextures()) {
            if (allowedTexture.getName().equals(map.getACell(x, y).getTexture().getName()))
                return true;
        }
        return false;
    }

    private boolean isThereABuilding(int x, int y) {
        return map.getACell(x, y).getBuilding() != null;
    }

    private boolean doIHaveABuilding(int x, int y) {
        return map.getACell(x, y).getBuilding().getGovernment().equals(government);
    }

    private boolean doIHaveAKingPlace() {
        return government.getBuildingByName("Small stone gatehouse") != null;
    }

    private boolean isResourceEnough(Building building, Government government) {
        String resourceName = building.getResourceRequired().getName();
        return government.getStockpile().getByName(resourceName) >= building.getAmountOfResource();
    }

    private boolean isGoldEnough(Building building, Government government) {
        return building.getGold() <= government.getCoin();
    }

    private boolean isStoneEnough(int number) {
        return number <= government.getStockpile().getStone();
    }

    public BuildingMenuController(Map map, Government government) {
        this.map = map;
        this.government = government;
    }

    private Building getNewBuilding(Building tempBuilding, int x, int y) {
        if (tempBuilding instanceof CastleBuilding)
            return new CastleBuilding(government, tempBuilding.getGold(), tempBuilding.getName(), tempBuilding.getHitpoint(),
                    tempBuilding.getResourceRequired(), tempBuilding.getAmountOfResource(), tempBuilding.getAmountOfWorkers(), tempBuilding.getAllowedTextures(),
                    map.getACell(x, y), ((CastleBuilding) tempBuilding).getCapacity(), ((CastleBuilding) tempBuilding).getFireRange(),
                    ((CastleBuilding) tempBuilding).getDefendRange(), ((CastleBuilding) tempBuilding).getCost(), ((CastleBuilding) tempBuilding).getAmountOfDecreaseInSpeed(),
                    ((CastleBuilding) tempBuilding).getDamage(), ((CastleBuilding) tempBuilding).getRate());
        else if (tempBuilding instanceof WeaponBuilding)
            return new WeaponBuilding(government, tempBuilding.getGold(), tempBuilding.getName(), tempBuilding.getHitpoint(),
                    tempBuilding.getResourceRequired(), tempBuilding.getAmountOfResource(), tempBuilding.getAmountOfWorkers(), tempBuilding.getAllowedTextures(),
                    map.getACell(x, y), ((WeaponBuilding) tempBuilding).getConsumableResources(), ((WeaponBuilding) tempBuilding).getProductionRate());
        else
            return new OtherBuilding(government, tempBuilding.getGold(), tempBuilding.getName(), tempBuilding.getHitpoint(),
                    tempBuilding.getResourceRequired(), tempBuilding.getAmountOfResource(), tempBuilding.getAmountOfWorkers(), tempBuilding.getAllowedTextures(),
                    map.getACell(x, y), ((OtherBuilding) tempBuilding).getRate(), ((OtherBuilding) tempBuilding).getCapacity());
    }

    private boolean isWorkerEnough(int amountNeeded) {
        int count = 0;
        for (Person person : government.getPeople()) {
            if (!person.isBusy())
                count++;
        }
        return count >= amountNeeded;
    }

    private int isNearWall(int x,int y)
    {
        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if(!(i < 0 || i > map.getSize() || j < 0 || j > map.getSize()))
                {
                    if(!(i != x && j != y)) {
                        if (map.getACell(i, j).hasWall())
                            return map.getACell(i,j).getWall().getLength();
                    }
                }
            }
        }
        return 0;
    }

    private void changeAccessibilityOfWall(int x,int y)
    {
        if(!(x < 0 || x > map.getSize() || y < 0 || y > map.getSize()))
        {
            if(map.getACell(x,y).hasWall()) {
                map.getACell(x, y).getWall().setAccessible(true);
                changeAccessibilityOfWall(x - 1, y);
                changeAccessibilityOfWall(x, y - 1);
                changeAccessibilityOfWall(x + 1, y);
                changeAccessibilityOfWall(x, y + 1);
            }
        }
    }
}
