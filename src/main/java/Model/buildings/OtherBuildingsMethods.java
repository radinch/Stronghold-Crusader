package Model.buildings;

import Controller.DataBank;
import Model.gameandbattle.Government;
import Model.gameandbattle.battle.Person;
import Model.gameandbattle.map.Building;
import Model.gameandbattle.map.Map;
import Model.gameandbattle.stockpile.Resource;

import java.util.Scanner;
import java.util.regex.Matcher;

public class OtherBuildingsMethods {
    public static void processFood(Resource firstResource, Resource finalResource){

    }
    public static void distributeFood(Map map,int x,int y){

    }
    public static void extract(Resource resource){

    }
    public static void shop(){

    }
    public static boolean isMaterialOK(Map map, int x, int y, Building building){
        return false;
    }
    public static void makeTunnel(Map map,int x,int y){

    }

    public static void changeTaxRate(Government government, Matcher matcher) {
        int rateNumber = Integer.parseInt(matcher.group("rate"));
        government.setTaxRate(rateNumber);
        if (rateNumber <= 0)
            government.setPopularity(government.getPopularity() + -2 * rateNumber + 1);
        else
            government.setPopularity(government.getPopularity() + -2 * rateNumber);
    }

    public static void repair(Government government,Building building,int x,int y,Map currentMap) {
        int maxHp = DataBank.getBuildingName().get(building.getName()).getHitpoint();
        int amountOfStoneNeeded = (maxHp - building.getHitpoint()) / 100;
        if(government.getStockpile().getStone() < amountOfStoneNeeded)
            System.out.println("stone is not enough");
        else if(isEnemyClose(government,x,y,currentMap))
            System.out.println("you cant repair because enemy is near to you");
        building.setHitpoint(maxHp);
        government.getStockpile().increaseByName("stone", (-1)*amountOfStoneNeeded);
    }

    private static boolean isEnemyClose(Government government,int x,int y,Map currentMap){
        for (int i = x-1; i <x+2 ; i++) {
            for (int j = y-1; j <y+2 ; j++) {
                if(i>= 0 && j>=0 && i< currentMap.getSize() && j<currentMap.getSize()) {
                    if(currentMap.getACell(i,j).getPeople() == null)
                        continue;
                    for (Person person : currentMap.getACell(i, j).getPeople()) {
                        if(!person.getGovernment().equals(government))
                            return true;
                    }
                }
            }
        }
        return false;
    }
}
