package Controller;

import Model.buildings.CastleBuilding;
import Model.buildings.OtherBuilding;
import Model.buildings.WeaponBuilding;
import Model.gameandbattle.Government;
import Model.gameandbattle.map.Building;
import Model.gameandbattle.map.Map;
import Model.gameandbattle.map.Texture;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenuController {
    private Map map;
    private Government government;
    public String dropBuilding(Matcher matcher){
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));
        String type = matcher.group("type");
        if(!doIHaveAKingPlace() && !type.equals("Small stone gatehouse")) return "you have to place a small stone gatehouse first";
        else if(isCoordinateValid(x,y) != null)
            return isCoordinateValid(x,y);
        else if(!DataBank.getBuildingName().containsKey(type))
            return "invalid name of building";
        else if(isThereABuilding(x,y))
            return "can't put building on building";
        else if(!isThisCellMaterialValid(x,y,DataBank.getBuildingName().get(type)))
            return "can't put building on this texture";
        else if(!isResourceEnough(DataBank.getBuildingName().get(type),government))
            return "resource is not enough";
        else if(isGoldEnough(DataBank.getBuildingName().get(type),government ))
            return "gold is not enough";
        else {
            Building tempBuilding = DataBank.getBuildingName().get(type);
            map.getACell(x,y).setBuilding(getNewBuilding(tempBuilding));
            map.getACell(x,y).setDetail('B');
            government.getStockpile().increaseByName(tempBuilding.getResourceRequired().getName(),(-1)*tempBuilding.getAmountOfResource());
            government.setCoin(government.getCoin() - tempBuilding.getGold());
            if(tempBuilding.getName().equals("Hovel"))government.setMaxPopulation(government.getMaxPopulation()+8);
            return "success";
        }
    }
    public String selectBuilding(Matcher matcher, Scanner scanner){
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));
        if(!isThereABuilding(x,y))
            return "there is no building at this location";
        else if(!doIHaveABuilding(x,y))
            return "this building is not for you";
        map.getACell(x,y).getBuilding().whenBuildingIsSelected(x,y,map,scanner);
        return  null;
    }
    public String createUnit(Matcher matcher){
        return null;
    }
    public boolean isStockPilePlaceOK(int x ,int y){
        return false;
    }
    private String isCoordinateValid(int x,int y){
        if(x < 0 || y < 0)
            return "invalid x,y (less than zero)";
        else if(x >= map.getSize() || y >= map.getSize())
            return "invalid x,y (bigger that map size)";
        return null;
    }
    private boolean isThisCellMaterialValid(int x,int y,Building building){
        for (Texture allowedTexture : building.getAllowedTextures()) {
            if(allowedTexture.getName().equals(map.getACell(x,y).getTexture().getName()))
                return true;
        }
        return false;
    }
    private boolean isThereABuilding(int x,int y){
        return map.getACell(x,y).getBuilding() != null;
    }

    private boolean doIHaveABuilding(int x,int y) {
        return map.getACell(x,y).getBuilding().getGovernment().equals(government);
    }
    private boolean doIHaveAKingPlace(){
        if(government.getBuildingByName("Small stone gatehouse")!=null) return true;
        return false;
    }

    private boolean isResourceEnough(Building building, Government government) {
        String resourceName = building.getResourceRequired().getName();
        return government.getStockpile().getByName(resourceName) >= building.getAmountOfResource();
    }

    private boolean isGoldEnough(Building building, Government government) {
        return building.getGold() <= government.getCoin();
    }
    private boolean isResourcesEnough(int x,int y){
        return false;
    }
    private boolean isThereEnoughPeople(int number){
        return false;
    }
    private boolean isUnitTypeValid(int number){
        return false;
    }
    private boolean isStoneEnough(int number){
        return false;
    }
    public BuildingMenuController(Map map, Government government) {
        this.map = map;
        this.government = government;
    }
    private Building getNewBuilding(Building tempBuilding) {
        if(tempBuilding instanceof CastleBuilding)
            return new CastleBuilding(government,tempBuilding.getGold(), tempBuilding.getName(), tempBuilding.getHitpoint(),
                    tempBuilding.getResourceRequired(),tempBuilding.getAmountOfResource(),tempBuilding.getAmountOfWorkers(),tempBuilding.getAllowedTextures(),
                    tempBuilding.getOccupiedCells(),((CastleBuilding) tempBuilding).getCapacity(), ((CastleBuilding) tempBuilding).getFireRange(),
                    ((CastleBuilding) tempBuilding).getDefendRange(), ((CastleBuilding) tempBuilding).getCost(), ((CastleBuilding) tempBuilding).getAmountOfDecreaseInSpeed(),
                    ((CastleBuilding) tempBuilding).getDamage(), ((CastleBuilding) tempBuilding).getRate());
        else if (tempBuilding instanceof WeaponBuilding)
            return new WeaponBuilding(government,tempBuilding.getGold(), tempBuilding.getName(), tempBuilding.getHitpoint(),
                    tempBuilding.getResourceRequired(),tempBuilding.getAmountOfResource(),tempBuilding.getAmountOfWorkers(),tempBuilding.getAllowedTextures(),
                    tempBuilding.getOccupiedCells(),((WeaponBuilding) tempBuilding).getConsumableResources(), ((WeaponBuilding) tempBuilding).getProductionRate());
        else
            return new OtherBuilding(government,tempBuilding.getGold(), tempBuilding.getName(), tempBuilding.getHitpoint(),
                    tempBuilding.getResourceRequired(),tempBuilding.getAmountOfResource(),tempBuilding.getAmountOfWorkers(),tempBuilding.getAllowedTextures(),
                    tempBuilding.getOccupiedCells(),((OtherBuilding) tempBuilding).getRate(), ((OtherBuilding) tempBuilding).getCapacity());
    }
}
