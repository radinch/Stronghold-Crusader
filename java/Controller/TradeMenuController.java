package Controller;

import Model.gameandbattle.Government;
import Model.gameandbattle.shop.Request;
import Model.gameandbattle.stockpile.Resource;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class TradeMenuController {
    private Government government;
    private Government otherGovernment;

    public TradeMenuController(Government government, Government otherGovernment) {
        this.government = government;
        this.otherGovernment = otherGovernment;
    }

    public String trade(Matcher matcher) {
        String type=matcher.group("type");
        int amount=Integer.parseInt(matcher.group("amount"));
        int price =Integer.parseInt(matcher.group("price"));
        String message=matcher.group("message");
        Request request=new Request(government,otherGovernment, Resource.getByName(type),message,price,amount);
        DataBank.getRequests().add(request);
        government.getRequestsMadeByMe().add(request);
        otherGovernment.getRequestsToMe().add(request);
        otherGovernment.getUnseenRequests().add(request);
        return "trade made!";
    }

    public String showTradeList() {
        for(Request request:government.getRequestsToMe()){
            System.out.println("type: "+request.getResource().getName()+"\namount: "+request.getAmount()+"\nprice: "+request.getPrice()+"\n" +
                    "made by: "+request.getSender().getRuler().getUsername()+"\nthe message: "+request.getSenderMessage());
        }
        return "that's all of your trades!";
    }

    public String acceptTrade(Matcher matcher) {
        int id=Integer.parseInt(matcher.group("id"));
        String message=matcher.group("message");
        Request request=government.getRequestById(id);
        request.setReceiverMessage(message);
        if(request.getPrice()==0){
            government.getStockpile().increaseByName(request.getResource().getName(),request.getAmount());
            for(int i=0;i<government.getRequestsToMe().size();i++){
                if(government.getRequestsToMe().get(i).getId()==id){
                    government.getRequestsToMe().remove(i);
                    break;
                }
            }
            government.getRequestsAcceptedByMe().add(request);
        }
        else{
            government.getStockpile().increaseByName(request.getResource().getName(),-1*request.getAmount());
            for(int i=0;i<government.getRequestsToMe().size();i++){
                if(government.getRequestsToMe().get(i).getId()==id){
                    government.getRequestsToMe().remove(i);
                    break;
                }
            }
            government.getRequestsAcceptedByMe().add(request);
            government.setCoin(government.getCoin()+request.getPrice());
        }
        return "accepted!";
    }

    public String tradeHistory() {
        System.out.println("made by you: ");
        for(Request request:government.getRequestsMadeByMe()){
            System.out.println("type: "+request.getResource().getName()+"\namount: "+request.getAmount()+"\nprice: "+request.getPrice()+"\n" +
                    "made by: "+request.getSender().getRuler().getUsername()+"\nthe message: "+request.getSenderMessage());
        }
        System.out.println("accepted by you:");
        for(Request request:government.getRequestsAcceptedByMe()){
            System.out.println("type: "+request.getResource().getName()+"\namount: "+request.getAmount()+"\nprice: "+request.getPrice()+"\n" +
                    "made by: "+request.getSender().getRuler().getUsername()+"\nthe message: "+request.getSenderMessage());
        }
        return "done!";
    }
}
