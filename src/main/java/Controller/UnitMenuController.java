package Controller;

import Model.gameandbattle.Government;
import Model.gameandbattle.battle.Patrol;
import Model.gameandbattle.battle.Person;
import Model.gameandbattle.battle.Troop;
import Model.gameandbattle.map.Map;
import Model.gameandbattle.map.Texture;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class UnitMenuController {
    private Government government;
    private Map map;
    private ArrayList<Person> currentUnit;
    private int x;
    private int y;

    public UnitMenuController(ArrayList<Person> currentUnit, int x, int y) {
        this.currentUnit = currentUnit;
        this.x = x;
        this.y = y;
    }

    public String moveUnit(Matcher matcher) {
        int x1 = Integer.parseInt(matcher.group("x"));
        int y1 = Integer.parseInt(matcher.group("y"));
        boolean help[][] = new boolean[map.getSize()][map.getSize()];
        prepareHelp(help);
        ArrayList<Integer> pathX=new ArrayList<>(); ArrayList<Integer> pathY=new ArrayList<>();
        findPath(help,x,y,x1,y1,pathX,pathY);
        if(pathX.size()==0) return "there is no path between these points";
        else{
            int counter=0;
            for(Person person:currentUnit) {
                if(person.getGovernment().getRuler().getUsername().equals(government.getRuler().getUsername())) {
                    map.getACell(x, y).getPeople().remove(counter);
                    int speed = 100;
                    if ((person instanceof Troop)) speed = ((Troop) person).getSpeed();
                    x = pathX.get(Math.min(pathX.size() - 1, speed / 25));
                    y = pathY.get(Math.min(pathY.size() - 1, speed / 25));
                    map.getACell(x, y).getPeople().add(person);
                }
                counter++;
            }
        }
        return "troop moved successfully";
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
    public String patrolUnit(Matcher matcher){
        int x1=Integer.parseInt(matcher.group("x"));
        int y1=Integer.parseInt(matcher.group("y"));
        ArrayList<Integer> pathX=new ArrayList<>(); ArrayList<Integer> pathY=new ArrayList<>();
        boolean[][] help=new boolean[map.getSize()][map.getSize()];
        findPath(help,x,y,x1,y1,pathX,pathY);
        Patrol patrol=new Patrol(x,y,x1,y1);
        if(pathX.size()==0) return "there is no path between these points";
        for(Person person:currentUnit) {
            if(person.getGovernment().getRuler().getUsername().equals(government.getRuler().getUsername())) person.setPatrol(patrol);
        }
        return "done!";
    }
    public String stopPatrol(){
        for(Person person:currentUnit) {
            if(person.getGovernment().getRuler().equals(government.getRuler().getUsername())) person.setPatrol(null);
        }
        return "done";
    }
    public String attack(Matcher matcher){
        return  null;
    }
    public String skyAttack(Matcher matcher){
        return null;
    }
    public String pourOil(Matcher matcher){
        return null;
    }
    public String digTunnel(Matcher matcher) {
        return null;

    }
    public String buildSurroundings(Matcher matcher){
        return null;
    }
    public String disbandUnit(Matcher matcher){
        return null;
    }
    public void prepareHelp(boolean[][] help){
        for(int i=0;i<map.getSize();i++){
            for(int j=0;j<map.getSize();j++){
                if(map.getACell(i,j).getTexture()== Texture.SEE||map.getACell(i,j).getTexture()== Texture.SHALLOW_WATER||
                        map.getACell(i,j).getTexture()== Texture.ROCK||map.getACell(i,j).getTexture()== Texture.RIVER||
                        map.getACell(i,j).getTexture()== Texture.SMALL_POUND||
                        map.getACell(i,j).getTexture()== Texture.LARGE_POUND||map.getACell(i,j).getBuilding()!=null){
                    help[i][j]=false;
                }
                else help[i][j]=true;
            }
        }
    }

}
