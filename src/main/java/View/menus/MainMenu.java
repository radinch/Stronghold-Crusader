package View.menus;

import Controller.DataBank;
import Controller.GameMenuController;
import Controller.MainMenuController;
import Controller.MapMenuController;
import Model.gameandbattle.Government;
import Model.gameandbattle.map.Map;
import Model.signup_login_profile.User;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
    //this is the menu before game menu
    //in this menu you can select a map and start a game;
    private final MainMenuController mainMenuController;

    public MainMenu(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    public void run(Scanner scanner) {
        String command;
        Matcher matcher;
        int numberOfPlayers;
        System.out.println("welcome to main manu of the game");
        System.out.println("you can type show available maps to see game maps");
        System.out.println("or you can type new game for starting a new adventure");
        while (true) {
            command = scanner.nextLine();
            if(command.matches("show available maps"))
                showAvailableMaps();
            else if (command.equals("new game")) {
                System.out.println("how many players do you want to play with? [choose a number from 1,3,5,7]");
                numberOfPlayers = scanner.nextInt();
                while (numberOfPlayers != 1 && numberOfPlayers != 3 && numberOfPlayers != 5 && numberOfPlayers != 7) {
                    System.out.println("please choose correct number");
                    numberOfPlayers = scanner.nextInt();
                }
                int playerCounter = 0;
                System.out.println("please type username of players that you want to play with");
                while (playerCounter != numberOfPlayers) {
                    System.out.println("enter username of player number " + (playerCounter+1));
                    command = scanner.next();
                    if (!mainMenuController.isUserNameValid(command))
                        System.out.println("user: " + command + " does not exists");
                    mainMenuController.addPlayer(command);
                    playerCounter++;
                }
                System.out.println("now please select a map for this battle. [choose a number between 1,2,3]");
                int mapNumber = scanner.nextInt();
                while (mapNumber != 1 && mapNumber != 2 && mapNumber != 3)
                {
                    System.out.println("please choose correct number");
                    mapNumber = scanner.nextInt();
                }
                GameMenuController gameMenuController = new GameMenuController(mainMenuController.createGovernments(),Map.MAP_NUMBER_ONE);
                gameMenuController.run(scanner);
            }
            else if(command.equals("exit")) return;
        }

    }

    public void showAvailableMaps() {
        MapMenuController mapMenuController = new MapMenuController();
        System.out.println(mapMenuController.showAllOfMapWithXY(Map.MAP_NUMBER_ONE,100,100));
    }

}
