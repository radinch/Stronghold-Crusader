package View.menus;

import Controller.DataBank;
import Controller.DropMenuController;
import Controller.GameMenuController;
import Model.gameandbattle.Government;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private final GameMenuController gameMenuController;
    private boolean isGameOver;

    public GameMenu(GameMenuController gameMenuController) {
        Government help=gameMenuController.getCurrentGovernment();
        this.gameMenuController = gameMenuController;
        shopMenu=new ShopMenu(gameMenuController.getCurrentGovernment());
        tradeMenu=new TradeMenu(gameMenuController.getCurrentGovernment());
        DropMenuController dropMenuController=new DropMenuController(gameMenuController.getCurrentMap(),gameMenuController.getCurrentGovernment());
        dropElementMenu=new DropElementMenu(dropMenuController);
        buildingMenu.setGovernment(help); shopMenu.setGovernment(help); tradeMenu.setGovernment(help);
        unitMenu.setGovernment(help); tradeMenu.setGovernment(help);
        isGameOver=false;
    }
    private MapMenu mapMenu = new MapMenu();
    private BuildingMenu buildingMenu = new BuildingMenu();
    private GovernmentMenu governmentMenu = new GovernmentMenu();
    private ShopMenu shopMenu;
    private UnitMenu unitMenu = new UnitMenu();
    private TradeMenu tradeMenu;
    private DropElementMenu dropElementMenu;

    public void run(Scanner scanner) {
        System.out.println("The battle started");
        String command;
        Matcher matcher;
        while (true)
        {
            if(isGameOver) {
                System.out.println("game is over the winner is :"+ Objects.requireNonNull(winner()).getRuler().getUsername());
                break;
            }
            command = scanner.nextLine();
            if(command.equals("map menu"))
                mapMenu.run(scanner,gameMenuController.getCurrentMap());
            else if(command.equals("government menu"))
                governmentMenu.run(scanner,gameMenuController.getCurrentGovernment());
            else if(command.equals("building menu"))
                buildingMenu.run(scanner,gameMenuController.getCurrentMap(),gameMenuController.getCurrentGovernment());
            else if(command.equals("shop menu")) {
                shopMenu = new ShopMenu(gameMenuController.getCurrentGovernment());
                shopMenu.run(scanner);
            }
            else if(command.equals("unit menu"))
                unitMenu.run(scanner,gameMenuController.getCurrentMap(),gameMenuController.getCurrentGovernment());
            else if(command.equals("trade menu"))
                tradeMenu.run(scanner,gameMenuController.getGovernments());
            else if(command.equals("next turn"))
                gameMenuController.nextTurn(buildingMenu,shopMenu,tradeMenu,unitMenu,dropElementMenu,this);
            //else if(command.equals("exit")) return;
        }
    }

    public void selectColor(Scanner scanner) {
        System.out.println("now please select a color from : ");
        for (int i = 0; i < DataBank.colors.size(); i++) {
            System.out.print(DataBank.colors.get(i) + " ");
        }
        String color = scanner.nextLine();
        while (!DataBank.colors.contains(color))
        {
            System.out.println("please choose correct color");
            color = scanner.nextLine();
        }
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
    private Government winner(){
        for(Government government: gameMenuController.getGovernments()){
            if(government.isAlive()) return government;
        }
        return null;
    }
}
