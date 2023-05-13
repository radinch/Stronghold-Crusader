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
import java.util.List;
import java.util.Scanner;

import static Model.gameandbattle.map.Texture.RIVER;
import static Model.gameandbattle.map.Texture.SHALLOW_WATER;

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
        for (int i=0;i<currentMap.getSize();i++) {
            for (int j = 0; j < currentMap.getSize(); j++) {
                tunnelAbility(currentMap.getACell(i,j));
                if (currentMap.getACell(i, j).getBuilding() != null) {
                    if (currentMap.getACell(i, j).getBuilding().getHitpoint() <= 0)
                        removeBuilding(currentMap.getACell(i, j));
                }
                if (currentMap.getACell(i, j).getBuilding() != null) {
                    currentMap.getACell(i, j).getBuilding().makeAffect(i, j, currentMap);
                    if (currentMap.getACell(i, j).getBuilding().isFiery())
                        removeBuilding(currentMap.getACell(i, j));
                }
                if (currentMap.getACell(i, j).isDitchUnderConstruction()) {
                    currentMap.getACell(i, j).setTurnCounterForDitch(currentMap.getACell(i, j).getTurnCounterForDitch() + 1);
                    if (currentMap.getACell(i, j).getTurnCounterForDitch() == 2)
                        currentMap.getACell(i, j).setDitch();
                }
                for (int m=currentMap.getACell(i,j).getPeople().size()-1;m>=0;m--) {
                    Person person=currentMap.getACell(i,j).getPeople().get(m);
                    ArrayList<String> airAttackers = new ArrayList<>(List.of("Crossbowmen", "Archer Bow", "Horse Archers", "Slingers", "Archer", "Fire Throwers"));
                    boolean flagAir = false;
                    for (String string : airAttackers) {
                        if (person.getName().equals(string)) flagAir = true;
                    }
                    removePeople(currentMap.getACell(i, j), person);
                    if (person.getPatrol() != null) {
                        Patrol patrol = person.getPatrol();
                        int condition = patrol.getCondition();
                        int x, y, x1, y1;
                        if (condition == 0) {
                            x = patrol.getStartX();
                            y = patrol.getStartY();
                            x1 = patrol.getFinishX();
                            y1 = patrol.getFinishY();
                        } else {
                            x = patrol.getFinishX();
                            y = patrol.getFinishY();
                            x1 = patrol.getStartX();
                            y1 = patrol.getStartY();
                        }
                        ArrayList<Integer> pathX = new ArrayList<>();
                        ArrayList<Integer> pathY = new ArrayList<>();
                        boolean[][] help = new boolean[currentMap.getSize()][currentMap.getSize()];
                        prepareHelp(help);
                        currentMap.getACell(i, j).getPeople().remove(m);
                        System.out.println("start x : "+x+"start y : "+y+"finish x : "+x1+"finish y : "+y1);
                        UnitMenuController.aStarSearch(help,x,y,x1,y1,pathX,pathY,currentMap.getSize(),currentMap.getSize());
                        int speed = 100;
                        if ((person instanceof Troop)) speed = ((Troop) person).getSpeed();
                        x = pathX.get(Math.min(pathX.size() - 1, speed / 25));
                        y = pathY.get(Math.min(pathY.size() - 1, speed / 25));
                        if (x == pathX.get(pathX.size() - 1) && y == pathY.get(pathY.size() - 1))
                            patrol.setCondition(1 - patrol.getCondition());
                        currentMap.getACell(x, y).getPeople().add(person);
                    }
                    if (currentMap.getACell(i, j).getTexture().equals(Texture.PETROLEUM))
                        currentMap.getACell(i, j).setTexture(Texture.GROUND);
                    if (person instanceof Troop&&((Troop) person).getState() == 2) {
                        for (int k = Math.max(i-5,0); k < Math.min(i+5,currentMap.getSize()-1); k++) {
                            for (int q =Math.max(j-5,0); q <Math.min(j+5,currentMap.getSize()-1) ; q++) {
                                for(Person person1:currentMap.getACell(k,q).getPeople()){
                                    if(!person1.getGovernment().getRuler().getUsername().equals(person.getGovernment().getRuler().getUsername())){
                                       person1.setHp(person1.getHp()-((Troop)person).getAttackStrength());
                                    }
                                }
                                if(currentMap.getACell(k,q).getBuilding()!=null&&!currentMap.getACell(k,q).getBuilding().getGovernment().getRuler().getUsername().equals(person.getGovernment().getRuler().getUsername())){
                                    currentMap.getACell(k,q).getBuilding().setHitpoint(currentMap.getACell(k,q).getBuilding().getHitpoint()-((Troop) person).getAttackStrength());
                                }
                            }
                        }
                    }
                    if (person instanceof Troop&&((Troop) person).getState() != 2) {
                        int amountOfMove;
                        if(((Troop) person).getState()==1) amountOfMove=5;
                        else amountOfMove=10;
                        outer:
                        for (int k = Math.max(i-amountOfMove,0); k < Math.min(i+amountOfMove,currentMap.getSize()-1); k++) {
                            for (int q =Math.max(j-amountOfMove,0); q <Math.min(j+amountOfMove,currentMap.getSize()-1) ; q++) {
                                int counter1=0;
                                for(Person person1:currentMap.getACell(k,q).getPeople()){
                                    if(!person1.getGovernment().getRuler().getUsername().equals(person.getGovernment().getRuler().getUsername())){
                                        ArrayList<Integer> pathX = new ArrayList<>();
                                        ArrayList<Integer> pathY = new ArrayList<>();
                                        boolean[][] help = new boolean[currentMap.getSize()][currentMap.getSize()];
                                        prepareHelp(help);
                                        currentMap.getACell(i, j).getPeople().remove(counter1);
                                        UnitMenuController.aStarSearch(help,i,j,k,q,pathX,pathY,currentMap.getSize(),currentMap.getSize());
                                        int speed = 100;
                                        speed = ((Troop) person).getSpeed(); int x;int y;
                                        x = pathX.get(Math.min(pathX.size() - 1, speed / 25));
                                        y = pathY.get(Math.min(pathY.size() - 1, speed / 25));
                                        currentMap.getACell(x, y).getPeople().add(person);
                                        break outer;
                                    }
                                    counter1++;
                                }
                            }
                        }
                    }

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
            government.getGranary().setApple(government.getGranary().getApple() - (1 + government.getFoodRate()*0.5)*government.getPopulation());
            government.getGranary().setCheese(government.getGranary().getCheese() - (1 + government.getFoodRate()*0.5)*government.getPopulation());
            government.getGranary().setBread(government.getGranary().getBread() - (1 + government.getFoodRate()*0.5)*government.getPopulation());
            government.getGranary().setMeat(government.getGranary().getMeat() - (1 + government.getFoodRate()*0.5)*government.getPopulation());
            for(int i=0;i<government.getFoods().length;i++){
                government.getFoods()[i]=government.getFoods()[i]-(1 + government.getFoodRate()*0.5)*government.getPopulation();
            }
            government.setPopulation(government.getMaxPopulation()*government.getPopularity()/100);
            System.out.println(government.getPopulation());
        }
        buildingMenu.setGovernment(currentGovernment); shopMenu.setGovernment(currentGovernment); tradeMenu.setGovernment(currentGovernment);
        unitMenu.setGovernment(currentGovernment); tradeMenu.setGovernment(currentGovernment);

        System.out.println("it's your turn "+currentGovernment.getRuler().getNickname());
    }
    private void tunnelAbility(Cell cell){
        if(cell.getTunnel()==null) return;
        cell.getTunnel().setLength(cell.getTunnel().getLength()+1);
        if(cell.getTunnel().getLength()==Tunnel.limitLength){
            if(cell.getBuilding()!=null) removeBuilding(cell);
        }
        for(Person person:cell.getPeople()) removePeople(cell,person);
        cell.setTunnel(null);
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
                if(currentMap.getACell(i,j).getTexture()== Texture.SEE||currentMap.getACell(i,j).getTexture()== SHALLOW_WATER||
                        currentMap.getACell(i,j).getTexture()== Texture.ROCK||currentMap.getACell(i,j).getTexture()== RIVER||
                        currentMap.getACell(i,j).getTexture()== Texture.SMALL_POUND||
                        currentMap.getACell(i,j).getTexture()== Texture.LARGE_POUND){
                    help[i][j]=false;
                }
                else
                {
                    help[i][j]=true;
                    if(currentMap.getACell(i,j).hasWall())
                    {
                        if(!currentMap.getACell(i,j).getWall().isAccessible())
                        {
                            help[i][j] = false;
                        }
                        else {
                            help[i][j] = true;
                        }
                    }
                }
            }
        }
    }
}
