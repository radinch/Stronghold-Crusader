package Controller;

import Model.Regex.ShopMenuRegexes;
import Model.gameandbattle.Government;
import Model.gameandbattle.battle.Weapon;
import Model.gameandbattle.shop.Shop;
import Model.gameandbattle.stockpile.Food;
import Model.gameandbattle.stockpile.Resource;
import View.menus.ShopMenu;

import java.util.regex.Matcher;

public class ShopMenuController {
    private Government government;

    public ShopMenuController(Government government) {
        this.government = government;
    }

    public String showPriceList(){
        Shop shop=Shop.getShop();
        for(int i=0;i<shop.getFoods().length;i++) System.out.println("name: "+shop.getFoods()[i].getName()+"\nprice: "+shop.getFoods()[i].getPrice()+
                "\nsell price: "+shop.getFoods()[i].getSellPrice()+"\nyou have: "+ government.getFoods()[i]);
        for(int i=0;i<shop.getWeapons().length;i++) System.out.println("name: "+shop.getWeapons()[i].getName()+"\nprice: "+shop.getWeapons()[i].getPrice()+
                "\nsell price: "+shop.getWeapons()[i].getSellPrice()+
                "\nyou have: "+ government.getWeapons()[i]);
        for(int i=0;i<shop.getResources().length;i++) System.out.println("name: "+shop.getResources()[i].getName()+"\nprice: "+shop.getResources()[i].getPrice()+
                "\nsell price: "+shop.getResources()[i].getSellPrice()+"\nyou have: "+ government.getStockpile().getByName(shop.getResources()[i].getName()));
        return "that's it!";
    }
    public String buy(String input){
        Matcher nameMather= ShopMenuRegexes.NAME.getMatcher(input); Matcher amountMather= ShopMenuRegexes.AMOUNT.getMatcher(input);
        String name = null; int amount = 0;
        if(nameMather.find()) name=nameMather.group("name");
        if(amountMather.find()) amount=Integer.parseInt(amountMather.group("amount"));
        int gold=getNeededGold(name);
        if(gold*amount>government.getCoin()) return "not enough gold";
        if(government.amountOfAllResources()+amount>Government.getLimitOfResources()) return "you will pass your limit";
        government.setCoin(government.getCoin()-gold*amount);
        addItem(name,government,amount);
        return "item bought successfully";
    }
    public String sell(String input){
        Matcher nameMather= ShopMenuRegexes.NAME.getMatcher(input); Matcher amountMather= ShopMenuRegexes.AMOUNT.getMatcher(input);
        String name = null; int amount = 0;
        if(nameMather.find()) name=nameMather.group("name");
        if(amountMather.find()) amount=Integer.parseInt(amountMather.group("amount"));
        if(!isThereEnoughItems(name,amount,government)) return "you don't have that many items";
        int increaseGold=getSellGold(name);
        government.setCoin(government.getCoin()+increaseGold*amount);
        addItem(name,government,-1*amount);
        return "item sold successfully";
    }
    public static int getNeededGold(String name){
        Shop shop=Shop.getShop();
        for(int i=0;i<shop.getFoods().length;i++){
            if(shop.getFoods()[i].getName().equals(name)) return shop.getFoods()[i].getPrice();
        }
        for(Weapon weapon:shop.getWeapons()){
            if(weapon.getName().equals(name)) return weapon.getPrice();
        }
        for(Resource resource:shop.getResources()){
            if(resource.getName().equals(name)) return resource.getPrice();
        }
        return -1;
    }
    public static void addItem(String name,Government government,int amount){
        Shop shop=Shop.getShop();
        for(int i=0;i<shop.getFoods().length;i++){
            if(shop.getFoods()[i].getName().equals(name)) {
                government.getFoods()[i]=government.getFoods()[i]+amount;
                government.getGranary().setGranaryByName(name,  government.getFoods()[i]);
            }
        }
        for(int i=0;i<shop.getWeapons().length;i++){
            if(shop.getWeapons()[i].getName().equals(name)) government.getWeapons()[i]=government.getWeapons()[i]+amount;
        }
        for(Resource resource:shop.getResources()){
            if(resource.getName().equals(name)) government.getStockpile().increaseByName(name,amount);
        }
    }
    public static boolean isThereEnoughItems(String name,int amount,Government government){
        Shop shop=Shop.getShop();
        for(int i=0;i<shop.getFoods().length;i++){
            if(shop.getFoods()[i].getName().equals(name)) return (government.getFoods()[i]>amount);
        }
        for(int i=0;i<shop.getWeapons().length;i++){
            if(shop.getWeapons()[i].getName().equals(name)) return (government.getWeapons()[i]>amount);
        }
        for(Resource resource:shop.getResources()){
            if(resource.getName().equals(name)) return government.getStockpile().getByName(name)>amount;
        }
        return false;
    }
    public static int getSellGold(String name){
        Shop shop=Shop.getShop();
        for(int i=0;i<shop.getFoods().length;i++){
            if(shop.getFoods()[i].getName().equals(name)) return shop.getFoods()[i].getSellPrice();
        }
        for(Weapon weapon:shop.getWeapons()){
            if(weapon.getName().equals(name)) return weapon.getSellPrice();
        }
        for(Resource resource:shop.getResources()){
            if(resource.getName().equals(name)) return resource.getSellPrice();
        }
        return -1;
    }
}
