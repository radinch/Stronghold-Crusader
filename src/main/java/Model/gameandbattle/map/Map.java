package Model.gameandbattle.map;

import Controller.DataBank;
import Model.gameandbattle.map.Cell;

import java.util.ArrayList;

public enum Map {

    //map templates
    MAP_NUMBER_ONE(200,"map number one", DataBank.getMapNumberOne()),
    MAP_NUMBER_TWO(0,"map number two",null),
    MAP_NUMBER_THREE(0,"map number tree",null);
    private Cell[][] map;
    private String name;
    private final int size;

    Map(int size,String name,Cell[][] map){
        this.size=size;
        this.map=map;
        this.name=name;
    }

    public Map getMapByNameAndSize(int size,String name) {
        return this;
    }

    public Cell[][] getMapCells() {
        return map;
    }

    public int getSize() {
        return size;
    }
    public Cell getACell(int x,int y){
        return map[x][y];
    }
    public void setACell(int x,int y,Cell cell){
        map[x][y]=cell;
    }
    public void burnAllOil(){

    }
    public void buildWall(int x,int y,int wallLength){

    }
    public void buildStair(int x,int y){

    }
    public boolean isConnectedToWall(int x,int y){
        return false;
    }
    public ArrayList<Stair> stairs(int x,int y){
        return null;
    }
}
