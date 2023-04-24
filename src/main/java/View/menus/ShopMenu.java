package View.menus;

import Controller.ShopMenuController;
import Model.Regex.ShopMenuRegexes;
import Model.gameandbattle.Government;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {
    private Government government;

    public ShopMenu(Government government) {
        this.government = government;
    }
    public void run(Scanner scanner){
        System.out.println("Welcome to the shop menu");
        String input;
        ShopMenuController controller=new ShopMenuController(government);
        while (true){
            input=scanner.nextLine();
            Matcher sell =ShopMenuRegexes.SELL.getMatcher(input);
            Matcher buy =ShopMenuRegexes.BUY.getMatcher(input);
            if(sell.matches()) System.out.println(controller.sell(input));
            else if(buy.matches()) System.out.println(controller.buy(input));
            else if(input.equals("show price list")) System.out.println(controller.showPriceList());
            else if(input.equals("exit")) break;
            else System.out.println("invalid command");
        }
    }
}
