package Model.gameandbattle;

import Model.buildings.CastleBuilding;
import Model.buildings.OtherBuilding;
import Model.gameandbattle.battle.Person;
import Model.gameandbattle.battle.Troop;
import Model.gameandbattle.battle.Weapon;
import Model.gameandbattle.map.Building;
import Model.gameandbattle.shop.Request;
import Model.gameandbattle.stockpile.Granary;
import Model.gameandbattle.stockpile.Stockpile;
import org.example.User;

import java.util.ArrayList;

public class Government {
    private static final int limitOfResources = 600000;
    private int popularity;
    private int foodRate;
    private User ruler;
    private int taxRate;
    private double coin;
    private int fearRate;
    private Stockpile stockpile;
    private Granary granary;
    private ArrayList<Person> people;
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

    public Government(int popularity, int foodRate, User ruler, int taxRate, int coin, int fearRate, int population) {
        this.popularity = popularity;
        //initialize food here
        this.foodRate = foodRate;
        this.ruler = ruler;
        this.taxRate = taxRate;
        this.coin = coin;
        this.fearRate = fearRate;
        this.population = population;
        maxPopulation = 8;
        buildings = new ArrayList<>();
        stockpile = new Stockpile(100, 0, 50, 100, 0, 0, 0, 0,0);
        granary = new Granary(50, 50, 50, 50);
        workersEfficiency = 100;
        foods = new double[]{50, 50, 50, 50};
        weapons = new int[]{0, 0, 0, 0, 0, 0, 0, 0,0};
        requestsAcceptedByMe = new ArrayList<>();
        requestsToMe = new ArrayList<>();
        requestsMadeByMe = new ArrayList<>();
        unseenRequests = new ArrayList<>();
        isAlive = true;
        people = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            addPerson();
        }
        this.king = new Troop("King",2000,this,true,this.getBuildingByName("Small stone gatehouse"),1000,300,1000,5,0,null);
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

    public Building getBuildingByName(String name) {
        for (Building building : buildings) {
            if (building.getName().equals(name))
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

    public int amountOfAllResources() {
        return stockpile.getAle() + stockpile.getWood() + stockpile.getFloor() + stockpile.getMetal() + stockpile.getHops() + stockpile.getPitch() + stockpile.getStone() + stockpile.getWheat();
    }

    public static int getLimitOfResources() {
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

    public Request getRequestById(int id) {
        for (Request request : requestsToMe) {
            if (request.getId() == id) return request;
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

    public int nonZeroFoods() {
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            if (!(foods[i] < 0.1 && foods[i] > -0.1)) counter++;
        }
        return counter;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void addPerson() {
        people.add(new Person("unemployed", 1, this, false, null));
    }

    public void addUnit(Troop troop) {
        for (Person person : people) {
            if (!person.isBusy()) {
                people.remove(person);
                break;
            }
        }
        people.add(troop);
    }

    public int getCountOfWeapon(String name) {
        for (int i = 0; i < weapons.length; i++) {
            if (Weapon.getAllWeapons()[i].getName().equals(name))
                return weapons[i];
        }
        return 0;
    }

    public void setCountOfWeapon(int count, String name) {
        for (int i = 0; i < weapons.length; i++) {
            if (Weapon.getAllWeapons()[i].getName().equals(name)) {
                weapons[i] += count;
                if(getTotalWeapon() > getMaxWeaponCapacity())
                    weapons[i]-=getTotalWeapon() - getMaxWeaponCapacity();
            }
        }
    }

    public int getUnEmployedUnit() {
        int count = 0;
        for (Person person : people) {
            if (!person.isBusy())
                count++;
        }
        return count;
    }

    public double getMaxFoodCapacity() {
        double maxFoodCapacity = 0;
        for (Building building : buildings) {
            if (building.getName().equals("Food StockPile"))
                maxFoodCapacity += ((OtherBuilding) building).getCapacity();
        }
        return maxFoodCapacity;
    }

    public int getMaxResourceCapacity()
    {
        int maxResource = 0;
        for (Building building : buildings) {
            if(building.getName().equals("Stockpile"))
                maxResource += ((OtherBuilding)building).getCapacity();
        }
        return maxResource;
    }

    public int getTotalWeapon() {
        int total=0;
        for (int i = 0; i < weapons.length; i++) {
            total+=weapons[i];
        }
        return total;
    }

    public int getMaxWeaponCapacity() {
        int maxWeapon =0;
        for (Building building : buildings) {
            if(building.getName().equals("armoury"))
                maxWeapon +=((CastleBuilding)building).getCapacity();
        }
        return maxWeapon;
    }

    public void addWorker(Building building,int count)
    {
        int number = 0;
        for (Person person : people) {
            if(number == count)
                break;
            if(!person.isBusy()) {
                person.setBusy(true);
                building.getWorkers().add(person);
                person.setBuilding(building);
                number++;
            }
        }
    }
}
