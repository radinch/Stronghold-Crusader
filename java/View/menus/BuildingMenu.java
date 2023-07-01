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
        System.out.println("Welcome to the building menu");
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
                //System.out.println(buildingMenuController.dropBuilding(matcher));
            }
            else if((matcher = BuildingMenuRegexes.getMatcher(command,BuildingMenuRegexes.DROP_WALL)) != null)
                System.out.println(buildingMenuController.dropWall(matcher));
            else if((matcher = BuildingMenuRegexes.getMatcher(command,BuildingMenuRegexes.DROP_GATE)) != null)
                System.out.println(buildingMenuController.dropGate(matcher));
            else if((matcher = BuildingMenuRegexes.getMatcher(command,BuildingMenuRegexes.SELECT_BUILDING)) != null) {
                if(buildingMenuController.selectBuilding(matcher,scanner) != null)
                    System.out.println(buildingMenuController.selectBuilding(matcher,scanner));
            }
            else if((matcher = BuildingMenuRegexes.getMatcher(command,BuildingMenuRegexes.DROP_STAIR)) != null)
            {
                System.out.println(buildingMenuController.dropStair(matcher));
            }
            else if(command.equals("exit"))
                return;
            else
                System.out.println("invalid command");
        }
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }
}
