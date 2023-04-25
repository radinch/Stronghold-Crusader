package Controller;

import Model.gameandbattle.Government;
import Model.gameandbattle.map.Map;
import Model.signup_login_profile.User;
import View.menus.GameMenu;

import java.util.ArrayList;
import java.util.Scanner;

public class GameMenuController {

    private Map currentMap;
    private ArrayList<Government> governments;

    private Government currentGovernment;
    private int turnCounter;
    private GameMenu gameMenu;
    public GameMenuController(ArrayList<Government> governments, Map currentMap) {
        turnCounter=0;
        gameMenu=new GameMenu(this);
        this.governments=governments;
        currentGovernment=governments.get(0);
        this.currentMap=currentMap;
    }
    public void run(Scanner scanner) {
        gameMenu.run(scanner);
    }

    public Government getCurrentGovernment() {
        return currentGovernment;
    }

    public void setCurrentGovernment(Government currentGovernment) {
        this.currentGovernment = currentGovernment;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    public void nextTurn()
    {

    }
}
