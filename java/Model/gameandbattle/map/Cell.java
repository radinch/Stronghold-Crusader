package Model.gameandbattle.map;

import Model.buildings.BuildingImage;
import Model.gameandbattle.Government;
import Model.gameandbattle.battle.Person;
import Model.gameandbattle.battle.Troop;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private Texture texture;
    private Building building;
    private ArrayList<Person> people;
    private boolean isDitchUnderConstruction;
    private int turnCounterForDitch;
    private boolean isDitch;
    private Tree tree;
    private Wall wall;
    private Stair stair;
    private Tunnel tunnel;
    private String toPrint;
    private BuildingImage buildingImage = null;
    private HBox queues = new HBox();
    private char detail;
    public Cell(Texture texture, Building building, ArrayList<Person> people) {
        this.texture = texture;
        this.building = building;
        isDitch=false;
        detail='#';
        toPrint = "##" + detail + "##";
        this.people = new ArrayList<>();
        turnCounterForDitch =0;
    }

    public Tunnel getTunnel() {
        return tunnel;
    }

    public void setTunnel(Tunnel tunnel) {
        this.tunnel = tunnel;
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

    public boolean hasTower(Government government) {
        return building.getName().contains("tower") && building.getGovernment().equals(government);
    }
    public boolean hasWall(){
        return wall != null;
    }
    public Wall getWall(){
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
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

    public boolean isDitchUnderConstruction() {
        return isDitchUnderConstruction;
    }
    public void cancelDitch() {
        isDitchUnderConstruction = false;
        turnCounterForDitch = 0;
    }
    public void setDitch() {
        isDitch = true;
        isDitchUnderConstruction = false;
        turnCounterForDitch =0;
    }
    public void digDitch(){
        isDitchUnderConstruction = true;
    }

    public void removeDitch() {
        isDitch = false;
    }
    public void burnOil(){

    }
    public void fillHole(){

    }

    public int getTurnCounterForDitch() {
        return turnCounterForDitch;
    }

    public void setTurnCounterForDitch(int turnCounterForDitch) {
        this.turnCounterForDitch = turnCounterForDitch;
    }

    public void addUnit(Troop troop) {
        people.add(troop);
    }

    public ArrayList<Integer> getCellCoordinate(Map map) {
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                if(map.getACell(i,j).equals(this))
                    return new ArrayList<>(List.of(i,j));
            }
        }
        return null;
    }

    public void setStair(Stair stair) {
        this.stair = stair;
    }
    public BuildingImage getBuildingImage() {
        return buildingImage;
    }

    public void setBuildingImage(BuildingImage buildingImage) {
        this.buildingImage = buildingImage;
    }

    public HBox getQueues() {
        return queues;
    }

    public void setQueues(HBox queues) {
        this.queues = queues;
    }
}
