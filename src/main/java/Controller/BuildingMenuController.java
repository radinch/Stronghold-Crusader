package Controller;

import Model.gameandbattle.Government;
import Model.gameandbattle.map.Building;
import Model.gameandbattle.map.Map;
import Model.gameandbattle.map.Texture;

import java.util.regex.Matcher;

public class BuildingMenuController {
    private Map map;
    private Government government;
    public String dropBuilding(Matcher matcher){
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));
        String type = matcher.group("type");
        if(isCoordinateValid(x,y) != null)
            return isCoordinateValid(x,y);
        else if(!DataBank.getBuildingName().containsKey(type))
            return "invalid name of building";
        else if(isThereABuilding(x,y))
            return "can't put building on building";
        else if(!isThisCellMaterialValid(x,y,DataBank.getBuildingName().get(type)))
            return "can't put building on this texture";
        else {
            Building tempBuilding = DataBank.getBuildingName().get(type);
            tempBuilding.setGovernment(government);
            map.getACell(x,y).setBuilding(tempBuilding);
            return "success";
        }
    }
    public String selectBuilding(Matcher matcher){
        return  null;
    }
    public String createUnit(Matcher matcher){
        return null;
    }
    public String repair(){
        return  null;
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
    private boolean doIHaveABuilding(int x,int y){
        return false;
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
    private boolean isEnemyClose(){
        return false;
    }
    public BuildingMenuController(Map map, Government government) {
        this.map = map;
        this.government = government;
    }
}
