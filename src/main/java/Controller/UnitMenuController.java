package Controller;

import Model.gameandbattle.Government;
import Model.gameandbattle.battle.Catapult;
import Model.gameandbattle.battle.Patrol;
import Model.gameandbattle.battle.Person;
import Model.gameandbattle.battle.Troop;
import Model.gameandbattle.map.Map;
import Model.gameandbattle.map.Texture;
import Model.gameandbattle.map.Tunnel;

import java.net.PortUnreachableException;
import Model.gameandbattle.battle.*;
import Model.gameandbattle.map.Map;
import Model.gameandbattle.map.Texture;

import java.util.*;
import java.util.regex.Matcher;

import static Model.gameandbattle.map.Texture.*;

public class UnitMenuController {
    private final Government government;
    private Map map;
    private final ArrayList<Person> currentUnit;
    private int x;
    private int y;

    public UnitMenuController(ArrayList<Person> currentUnit, int x, int y,Government government) {
        this.currentUnit = currentUnit;
        this.x = x;
        this.y = y;
        this.government = government;
    }

    public String moveUnit(Matcher matcher, Map map) {
        int finalX = x;
        int finalY = y;
        this.map = map;
        int x1 = Integer.parseInt(matcher.group("y"));
        int y1 = Integer.parseInt(matcher.group("x"));
        boolean[][] help = new boolean[map.getSize()][map.getSize()];
        prepareHelp(help);
        ArrayList<Integer> pathX=new ArrayList<>(); ArrayList<Integer> pathY=new ArrayList<>();
        //findPath(help,x,y,x1,y1,pathX,pathY);
        aStarSearch(help,x,y,x1,y1,pathX,pathY,map.getSize(),map.getSize());
        //System.out.println(pathY);
        if(pathX.size()==0) return "there is no path between these points";
        else{
            for(int i=currentUnit.size()-1;i>=0;i--) {
                //System.out.println(person.getGovernment().getRuler().getUsername());
                if(currentUnit.get(i).getGovernment().getRuler().getUsername().equals(government.getRuler().getUsername())) {
                    int speed = 100;
                    if ((currentUnit.get(i) instanceof Troop)) speed = ((Troop) currentUnit.get(i)).getSpeed();
                    System.out.println(pathX.size());
                    x = pathX.get(Math.min(pathX.size() - 1, speed/25));
                    y = pathY.get(Math.min(pathY.size() - 1, speed/25));
                    map.getACell(x, y).getPeople().add(currentUnit.get(i));
                    currentUnit.remove(i);
                }
            }
        }
        return "troop moved successfully";
    }
    /*private static void findPath(boolean[][] table, int startX, int startY, int endX, int endY,

    public String setCondition(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        int con = 0;
        String condition = matcher.group("condition");
        if (condition.equals("defensive")) con = 1;
        if (condition.equals("standing")) con = 2;
        if (condition.equals("offensive")) con = 3;
        for (Person person : map.getACell(y, x).getPeople()) {
            if (person.getGovernment().getRuler().getUsername().equals(government.getRuler().getUsername()) && person instanceof Troop) {
                ((Troop) person).setState(con);
            }
        }
        return "successful";
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
    }*/
    private static boolean findPathHelper(boolean[][] table, int currX, int currY, int endX, int endY,
                                          boolean[][] visited, ArrayList<Integer> pathX, ArrayList<Integer> pathY) {
        //System.out.println("x: " + currX + " y: " + currY+" end x :"+endX+" end y: "+endY);
        if (currX == endX && currY == endY) {
            //System.out.println("salam");
            pathX.add(currX);
            pathY.add(currY);
            return true;
        }
        if (currX >= 0 && currX < table.length && currY >= 0 && currY < table[0].length
                && !visited[currX][currY] && table[currX][currY]) {
            visited[currX][currY] = true;
            if (findPathHelper(table, currX + 1, currY, endX, endY, visited, pathX, pathY)) {
                pathX.add(currX);
                pathY.add(currY);
                return true;
            }
            if (findPathHelper(table, currX - 1, currY, endX, endY, visited, pathX, pathY)) {
                pathX.add(currX);
                pathY.add(currY);
                return true;
            }
            if (findPathHelper(table, currX, currY + 1, endX, endY, visited, pathX, pathY)) {
                pathX.add(currX);
                pathY.add(currY);
                return true;
            }
            if (findPathHelper(table, currX, currY - 1, endX, endY, visited, pathX, pathY)) {
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

    public String patrolUnit(Matcher matcher) {
        int x1 = Integer.parseInt(matcher.group("y"));
        int y1 = Integer.parseInt(matcher.group("x"));
        ArrayList<Integer> pathX = new ArrayList<>();
        ArrayList<Integer> pathY = new ArrayList<>();
        boolean[][] help = new boolean[map.getSize()][map.getSize()];
        //findPath(help,x,y,x1,y1,pathX,pathY);
        aStarSearch(help, x, y, x1, y1, pathX, pathY, map.getSize(), map.getSize());
        Patrol patrol = new Patrol(x, y, x1, y1);
        if (pathX == null) return "there is no path between these points";
        for (Person person : currentUnit) {
            if (person.getGovernment().getRuler().getUsername().equals(government.getRuler().getUsername()))
                person.setPatrol(patrol);
        }
        return "done!";
    }

    public String stopPatrol() {
        for (Person person : currentUnit) {
            if (person.getGovernment().getRuler().equals(government.getRuler().getUsername())) person.setPatrol(null);
        }
        return "done";
    }

    public String attack(Matcher matcher) {
        int enemyX = Integer.parseInt(matcher.group("y"));
        int enemyY = Integer.parseInt(matcher.group("x"));
        if (!isEnemyClose(((Troop) currentUnit.get(0)).getAttackRange(), enemyX, enemyY))
            return "enemy is not close";
        for (Person person : currentUnit) {
            moveUnitWithOnePerson(person, enemyX, enemyY);
        }
        for (Person person : map.getACell(enemyX, enemyY).getPeople()) {
            for (Person person1 : currentUnit) {
                int attackStrength = ((Troop) person1).getAttackStrength();
                if (person instanceof Troop && ((Troop) person).getState() == 2) attackStrength = attackStrength / 2;
                person.setHp(person.getHp() - attackStrength);
                int strength = ((Troop) person).getDefenseStrength();
                if (((Troop) person1).getState() == 2) strength = strength / 2;
                person1.setHp(person1.getHp() - strength);
            }
        }
        shotBuildingAndWall(enemyX,enemyY);
        /*for (Person person : map.getACell(enemyX, enemyY).getPeople()) {
            if(!person.getGovernment().equals(government))

        }*/
        return "succsess";
    }

    public String skyAttack(Matcher matcher) {
        int attackStrength;
        int enemyX = Integer.parseInt(matcher.group("y"));
        int enemyY = Integer.parseInt(matcher.group("x"));
        if (!isEnemyClose(((Troop) currentUnit.get(0)).getAttackRange(), enemyX, enemyY))
            return "enemy is not close";
        if (getCountOfAirAttackers() == 0)
            return "you can't do an airstrike with this troops";
        shotPeople(enemyX, enemyY);
        shotBuildingAndWall(enemyX, enemyY);
        return "success";
    }

    public void shotPeople(int enemyX, int enemyY) {
        int attackStrength;
        for (Person person : map.getACell(enemyX, enemyY).getPeople()) {
            for (Person unit : currentUnit) {
                if (isAirAttacker(unit)) {
                    attackStrength = ((Troop) unit).getAttackStrength();
                    if (map.getACell(x, y).hasTower(government))
                        attackStrength *= 2;
                    if (person instanceof Troop && ((Troop) person).getState() == 2)
                        attackStrength = attackStrength / 2;
                    person.setHp(person.getHp() - attackStrength);
                    if (person instanceof Troop && ((Troop) person).getState() == 3) {
                        int strength = ((Troop) person).getDefenseStrength();
                        if (((Troop) unit).getState() == 2) strength = strength / 2;
                        unit.setHp(unit.getHp() - strength);
                    }
                }
            }
        }
    }

    public void shotBuildingAndWall(int enemyX, int enemyY) {
        int attackStrength;
        for (Person unit : currentUnit) {
            attackStrength = ((Troop) unit).getAttackStrength();
            if (map.getACell(x, y).hasTower(government)&&isAirAttacker(unit))
                attackStrength *= 2;
            if (map.getACell(enemyX, enemyY).getBuilding() != null) {
                map.getACell(enemyX, enemyY).getBuilding().setHitpoint(map.getACell(enemyX, enemyY).getBuilding().getHitpoint() - attackStrength);
                if (unit.getName().equals("Fire Throwers"))
                    map.getACell(enemyX, enemyY).getBuilding().setFiery(true);
            }
            if (map.getACell(enemyX, enemyY).hasWall())
                map.getACell(enemyX, enemyY).getWall().setHitpoint(map.getACell(enemyX, enemyY).getWall().getHitpoint() - attackStrength);
        }
    }

    private boolean isEnemyClose(int attackRange, int enemyX, int enemyY) {
        if (attackRange + x < enemyX || attackRange + y < enemyY)
            return false;
        return x - attackRange <= enemyX && y - attackRange <= enemyY;
    }

    private int getCountOfAirAttackers() {
        int count = 0;
        ArrayList<String> airAttackers = new ArrayList<>(List.of("Crossbowmen", "Archer Bow", "Horse Archers", "Slingers", "Archer", "Fire Throwers"));
        for (Person person : currentUnit) {
            if (airAttackers.contains(person.getName()))
                count++;
        }
        return count;
    }

    public boolean isAirAttacker(Person person) {
        ArrayList<String> airAttackers = new ArrayList<>(List.of("Crossbowmen", "Archer Bow", "Horse Archers", "Slingers", "Archer", "Fire Throwers"));
        return airAttackers.contains(person.getName());
    }

    public String digTunnel(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));
        if ((map.getACell(x, y).getBuilding() == null && map.getACell(x, y).getWall() == null) || map.getACell(x, y).getBuilding().getGovernment().getRuler().getUsername().equals(government.getRuler().getUsername()))
            return "there is no defensive structure here";
        if (!isTunnelCoordinateValid(x, y)) return "invalid coordinates!";
        if (currentUnit == null) return "no units";
        boolean flag = false;
        int counter = 0;
        Troop troop;
        for (Person person : currentUnit) {
            if (person.getName().equals("Tunneler")) {
                flag = true;
                moveUnitWithOnePerson(person, x, y);
                break;
            }
        }
        if (!flag) return "no tunnelers!";
        Tunnel tunnel = new Tunnel(0);
        map.getACell(x, y).setTunnel(tunnel);

        return "done!";
    }

    private boolean isTunnelCoordinateValid(int x, int y) {
        if (!DataBank.getCastleBuildingTextures().contains(map.getACell(x, y).getTexture())) return false;
        if (map.getACell(x, y).getBuilding().getName().contains("tower")) return false;
        if (map.getACell(x, y).isDitch()) return false;
        return true;
    }

    public String digDitch(Matcher matcher) {
        Person spearman = null;
        int ditchX = Integer.parseInt(matcher.group("y"));
        int ditchY = Integer.parseInt(matcher.group("x"));
        for (Person person : currentUnit) {
            if (person.getName().equals("Spearmen"))
                spearman = person;
        }
        if (spearman == null)
            return "you dont have Spearmen in this cell";
        if (!isCellProperForDitch(ditchX, ditchY))
            return "you cant dig ditch in this cell";
        if (moveUnitWithOnePerson(spearman, ditchX, ditchY).equals("fail"))
            return "there is no path to move this unit";
        map.getACell(ditchX, ditchY).digDitch();
        return "ditch is under construction";
    }

    public String cancelDitch(Matcher matcher) {
        int ditchX = Integer.parseInt(matcher.group("y"));
        int ditchY = Integer.parseInt(matcher.group("x"));
        if (!map.getACell(ditchX, ditchY).isDitchUnderConstruction())
            return "there is not a ditch under construction in this cell";
        map.getACell(ditchX, ditchY).cancelDitch();
        return "success";
    }

    public String removeDitch(Matcher matcher) {
        int ditchX = Integer.parseInt(matcher.group("y"));
        int ditchY = Integer.parseInt(matcher.group("x"));
        if (!map.getACell(ditchX, ditchY).isDitch())
            return "there is no ditch in this cell";
        map.getACell(ditchX, ditchY).removeDitch();
        return "success";
    }

    private boolean isCellProperForDitch(int x, int y) {
        if (map.getACell(x, y).getBuilding() != null)
            return false;
        if (map.getACell(x, y).getPeople().size() != 0)
            return false;
        if (!DataBank.getCastleBuildingTextures().contains(map.getACell(x, y).getTexture()))
            return false;
        if (map.getACell(x, y).hasStair())
            return false;
        if (map.getACell(x, y).isDitch())
            return false;
        return !map.getACell(x, y).hasWall();
    }

    public String disbandUnit() {
        for (Person person : currentUnit) {
            if (person.getGovernment().equals(government)) {
                person.getBuilding().getWorkers().remove(person);
                map.getACell(x, y).getPeople().remove(person);
                if (person.getGovernment().getKing().getHp() > 0)
                    person.getGovernment().addPerson();
            }
        }
        return "success";
    }
    public void prepareHelp(boolean[][] help){
        for(int i=0;i<map.getSize();i++){
            for(int j=0;j<map.getSize();j++){
                if(map.getACell(i,j).getTexture()== Texture.SEE||map.getACell(i,j).getTexture()== SHALLOW_WATER||
                        map.getACell(i,j).getTexture()== Texture.ROCK||map.getACell(i,j).getTexture()== RIVER||
                        map.getACell(i,j).getTexture()== Texture.SMALL_POUND||
                        map.getACell(i,j).getTexture()== Texture.LARGE_POUND){
                    help[i][j]=false;
                }
                else
                {
                    help[i][j]=true;
                    if(map.getACell(i,j).hasWall())
                    {
                        if(!map.getACell(i,j).getWall().isAccessible())
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
    private String moveUnitWithOnePerson(Person person,int x1,int y1){
        boolean help[][] = new boolean[map.getSize()][map.getSize()];
        prepareHelp(help);
        int finalX=x;
        int finalY=y;
        ArrayList<Integer> pathX=new ArrayList<>(); ArrayList<Integer> pathY=new ArrayList<>();
        //findPath(help,x,y,x1,y1,pathX,pathY);
        aStarSearch(help,x,y,x1,y1,pathX,pathY,map.getSize(),map.getSize());
        if(pathX.size()==0) return "fail";
        else{
            int counter=0;
            for(Person person1:currentUnit) {
                //System.out.println(person.getGovernment().getRuler().getUsername());
                if(person1.getGovernment().getRuler().getUsername().equals(government.getRuler().getUsername())&&
                        person1.getName().equals(person.getName()) && person1.getHp()==person.getHp()) {
                    map.getACell(finalX, finalY).getPeople().remove(counter);
                    int speed = 100;
                    if ((person instanceof Troop)) speed = ((Troop) person).getSpeed();
                    finalX = pathX.get(Math.min(pathX.size() - 1, speed * 100));
                    finalY = pathY.get(Math.min(pathY.size() - 1, speed * 100));
                    map.getACell(x, y).getPeople().add(person);
                    return "done";
                }
                counter++;
            }
        }
        return "fail";
    }

    public String pourOil(Matcher matcher){
        String direction = matcher.group("direction");
        Person engineer = null;
        for (Person person : currentUnit) {
            if (person.getName().equals("Engineer")) {
                engineer = person;
                break;
            }
        }
        if(engineer == null)
            return "you dont have engineer in this cell";
        if(!((Troop) engineer).hasOil())
            return "you should first equip this engineer with oil";
        if(!isEnemyCloseToEngineer(engineer))
            return "enemy is not close to engineer";
        switch (direction) {
            case "up" -> {
                if (x + 1 <= map.getSize()) {
                    if (!isCellOkForOil(x + 1, y))
                        return "you can not pour oil in this cell";
                    map.getACell(x + 1, y).setTexture(PETROLEUM);
                }
                else
                    return "invalid direction";
            }
            case "down" -> {
                if (x - 1 >= 0) {
                    if (!isCellOkForOil(x - 1, y))
                        return "you can not pour oil in this cell";
                    map.getACell(x - 1, y).setTexture(PETROLEUM);
                }
                else
                    return "invalid direction";
            }
            case "left" -> {
                if (y - 1 >= 0) {
                    if (!isCellOkForOil(x, y - 1))
                        return "you can not pour oil in this cell";
                    map.getACell(x, y - 1).setTexture(PETROLEUM);
                }
                else
                    return "invalid direction";
            }
            case "right" -> {
                if (y + 1 <= map.getSize()) {
                    if (!isCellOkForOil(x, y + 1))
                        return "you can not pour oil in this cell";
                    map.getACell(x, y + 1).setTexture(PETROLEUM);
                }
                else
                    return "invalid direction";
            }
            default -> {
                return "invalid direction";
            }
        }
        return "success";
    }

    public String equipWithOil() {
        if(government.getBuildingByName("oil smelter") == null)
            return "you dont have oil smelter";
        Person engineer = null;
        for (Person person : currentUnit) {
            if (person.getName().equals("Engineer")) {
                engineer = person;
                break;
            }
        }
        if(engineer == null)
            return "you dont have engineer in this cell";
        if(((Troop) engineer).hasOil())
            return "this engineer had equipped with oil before";
        moveUnitWithOnePerson(engineer,government.getBuildingByName("oil smelter").getOccupiedCell().getCellCoordinate(map).get(0),
                government.getBuildingByName("oil smelter").getOccupiedCell().getCellCoordinate(map).get(1));
        ((Troop) engineer).equipWithOil();
        return "success";
    }

    private boolean isEnemyCloseToEngineer(Person engineer) {
        int counter =0;
        for (int i = x-2; i <x+2 ; i++) {
            for (int j = y-2; j <y+2 ; j++) {
                if (isCoordinateValid(i,j, map.getSize())) {
                    if(getCountOfEnemy(i,j)>=4-((Troop) engineer).getState())
                        return true;
                }
            }
        }
        return false;
    }

    private int getCountOfEnemy(int x,int y) {
        int count = 0;
        for (Person person : map.getACell(x, y).getPeople()) {
            if(!person.getGovernment().equals(government))
                count++;
        }
        return count;
    }

    private boolean isCellOkForOil(int x,int y) {
        ArrayList<Texture> notAllowedTextures = new ArrayList<Texture>(List.of(SHALLOW_WATER,RIVER,SMALL_POUND,LARGE_POUND,METAL,ROCK,ROCKY_GROUND,SEE));
        if(map.getACell(x,y).getWall() != null ||
                map.getACell(x,y).getStair() != null ||
                map.getACell(x,y).getBuilding() != null) {
            return false;
        }
        else if(map.getACell(x,y).isDitch())
            return false;
        else return !notAllowedTextures.contains(map.getACell(x, y).getTexture());
    }
    public String buildSurroundings(Matcher matcher){
        String equipmentName = matcher.group("equipment");
        ArrayList<Troop> engineers = new ArrayList<>();
        int count = 0;
        for (Person person : currentUnit) {
            if(person.getName().equals("Engineer") && person.getGovernment().equals(government)) {
                count++;
                engineers.add((Troop) person);
            }
        }
        switch (equipmentName) {
            case "fiery catapult" -> {
                if (count >= 2) {
                    Catapult fieryCatapult = new Catapult("fiery catapult", 200, government, true, null, 200, 200, 200, 10, 10, new ArrayList<>());
                    ((Catapult) addEquipment(fieryCatapult)).setFiery(true);
                    addAndRemoveEngineers(engineers, 2);
                } else
                    return "not enough engineer";
            }
            case "catapult" -> {
                if (count >= 2) {
                    Catapult catapult = new Catapult("catapult", 200, government, true, null, 200, 200, 200, 10, 10, new ArrayList<>());
                    addEquipment(catapult);
                    addAndRemoveEngineers(engineers, 2);
                } else
                    return "not enough engineer";
            }
            case "siege tower" -> {
                if (count >= 4) {
                    Troop siegeTower = (Troop) DataBank.getUnitByName("siege tower");
                    addEquipment(siegeTower);
                    addAndRemoveEngineers(engineers, 4);
                } else
                    return "not enough engineer";
            }
            case "portable shields" -> {
                if (count >= 1) {
                    Troop portableShields = (Troop) DataBank.getUnitByName("portable shields");
                    addEquipment(portableShields);
                    addAndRemoveEngineers(engineers, 1);
                } else
                    return "not enough engineer";
            }
            case "battering ram" -> {
                if (count >= 4) {
                    Troop batteringRam = (Troop) DataBank.getUnitByName("battering ram");
                    addEquipment(batteringRam);
                    addAndRemoveEngineers(engineers, 4);
                } else
                    return "not enough engineer";
            }
            case "catapult with balance weight" -> {
                if (count >= 3) {
                    Catapult catapult = new Catapult("catapult with balance weight", 200, government, true, null, 400, 200, 200, 20, 10, new ArrayList<>());
                    ((Catapult) addEquipment(catapult)).setFiery(true);
                    ((Catapult) addEquipment(catapult)).setCanMove(true);
                    addAndRemoveEngineers(engineers, 3);
                } else
                    return "not enough engineer";
            }
        }
        return "success";
    }


    private void addAndRemoveEngineers(ArrayList<Troop> engineers,int count) {
        for (int i=0;i<count;i++) {
            government.getPeople().remove(engineers.get(i));
            engineers.get(i).getBuilding().getWorkers().remove(engineers.get(i));
            engineers.get(i).getBuilding().getOccupiedCell().getPeople().remove(engineers.get(i));
        }
    }

    public Troop addEquipment(Troop troop) {
        Troop newTroop= new Troop(troop.getName(), troop.getHp(), government, troop.isBusy(), null,
                troop.getAttackStrength(), troop.getSpeed(), troop.getDefenseStrength(), troop.getAttackRange(),
                troop.getCost(), troop.getWeapons());
        government.addUnit(newTroop);
        map.getACell(x,y).addUnit(newTroop);
        government.setCoin(government.getCoin() - 10);
        return newTroop;
    }

    private boolean isCoordinateValid(int x, int y, int mapSize) {
        return x >= 0 && y >= 0 && x < mapSize && y < mapSize;
    }

    //FIND PATH METHODS start
    // TODO : HOW TO USE
    /**
     *
     * first you should create a 2d boolean for grid(false = blocked,true = accessible)
     * you should call the method like this
     * aStarSearch(boolean[][] grid, int srcX, int srcY, int destX, int destY
     * , ArrayList<Integer> PathX,ArrayList<Integer> PathY,int ROW,int COL);
     * src is the start and des is the target
     * pathX and pathY would be automatically filled including the start location and target location
     * ROW AND PATH are just map size
     * if eny error occurs the pathX and pathY will be null
     * so you should check for errors before calling this method
     **/
    private Pair make_pair(int x,int y)
    {
        return new Pair(x,y);
    }
    private pPair make_pair(double x,Pair y)
    {
        return new pPair(x,y);
    }

    private boolean isValid(int row, int col,int ROW,int COL)
    {
        return (row >= 0) && (row < ROW) && (col >= 0)
                && (col < COL);
    }
    private boolean isUnBlocked(boolean[][] grid, int row, int col)
    {
        return grid[row][col];
    }
    private boolean isDestination(int row, int col, int destX,int destY)
    {
        return row == destX && col == destY;
    }
    private double calculateHValue(int row, int col, int destX,int destY)
    {
        return (Math.abs(row - destX) + Math.abs(col - destY));
    }
    private void tracePath(cellForPath[][] cellDetails, int destX, int destY,ArrayList<Integer> PathX,ArrayList<Integer> PathY)
    {
        int row = destX;
        int col = destY;

        Stack<Pair> Path = new Stack<>();

        while (!(cellDetails[row][col].parent_i == row
                && cellDetails[row][col].parent_j == col)) {
            Path.push(make_pair(row, col));
            int temp_row = cellDetails[row][col].parent_i;
            int temp_col = cellDetails[row][col].parent_j;
            row = temp_row;
            col = temp_col;
        }

        Path.push(make_pair(row, col));
        while (!Path.empty()) {
            Pair p = Path.peek();
            Path.pop();
            PathX.add(p.first);
            PathY.add(p.second);
        }
    }
    public void aStarSearch(boolean[][] grid, int srcX, int srcY, int destX, int destY, ArrayList<Integer> PathX,ArrayList<Integer> PathY,int ROW,int COL)
    {
        if (!isValid(srcX, srcY,ROW,COL)) {
            return;
        }
        if (!isValid(destX, destY,ROW,COL)) {
            return;
        }
        if (!isUnBlocked(grid, srcX, srcY)
                || !isUnBlocked(grid, destX, destY)) {
            return;
        }
        if (isDestination(srcX, srcY, destX, destY)) {
            return;
        }
        boolean[][] closedList = new boolean[ROW][COL];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                closedList[i][j] = false;
            }
        }
        cellForPath[][] cellDetails = new cellForPath[ROW][COL];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                cellDetails[i][j] = new cellForPath();
                cellDetails[i][j].f = Float.MAX_VALUE;
                cellDetails[i][j].g = Float.MAX_VALUE;
                cellDetails[i][j].h = Float.MAX_VALUE;
                cellDetails[i][j].parent_i = -1;
                cellDetails[i][j].parent_j = -1;
            }
        }
        int i = srcX;
        int j = srcY;
        cellDetails[i][j].f = 0.0;
        cellDetails[i][j].g = 0.0;
        cellDetails[i][j].h = 0.0;
        cellDetails[i][j].parent_i = i;
        cellDetails[i][j].parent_j = j;
        TreeSet<pPair> openList = new TreeSet<>();
        openList.add(make_pair(0.0, make_pair(i, j)));
        while (!openList.isEmpty()) {
            pPair p = openList.first();
            openList.remove(openList.first());
            i = p.second.first;
            j = p.second.second;
            closedList[i][j] = true;
            double gNew, hNew, fNew;

            //----------- 1st Successor (North) ------------

            if (isValid(i - 1, j,ROW,COL)) {
                if (isDestination(i - 1, j, destX,destY)) {
                    // Set the Parent of the destination cell
                    cellDetails[i - 1][j].parent_i = i;
                    cellDetails[i - 1][j].parent_j = j;
                    tracePath(cellDetails, destX,destY,PathX,PathY);
                    return;
                }
                else if (!closedList[i - 1][j]
                        && isUnBlocked(grid, i - 1, j)) {
                    gNew = cellDetails[i][j].g + 1.0;
                    hNew = calculateHValue(i - 1, j, destX,destY);
                    fNew = gNew + hNew;
                    if (cellDetails[i - 1][j].f == Float.MAX_VALUE
                            || cellDetails[i - 1][j].f > fNew) {
                        openList.add(make_pair(
                                fNew, make_pair(i - 1, j)));

                        // Update the details of this cell
                        cellDetails[i - 1][j].f = fNew;
                        cellDetails[i - 1][j].g = gNew;
                        cellDetails[i - 1][j].h = hNew;
                        cellDetails[i - 1][j].parent_i = i;
                        cellDetails[i - 1][j].parent_j = j;
                    }
                }
            }

            //----------- 2nd Successor (South) ------------

            if (isValid(i + 1, j,ROW,COL)) {
                if (isDestination(i + 1, j, destX,destY)) {
                    // Set the Parent of the destination cell
                    cellDetails[i + 1][j].parent_i = i;
                    cellDetails[i + 1][j].parent_j = j;
                    tracePath(cellDetails, destX,destY,PathX,PathY);
                    return;
                }
                else if (!closedList[i + 1][j]
                        && isUnBlocked(grid, i + 1, j)) {
                    gNew = cellDetails[i][j].g + 1.0;
                    hNew = calculateHValue(i + 1, j, destX,destY);
                    fNew = gNew + hNew;
                    if (cellDetails[i + 1][j].f == Float.MAX_VALUE
                            || cellDetails[i + 1][j].f > fNew) {
                        openList.add(make_pair(
                                fNew, make_pair(i + 1, j)));
                        cellDetails[i + 1][j].f = fNew;
                        cellDetails[i + 1][j].g = gNew;
                        cellDetails[i + 1][j].h = hNew;
                        cellDetails[i + 1][j].parent_i = i;
                        cellDetails[i + 1][j].parent_j = j;
                    }
                }
            }

            //----------- 3rd Successor (East) ------------
            if (isValid(i, j + 1,ROW,COL)) {
                if (isDestination(i, j + 1, destX,destY)) {
                    cellDetails[i][j + 1].parent_i = i;
                    cellDetails[i][j + 1].parent_j = j;
                    tracePath(cellDetails, destX,destY,PathX,PathY);
                    return;
                }
                else if (!closedList[i][j + 1]
                        && isUnBlocked(grid, i, j + 1)) {
                    gNew = cellDetails[i][j].g + 1.0;
                    hNew = calculateHValue(i, j + 1, destX,destY);
                    fNew = gNew + hNew;
                    if (cellDetails[i][j + 1].f == Float.MAX_VALUE
                            || cellDetails[i][j + 1].f > fNew) {
                        openList.add(make_pair(
                                fNew, make_pair(i, j + 1)));
                        cellDetails[i][j + 1].f = fNew;
                        cellDetails[i][j + 1].g = gNew;
                        cellDetails[i][j + 1].h = hNew;
                        cellDetails[i][j + 1].parent_i = i;
                        cellDetails[i][j + 1].parent_j = j;
                    }
                }
            }

            //----------- 4th Successor (West) ------------
            if (isValid(i, j - 1,ROW,COL)) {
                if (isDestination(i, j - 1, destX,destY)) {
                    cellDetails[i][j - 1].parent_i = i;
                    cellDetails[i][j - 1].parent_j = j;
                    tracePath(cellDetails, destX,destY,PathX,PathY);
                    return;
                }
                else if (!closedList[i][j - 1]
                        && isUnBlocked(grid, i, j - 1)) {
                    gNew = cellDetails[i][j].g + 1.0;
                    hNew = calculateHValue(i, j - 1, destX,destY);
                    fNew = gNew + hNew;
                    if (cellDetails[i][j - 1].f == Float.MAX_VALUE
                            || cellDetails[i][j - 1].f > fNew) {
                        openList.add(make_pair(
                                fNew, make_pair(i, j - 1)));
                        cellDetails[i][j - 1].f = fNew;
                        cellDetails[i][j - 1].g = gNew;
                        cellDetails[i][j - 1].h = hNew;
                        cellDetails[i][j - 1].parent_i = i;
                        cellDetails[i][j - 1].parent_j = j;
                    }
                }
            }
        }
    }
}

class Pair {
    int first, second;
    Pair(int f, int s) {
        first = f;
        second = s;
    }
}

class pPair implements Comparable<pPair>{
    double first;
    Pair second;
    pPair(double f, Pair s) {
        first = f;
        second = s;
    }

    @Override
    public int compareTo(pPair other) {
        if (Double.compare(this.first, other.first) != 0) {
            return Double.compare(this.first, other.first);
        }
        // compare the second values
        if (Integer.compare(this.second.first, other.second.first) != 0) {
            return Integer.compare(this.second.first, other.second.first);
        }
        // compare the third values
        return Integer.compare(this.second.second, other.second.second);
    }
}

class cellForPath
{
    public int parent_i;
    public int parent_j;
    public double f,g,h;
}
