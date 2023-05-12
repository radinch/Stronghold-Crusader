package Controller;

import Model.gameandbattle.Government;
import Model.gameandbattle.map.*;
import Model.gameandbattle.battle.Patrol;
import Model.gameandbattle.battle.Person;
import Model.gameandbattle.battle.Troop;
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
        DataBank.initializeWalls();
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
                if(currentMap.getACell(i,j).getBuilding().getHitpoint()<=0 )
                    removeBuilding(currentMap.getACell(i,j));
                if(currentMap.getACell(i,j).getBuilding()!=null){
                    currentMap.getACell(i,j).getBuilding().makeAffect(i,j,currentMap);
                    if (currentMap.getACell(i,j).getBuilding().isFiery())
                        removeBuilding(currentMap.getACell(i,j));
                }
                if(currentMap.getACell(i,j).isDitchUnderConstruction()) {
                    currentMap.getACell(i,j).setTurnCounterForDitch(currentMap.getACell(i,j).getTurnCounterForDitch() + 1);
                    if(currentMap.getACell(i,j).getTurnCounterForDitch() == 2)
                        currentMap.getACell(i,j).setDitch();
                }
                int counter=0;
                for(Person person:currentMap.getACell(i,j).getPeople()){
                    removePeople(currentMap.getACell(i,j),person);
                    if(person.getPatrol()!=null){
                        Patrol patrol=person.getPatrol(); int condition=patrol.getCondition(); int x,y,x1,y1;
                        if(condition==0){x=patrol.getStartX(); y=patrol.getStartY(); x1=patrol.getFinishX(); y1= patrol.getFinishY();}
                        else {x=patrol.getFinishX(); y=patrol.getFinishY(); x1=patrol.getStartX(); y1= patrol.getStartY();}
                        ArrayList<Integer> pathX=new ArrayList<>(); ArrayList<Integer> pathY=new ArrayList<>(); boolean[][] help=new boolean[currentMap.getSize()][currentMap.getSize()];
                        prepareHelp(help);
                        currentMap.getACell(i,j).getPeople().remove(counter);
                        findPath(help,x,y,x1,y1,pathX,pathY);
                        int speed = 100;
                        if ((person instanceof Troop)) speed = ((Troop) person).getSpeed();
                        x = pathX.get(Math.min(pathX.size() - 1, speed / 25));
                        y = pathY.get(Math.min(pathY.size() - 1, speed / 25));
                        if(x==pathX.get(pathX.size()-1)&&y==pathY.get(pathY.size()-1)) patrol.setCondition(1- patrol.getCondition());
                        currentMap.getACell(x, y).getPeople().add(person);
                    }
                    counter++;
                    if (currentMap.getACell(i,j).getTexture().equals(Texture.PETROLEUM))
                        currentMap.getACell(i,j).setTexture(Texture.GROUND);
                }
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

    private void removeBuilding(Cell cell) {
        currentGovernment.getBuildings().remove(cell.getBuilding());
        cell.setBuilding(null);
    }

    private void removePeople(Cell cell, Person person) {
        if(person.getHp()<=0 || cell.getTexture().equals(Texture.PETROLEUM)) {
            person.getBuilding().getWorkers().remove(person);
            currentGovernment.getPeople().remove(person);
            cell.getPeople().remove(person);
        }
    }

    public ArrayList<Government> getGovernments() {
        return governments;
    }

    public GameMenu getGameMenu() {
        return gameMenu;
    }
    private static void findPath(boolean[][] table, int startX, int startY, int endX, int endY,
                                 ArrayList<Integer> pathX, ArrayList<Integer> pathY) {
        if (startX < 0 || startX >= table.length || startY < 0 || startY >= table[0].length
                || endX < 0 || endX >= table.length || endY < 0 || endY >= table[0].length) {
            throw new IllegalArgumentException("Start or end coordinates are out of bounds!");
        }
        boolean[][] visited = new boolean[table.length][table[0].length];
        if (findPathHelper(table, startX, startY, endX, endY, visited, pathX, pathY)) {
            reverseList(pathX);
            reverseList(pathY);
        } else {
            pathX.clear();
            pathY.clear();
        }
    }
    private static boolean findPathHelper(boolean[][] table, int currX, int currY, int endX, int endY,
                                          boolean[][] visited, ArrayList<Integer> pathX, ArrayList<Integer> pathY) {
        if (currX == endX && currY == endY) {
            pathX.add(currX);
            pathY.add(currY);
            return true;
        }
        if (currX >= 0 && currX < table.length && currY >= 0 && currY < table[0].length
                && !visited[currX][currY] && table[currX][currY]) {
            visited[currX][currY] = true;
            if (findPathHelper(table, currX+1, currY, endX, endY, visited, pathX, pathY)) {
                pathX.add(currX);
                pathY.add(currY);
                return true;
            }
            if (findPathHelper(table, currX-1, currY, endX, endY, visited, pathX, pathY)) {
                pathX.add(currX);
                pathY.add(currY);
                return true;
            }
            if (findPathHelper(table, currX, currY+1, endX, endY, visited, pathX, pathY)) {
                pathX.add(currX);
                pathY.add(currY);
                return true;
            }
            if (findPathHelper(table, currX, currY-1, endX, endY, visited, pathX, pathY)) {
                pathX.add(currX);
                pathY.add(currY);
                return true;
            }
            visited[currX][currY] = false;
        }
        return false;
    }
    private static <T> void reverseList(ArrayList<T> list) {
        int size = list.size();
        for (int i = 0; i < size / 2; i++) {
            T temp = list.get(i);
            list.set(i, list.get(size - i - 1));
            list.set(size - i - 1, temp);
        }
    }
    public void prepareHelp(boolean[][] help){
        for(int i=0;i<currentMap.getSize();i++){
            for(int j=0;j<currentMap.getSize();j++){
                if(currentMap.getACell(i,j).getTexture()== Texture.SEE||currentMap.getACell(i,j).getTexture()== Texture.SHALLOW_WATER||
                        currentMap.getACell(i,j).getTexture()== Texture.ROCK||currentMap.getACell(i,j).getTexture()== Texture.RIVER||
                        currentMap.getACell(i,j).getTexture()== Texture.SMALL_POUND||
                        currentMap.getACell(i,j).getTexture()== Texture.LARGE_POUND||currentMap.getACell(i,j).getBuilding()!=null){
                    help[i][j]=false;
                }
                else help[i][j]=true;
            }
        }
    }
}
