package View.menus;

import Controller.GovernmentMenuController;
import Model.Regex.GovernmentMenuRegexes;
import Model.gameandbattle.Government;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GovernmentMenu {

    private final GovernmentMenuController governmentMenuController = new GovernmentMenuController();

    public void run(Scanner scanner, Government government) {
        System.out.println("Welcome to the government menu");
        String command;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if (command.matches("show\\s+popularity\\s+factors"))
                System.out.print(governmentMenuController.showPopularityFactors(government));
            else if (command.matches("show\\s+popularity"))
                System.out.println(governmentMenuController.showPopularity(government));
            else if ((matcher = GovernmentMenuRegexes.getMatcher(command, GovernmentMenuRegexes.CHANGE_FOOD_RATE)) != null) {
                governmentMenuController.changeFoodRate(matcher, government);
                System.out.println("successful");
            }
            else if (command.matches("food\\s+rate\\s+show"))
                System.out.println(governmentMenuController.showFoodRate(government));
            else if (command.matches("show\\s+food\\s+list"))
                System.out.print(governmentMenuController.showFoodList(government));
            else if ((matcher = GovernmentMenuRegexes.getMatcher(command, GovernmentMenuRegexes.CHANGE_TAX_RATE)) != null) {
                governmentMenuController.changeTaxRate(government, matcher);
                System.out.println("successful");
            }
            else if (command.matches("tax\\s+rate\\s+show"))
                System.out.println(governmentMenuController.showTaxRate(government));
            else if ((matcher = GovernmentMenuRegexes.getMatcher(command, GovernmentMenuRegexes.CHANGE_FEAR_RATE)) != null) {
                System.out.println("successful");
                governmentMenuController.changeFearRate(government, matcher);
            }
            else if(command.equals("exit")) return;
            else System.out.println("invalid command");
        }
    }

}
