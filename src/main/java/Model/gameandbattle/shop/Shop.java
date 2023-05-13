package Model.gameandbattle.shop;

import Model.gameandbattle.battle.Weapon;
import Model.gameandbattle.stockpile.Food;
import Model.gameandbattle.stockpile.Resource;

import java.util.ArrayList;

public class Shop {
    private static Shop shop;
    private Resource[] resources;
    private Food[] foods;
    private Weapon[] weapons;
    static {
        shop=new Shop();
    }
    private Shop(){
        resources=Resource.getAllResources();
        foods=Food.getAllFoods();
        weapons=Weapon.getAllWeapons();
    }

    public Resource[] getResources() {
        return resources;
    }

    public void setResources(Resource[] resources) {
        this.resources = resources;
    }

    public Food[] getFoods() {
        return foods;
    }

    public static Shop getShop() {
        return shop;
    }

    public static void setShop(Shop shop) {
        Shop.shop = shop;
    }

    public void setFoods(Food[] foods) {
        this.foods = foods;
    }

    public Weapon[] getWeapons() {
        return weapons;
    }

    public void setWeapons(Weapon[] weapons) {
        this.weapons = weapons;
    }
}
