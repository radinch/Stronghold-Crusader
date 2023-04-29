package View.menus;

import Controller.SelectBuildingController;
import Model.Regex.BuildingMenuRegexes;
import Model.Regex.GovernmentMenuRegexes;
import Model.buildings.CastleBuilding;
import Model.buildings.OtherBuildingsMethods;
import Model.gameandbattle.Government;
import Model.gameandbattle.map.Building;
import Model.gameandbattle.map.Map;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SelectBuildingMenu {

    private final SelectBuildingController selectBuildingController = new SelectBuildingController();

    public void run(Scanner scanner,Building building,int x,int y, Map map) {
        String command;
        Matcher matcher;
        if(building instanceof CastleBuilding) {
            System.out.println("hitpoint: " + building.getHitpoint());
            System.out.println("do you want to repair?[yes/no]");
            if (scanner.nextLine().equals("yes"))
                selectBuildingController.repair(building.getGovernment(), building,x,y,map);
        }
        while (true) {
            command = scanner.nextLine();
            if((matcher = GovernmentMenuRegexes.getMatcher(command,GovernmentMenuRegexes.CHANGE_TAX_RATE)) != null &&
                    (building.getName().equals("Small stone gatehouse") || building.getName().equals("big stone gatehouse"))) {
                selectBuildingController.changeTaxRate(building.getGovernment(), matcher);
            }
            if((matcher = BuildingMenuRegexes.getMatcher(command,BuildingMenuRegexes.CREATE_UNIT)) != null &&
                    (building.getName().equals("barrack") || building.getName().equals("Mercenary Post"))) {
                selectBuildingController.createUnit(building.getGovernment(),matcher,building);
            }
        }
    }
}
