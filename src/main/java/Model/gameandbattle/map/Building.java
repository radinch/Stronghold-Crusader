package Model.gameandbattle.map;

import Model.gameandbattle.Government;
import Model.gameandbattle.stockpile.Resource;
import Model.gameandbattle.battle.Person;

import java.util.ArrayList;

public class Building {
    private Government government;
    private int gold;
    private String name;
    private int hitpoint;
    private Resource resourceRequired;
    private int amountOfResource;
    private ArrayList<Person> workers;
    private ArrayList<Texture> allowedTextures;
    private int popularityIncreaseRate;
    private ArrayList<Cell> occupiedCells;

    public Building(Government government, int gold, String name, int hitpoint, Resource resourceRequired,int amountOfResource, int amountOfWorkers, ArrayList<Texture> textures, ArrayList<Cell> occupiedCells) {
        this.government = government;
        this.gold = gold;
        this.name = name;
        this.hitpoint = hitpoint;
        this.resourceRequired = resourceRequired;
        workers=new ArrayList<>();
        allowedTextures=textures;
        this.occupiedCells=occupiedCells;
        this.amountOfResource=amountOfResource;
    }

    public Building getBuildingByName(String name){
        return null;
    }
    public void makeAffect(int x, int y, Map map){

    }
    public void whenBuildingIsSelected(int x, int y, Map map){
        //population
    }
    ////////setters and getters

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHitpoint() {
        return hitpoint;
    }

    public void setHitpoint(int hitpoint) {
        this.hitpoint = hitpoint;
    }

    public Resource getResourcesRequired() {
        return resourceRequired;
    }

    public void setResourcesRequired(Resource resourceRequired) {
        this.resourceRequired = resourceRequired;
    }

    public ArrayList<Person> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<Person> workers) {
        this.workers = workers;
    }

    public Resource getResourceRequired() {
        return resourceRequired;
    }

    public void setResourceRequired(Resource resourceRequired) {
        this.resourceRequired = resourceRequired;
    }

    public ArrayList<Texture> getAllowedTextures() {
        return allowedTextures;
    }

    public void setAllowedTextures(ArrayList<Texture> allowedTextures) {
        this.allowedTextures = allowedTextures;
    }

    public ArrayList<Cell> getOccupiedCells() {
        return occupiedCells;
    }

    public void setOccupiedCells(ArrayList<Cell> occupiedCells) {
        this.occupiedCells = occupiedCells;
    }
}
