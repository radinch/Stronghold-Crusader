package View.menus;

import Controller.UnitMenuController;
import Model.Regex.UnitMenuRegexes;
import Model.gameandbattle.Government;
import Model.gameandbattle.battle.Person;
import Model.gameandbattle.battle.Troop;
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
    public void run(Scanner scanner,Map map,Government government){
        this.government = government;
        selectedTroop=new ArrayList<>();
        this.map = map;
        UnitMenuController unitMenuController;
        System.out.println("Welcome to the unit menu");
        String input;
        while (true){
            unitMenuController=selectUnit(scanner);
            if(unitMenuController == null)
                return;
            if(selectedTroop.size() == 0)
                System.out.println("you have not any troops in this cell");
            input=scanner.nextLine();
            if(input.equals("exit")) break;
            Matcher matcher1=UnitMenuRegexes.MOVE_UNIT.getMatcher(input);
            Matcher matcher2=UnitMenuRegexes.PATROL_UNIT.getMatcher(input);
            Matcher matcher3=UnitMenuRegexes.BUILD_SURROUNDINGS.getMatcher(input);
            Matcher matcher4=UnitMenuRegexes.POUR_OIL.getMatcher(input);
            Matcher matcher5=UnitMenuRegexes.ATTACK.getMatcher(input);
            Matcher matcher6=UnitMenuRegexes.AIR_ATTACK.getMatcher(input);
            Matcher matcher7=UnitMenuRegexes.DIG_DITCH.getMatcher(input);
            Matcher matcher8 = UnitMenuRegexes.CANCEL_DITCH.getMatcher(input);
            Matcher matcher9 = UnitMenuRegexes.REMOVE_DITCH.getMatcher(input);
            Matcher matcher10=UnitMenuRegexes.SET_CONDITION.getMatcher(input);
            Matcher matcher11=UnitMenuRegexes.DIG_TUNNEL.getMatcher(input);
            Matcher matcher12=UnitMenuRegexes.PUT_LADDER.getMatcher(input);
            if(matcher1.matches()) System.out.println(unitMenuController.moveUnit(matcher1,map));
            else if(matcher2.matches()) System.out.println(unitMenuController.patrolUnit(matcher2));
            else if(input.equals("stop patrol")) unitMenuController.stopPatrol();
            else if(matcher3.matches()) System.out.println(unitMenuController.buildSurroundings(matcher3));
            else if(matcher4.matches()) System.out.println(unitMenuController.pourOil(matcher4));
            else if(matcher5.matches()) System.out.println(unitMenuController.attack(matcher5));
            else if (matcher6.matches()) System.out.println(unitMenuController.skyAttack(matcher6));
            else if(UnitMenuRegexes.EQUIP_WITT_OIL.getMatcher(input).matches()) System.out.println(unitMenuController.equipWithOil());
            else if(input.equals("disband unit")) System.out.println(unitMenuController.disbandUnit());
            else if(matcher7.matches()) System.out.println(unitMenuController.digDitch(matcher7));
            else if(matcher8.matches()) System.out.println(unitMenuController.cancelDitch(matcher8));
            else if(matcher9.matches()) System.out.println(unitMenuController.removeDitch(matcher9));
            else if(matcher10.matches()) System.out.println(unitMenuController.setCondition(matcher10));
            else if(matcher11.matches()) System.out.println(unitMenuController.digTunnel(matcher11));
            else if(matcher12.matches()) System.out.println(unitMenuController.putLadder(matcher12));
            else System.out.println("invalid command");
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
                x=Integer.parseInt(matcher.group("y"));
                y=Integer.parseInt(matcher.group("x"));
                for (Person person : map.getACell(x, y).getPeople()) {
                    if(person.getGovernment().equals(government) && person instanceof Troop)
                        selectedTroop.add(person);
                }
                unitMenuController=new UnitMenuController(selectedTroop,x,y,government);
                return unitMenuController;
            }
            else if(input.equals("exit"))
                break;
            else System.out.println("you have to select a unit first");
        }
        return null;
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
