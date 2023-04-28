package View.menus;

import Model.gameandbattle.Government;
import Model.gameandbattle.map.Map;

import java.util.Scanner;

public class UnitMenu {
    private Map map;
    private Government government;
    public void run(Scanner scanner){
        System.out.println("Welcome to the unit menu");
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
