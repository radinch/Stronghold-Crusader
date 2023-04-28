package View.menus;

import Controller.DataBank;
import Controller.TradeMenuController;
import Model.Regex.TradeMenuRegexes;
import Model.gameandbattle.Government;
import Model.gameandbattle.shop.Request;

import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    private Government government;
    private Government otherGovernment;

    public TradeMenu(Government government) {
        this.government = government;
    }

    public void run(Scanner scanner){
        System.out.println("Welcome to the trade menu");
        for(Request request:government.getUnseenRequests()){
            System.out.println("type: "+request.getResource().getName()+"\namount: "+request.getAmount()+"\nprice: "+request.getPrice()+"\n" +
                    "made by: "+request.getSender().getRuler()+"\nthe message: "+request.getSenderMessage());
        }
        government.getUnseenRequests().clear();
        String input;
        while (true){
            System.out.println("select a player!");
            showPlayersList();
            otherGovernment=selectedGovernment(scanner.nextLine());
            input= scanner.nextLine();
            TradeMenuController controller=new TradeMenuController(government,otherGovernment);
            Matcher request= TradeMenuRegexes.TRADE.getMatcher(input);
            Matcher accept=TradeMenuRegexes.ACCEPT_TRADE.getMatcher(input);
            if(request.matches()) System.out.println(controller.trade(request));
            else if(accept.matches()) System.out.println(controller.acceptTrade(accept));
            else if(input.equals("trade list")) System.out.println(controller.showTradeList());
            else if (input.equals("trade history")) System.out.println(controller.showTradeList());
            else if(input.equals("exit")) break;
            else System.out.println("invalid command");
        }
    }
    public static void showPlayersList(){
        for (int i=0;i< DataBank.getGovernments().size();i++){
            System.out.println(DataBank.getGovernments().get(i).getRuler().getUsername());
        }
    }
    public static Government selectedGovernment(String name){
        for (int i=0;i< DataBank.getGovernments().size();i++){
            if(DataBank.getGovernments().get(i).getRuler().getUsername().equals(name)) return DataBank.getGovernments().get(i);
        }
        return null;
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public Government getOtherGovernment() {
        return otherGovernment;
    }

    public void setOtherGovernment(Government otherGovernment) {
        this.otherGovernment = otherGovernment;
    }
}
