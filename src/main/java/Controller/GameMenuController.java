package Controller;

import Model.gameandbattle.Government;
import Model.gameandbattle.map.Map;
import Model.signup_login_profile.User;
import View.menus.*;

import java.util.ArrayList;
import java.util.Scanner;

public class GameMenuController {

    private Map currentMap;
    private ArrayList<Government> governments;

    private Government currentGovernment;
    private int turnCounter;
    private GameMenu gameMenu;
    private int amountOfAllPlayers;
    public GameMenuController(ArrayList<Government> governments, Map currentMap) {
        turnCounter=0;
        gameMenu=new GameMenu(this);
        this.governments=governments;
        currentGovernment=governments.get(0);
        this.currentMap=currentMap;
        amountOfAllPlayers=governments.size();
        DataBank.initializeBuildingName();
        DataBank.initializeAllUnits();
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

    public void nextTurn(BuildingMenu buildingMenu, ShopMenu shopMenu, TradeMenu tradeMenu, UnitMenu unitMenu,DropElementMenu dropMenu,GameMenu gameMenu)
    {
        int counterLosers=0;
        turnCounter++;
        for(int i=governments.size()-1;i>=0;i--){
            if(governments.get(i).getKing().getHp()<=0) {
                governments.get(i).setAlive(false);
                counterLosers++;
                if(governments.get(i).getRuler().getHighScore()<turnCounter) governments.get(i).getRuler().setHighScore(turnCounter);
                governments.remove(i);
            }
        }
        if(counterLosers==amountOfAllPlayers-1){
            System.out.println(counterLosers);
            System.out.println(amountOfAllPlayers);
            gameMenu.setGameOver(true);
            return;
        }
        while (true){
            if(!governments.get(turnCounter%amountOfAllPlayers).isAlive()) turnCounter++;
            else break;
        }
        currentGovernment=governments.get(turnCounter%amountOfAllPlayers);
        for (int i=0;i<currentMap.getSize();i++){
            for (int j=0;j<currentMap.getSize();j++){
                if(currentMap.getACell(i,j).getBuilding()!=null) currentMap.getACell(i,j).getBuilding().makeAffect(i,j,currentMap);
            }
        }
        for(Government government:governments){
            double amount=0;
            if (government.getTaxRate()>0) amount=0.6+(government.getTaxRate()-1)*0.2;
            else if(government.getTaxRate()==0) amount=0;
            else amount=-1*(0.6+(Math.abs(government.getTaxRate())-1)*0.2);
            government.setCoin(government.getCoin()+government.getPopulation()*amount);
            government.setPopularity(government.nonZeroFoods()-1+government.getPopularity());
            government.setPopularity(government.getPopularity() + 4 * government.getFoodRate());
            if (government.getTaxRate() <= 0)
                government.setPopularity(government.getPopularity() + (-2) * government.getTaxRate() + 1);
            else
                government.setPopularity(government.getPopularity() + (-2) * government.getTaxRate());
            government.setPopularity(government.getPopularity() + government.getFearRate());
            government.setPopularity( Math.min(100, government.getPopularity()));
            government.getGranary().setApple((government.getGranary().getApple() - (1 + government.getFoodRate()*0.5))*government.getPopulation());
            government.getGranary().setCheese((government.getGranary().getCheese() - (1 + government.getFoodRate()*0.5))*government.getPopulation());
            government.getGranary().setBread((government.getGranary().getBread() - (1 + government.getFoodRate()*0.5))*government.getPopulation());
            government.getGranary().setMeat((government.getGranary().getMeat() - (1 + government.getFoodRate()*0.5))*government.getPopulation());
            for(int i=0;i<government.getFoods().length;i++){
                government.getFoods()[i]=government.getFoods()[i]-(1 + government.getFoodRate()*0.5)*government.getPopulation();
            }

            government.setPopulation(government.getMaxPopulation()*government.getPopularity()/100);
        }
        buildingMenu.setGovernment(currentGovernment); shopMenu.setGovernment(currentGovernment); tradeMenu.setGovernment(currentGovernment);
        unitMenu.setGovernment(currentGovernment); tradeMenu.setGovernment(currentGovernment);
        System.out.println("it's your turn "+currentGovernment.getRuler().getNickname());

    }

    public ArrayList<Government> getGovernments() {
        return governments;
    }

    public GameMenu getGameMenu() {
        return gameMenu;
    }
}
