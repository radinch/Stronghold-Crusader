package View.menus;

import Controller.UnitMenuController;
import Model.Regex.UnitMenuRegexes;
import Model.gameandbattle.Government;
import Model.gameandbattle.battle.Person;
import Model.gameandbattle.map.Map;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class UnitMenu {
    private Map map;
    private Government government;
    private ArrayList<Person> selectedTroop;
    private int x;
    private int y;
    {
        selectedTroop=null;
    }
    public void run(Scanner scanner){
        selectedTroop=null;
        UnitMenuController unitMenuController;
        System.out.println("Welcome to the unit menu");
        String input;
        while (true){
            unitMenuController=selectUnit(scanner);
            input=scanner.nextLine();
            if(input.equals("exit")) break;
            Matcher matcher1=UnitMenuRegexes.MOVE_UNIT.getMatcher(input);
            Matcher matcher2=UnitMenuRegexes.PATROL_UNIT.getMatcher(input);
            if(matcher1.matches()) unitMenuController.moveUnit(matcher1);
            else if(matcher2.matches())  unitMenuController.patrolUnit(matcher2);
            else if(input.equals("stop patrol")) unitMenuController.stopPatrol();
        }
    }
    public UnitMenuController selectUnit(Scanner scanner){
        System.out.println("ok,select a unit now!");
        String input;
        UnitMenuController unitMenuController;
        while (true){
            input=scanner.nextLine();
            Matcher matcher=UnitMenuRegexes.SELECT_UNIT.getMatcher(input);
            if(matcher.matches()){
                x=Integer.parseInt(matcher.group("x"));
                x=Integer.parseInt(matcher.group("y"));
                selectedTroop=map.getACell(x,y).getPeople();
                unitMenuController=new UnitMenuController(selectedTroop,x,y);
                return unitMenuController;
            }
            else System.out.println("you have to select a unit first");
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
