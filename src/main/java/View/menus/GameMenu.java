package View.menus;

import Controller.DataBank;
import Controller.GameMenuController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {

    private final GameMenuController gameMenuController;

    public GameMenu(GameMenuController gameMenuController) {
        this.gameMenuController = gameMenuController;
    }
    private MapMenu mapMenu = new MapMenu();
    private BuildingMenu buildingMenu = new BuildingMenu();
    private GovernmentMenu governmentMenu = new GovernmentMenu();
    private ShopMenu shopMenu;
    private UnitMenu unitMenu = new UnitMenu();
    public void run(Scanner scanner) {
        System.out.println("The battle started");
        String command;
        Matcher matcher;
        while (true)
        {
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
                unitMenu.run(scanner);
            else if(command.equals("next turn"))
                gameMenuController.nextTurn();
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
}
