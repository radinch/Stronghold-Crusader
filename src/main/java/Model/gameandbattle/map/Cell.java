package Model.gameandbattle.map;

import Model.gameandbattle.battle.Person;
import Model.gameandbattle.battle.Troop;

import java.util.ArrayList;

public class Cell {
    private Texture texture;
    private Building building;
    private ArrayList<Person> people;
    private boolean isDitch;
    private Tree tree;
    private Wall wall;
    private Stair stair;
    private Tunnel tunnel;
    private String toPrint;
    private char detail;
    public Cell(Texture texture, Building building, ArrayList<Person> people) {
        this.texture = texture;
        this.building = building;
        isDitch=false;
        detail='#';
        toPrint = "##" + detail + "##";
        this.people = new ArrayList<>();
    }

    public boolean isDitch() {
        return isDitch;
    }
    public boolean isPartOfTunnel() {
        return tunnel != null;
    }
    public boolean hasStair() {
        return stair != null;
    }
    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public Stair getStair() {
        return stair;
    }

    public boolean hasWall(){
        return wall != null;
    }
    public Wall getWall(){
        return wall;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public char getDetail() {
        return detail;
    }

    public void setDetail(char detail) {
        /*String ANSI_PURPLE = "\033[4;35m";
        String ANSI_RESET = "\u001B[0m";*/
        this.detail = detail;
        toPrint="##" + detail + "##";
    }

    public String getToPrint() {
        return toPrint;
    }

    public void digDitch(){

    }
    public void burnOil(){

    }
    public void fillHole(){

    }

    public void addUnit(Troop troop) {
        people.add(troop);
    }
}
