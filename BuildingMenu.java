package View.menus;

import Controller.BuildingMenuController;
import Model.Regex.BuildingMenuRegexes;
import Model.Regex.DropElementMenuRegexes;
import Model.gameandbattle.Government;
import Model.gameandbattle.map.Map;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenu {
    private Map map;
    private Government government;
    public void run(Scanner scanner,Map map,Government government){
        this.map = map;
        this.government = government;
        BuildingMenuController buildingMenuController  = new BuildingMenuController(map,government);
        String command;
        Matcher matcher;
        while (true)
        {
            command = scanner.nextLine();
            if((matcher = BuildingMenuRegexes.getMatcher(command,BuildingMenuRegexes.DROP_BUILDING)) != null)
            {
                System.out.println(buildingMenuController.dropBuilding(matcher));
            }
        }
    }
}
