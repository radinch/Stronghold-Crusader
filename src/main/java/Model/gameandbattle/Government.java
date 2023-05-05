package Model.gameandbattle;

import Model.gameandbattle.battle.Person;
import Model.gameandbattle.battle.Troop;
import Model.gameandbattle.map.Building;
import Model.gameandbattle.shop.Request;
import Model.gameandbattle.stockpile.Food;
import Model.gameandbattle.stockpile.Granary;
import Model.gameandbattle.stockpile.Resource;
import Model.gameandbattle.stockpile.Stockpile;
import Model.signup_login_profile.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Government {
    private static final int limitOfResources = 300;
    private int popularity;
    private int foodRate;
    private User ruler;
    private int taxRate;
    private double coin;
    private int fearRate;
    private Stockpile stockpile;
    private Granary granary;
    private int population;
    private int maxPopulation;
    private ArrayList<Building> buildings;
    private String color;
    private Troop king;
    private int workersEfficiency;
    private double[] foods;
    private int[] weapons;
    private ArrayList<Request> requestsMadeByMe;
    private ArrayList<Request> requestsToMe;
    private ArrayList<Request> requestsAcceptedByMe;
    private ArrayList<Request> unseenRequests;
    private boolean isAlive;
    private ArrayList<Person> people;
    public Government(int popularity, int foodRate, User ruler, int taxRate, int coin, int fearRate, int population) {
        this.popularity = popularity;
        //initialize food here
        this.foodRate = foodRate;
        this.ruler = ruler;
        this.taxRate = taxRate;
        this.coin = coin;
        this.fearRate = fearRate;
        this.population = population;
        buildings = new ArrayList<>();
        stockpile=new Stockpile(0,0,0,0,0,0,0,0);
        granary=new Granary(0,0,0,0);
        workersEfficiency=100;
        foods= new double[]{0, 0, 0, 0};
        weapons= new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        requestsAcceptedByMe=new ArrayList<>();
        requestsToMe=new ArrayList<>();
        requestsMadeByMe=new ArrayList<>();
        unseenRequests=new ArrayList<>();
        isAlive=true;
        people=new ArrayList<>();
        addPeopleByAmount(8);
    }
    ////////////////////setters and getters////////////////////


    public int[] getWeapons() {
        return weapons;
    }

    public void setWeapons(int[] weapons) {
        this.weapons = weapons;
    }

    public Troop getKing() {
        return king;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public Granary getGranary() {
        return granary;
    }

    public void setGranary(Granary granary) {
        this.granary = granary;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public User getRuler() {
        return ruler;
    }

    public void setRuler(User ruler) {
        this.ruler = ruler;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public double getCoin() {
        return coin;
    }

    public void setCoin(double coin) {
        this.coin = coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getFearRate() {
        return fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    public Stockpile getStockpile() {
        return stockpile;
    }

    public void setStockpile(Stockpile stockpile) {
        this.stockpile = stockpile;
    }

    public int calculateAllResource() {
        return 0;
    }

    public Building getBuildingByName(String name)
    {
        for (Building building : buildings) {
            if(building.getName().equals(name))
                return building;
        }
        return null;
    }

    public String getColor() {
        return color;
    }

    public void setWorkersEfficiency(int workersEfficiency) {
        this.workersEfficiency = workersEfficiency;
    }

    public int getWorkersEfficiency() {
        return workersEfficiency;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setKing(Troop king) {
        this.king = king;
    }

    public double[] getFoods() {
        return foods;
    }

    public void setFoods(double[] foods) {
        this.foods = foods;
    }
    public int amountOfAllResources(){
        return stockpile.getAle()+stockpile.getWood()+ stockpile.getFloor()+stockpile.getMetal()+ stockpile.getHops()+ stockpile.getPitch()+ stockpile.getStone()+ stockpile.getWheat();
    }
    public static int getLimitOfResources(){
        return limitOfResources;
    }

    public ArrayList<Request> getRequestsMadeByMe() {
        return requestsMadeByMe;
    }

    public void setRequestsMadeByMe(ArrayList<Request> requestsMadeByMe) {
        this.requestsMadeByMe = requestsMadeByMe;
    }

    public ArrayList<Request> getRequestsToMe() {
        return requestsToMe;
    }

    public void setRequestsToMe(ArrayList<Request> requestsToMe) {
        this.requestsToMe = requestsToMe;
    }

    public ArrayList<Request> getRequestsAcceptedByMe() {
        return requestsAcceptedByMe;
    }

    public void setRequestsAcceptedByMe(ArrayList<Request> requestsAcceptedByMe) {
        this.requestsAcceptedByMe = requestsAcceptedByMe;
    }
    public Request getRequestById(int id){
        for(Request request:requestsToMe){
            if(request.getId()==id) return request;
        }
        return null;
    }

    public ArrayList<Request> getUnseenRequests() {
        return unseenRequests;
    }

    public void setUnseenRequests(ArrayList<Request> unseenRequests) {
        this.unseenRequests = unseenRequests;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    public int nonZeroFoods(){
        int counter=0;
        for(int i=0;i<4;i++) {
            if (foods[i] < 0.1 && foods[i] > -0.1) counter++;
        }
        return counter;
    }

    public void addPeople(Person person) {
        people.add(person);
    }
    public void addPeopleByAmount(int amount) {
        for (int i=0;i<amount;i++){
            people.add(new Person("roostaii",100,this,false,null));
        }
    }
}
