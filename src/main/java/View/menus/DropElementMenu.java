package View.menus;

import Controller.DropMenuController;
import Model.Regex.DropElementMenuRegexes;
import Model.gameandbattle.Government;
import Model.gameandbattle.map.Map;

import java.util.Scanner;
import java.util.regex.Matcher;

public class DropElementMenu {
    private Government government;
    private final DropMenuController dropMenuController;
    public DropElementMenu(DropMenuController dropMenuController) {
        this.dropMenuController = dropMenuController;
    }
    public void run(Scanner scanner){
        System.out.println("Welcome to the drop element menu");
        String command;
        Matcher matcher;
        while(true) {
            command = scanner.nextLine();
            if((matcher = DropElementMenuRegexes.getMatcher(command, DropElementMenuRegexes.SET_A_TILE_TEXTURE)) != null)
                System.out.println(dropMenuController.setTileTexture(matcher));
            else if((matcher = DropElementMenuRegexes.getMatcher(command, DropElementMenuRegexes.SET_A_GROUP_TEXTURE)) != null)
                System.out.println(dropMenuController.setGroupTexture(matcher));
            else if((matcher = DropElementMenuRegexes.getMatcher(command, DropElementMenuRegexes.DROP_TREE)) != null)
                System.out.println(dropMenuController.dropTree(matcher));
            else if((matcher = DropElementMenuRegexes.getMatcher(command,DropElementMenuRegexes.CLEAR)) != null)
            {
                System.out.println(dropMenuController.clear(matcher));
            }
            else if((matcher = DropElementMenuRegexes.getMatcher(command,DropElementMenuRegexes.DROP_ROCK)) != null)
            {
                System.out.println(dropMenuController.dropRock(matcher));
            }
            else if((matcher = DropElementMenuRegexes.getMatcher(command, DropElementMenuRegexes.DROP_WATER)) != null)
                System.out.println(dropMenuController.dropWater(matcher));
            else if(command.equals("exit")) return;
            else System.out.println("invalid command");
        }
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public DropMenuController getDropMenuController() {
        return dropMenuController;
    }
}
