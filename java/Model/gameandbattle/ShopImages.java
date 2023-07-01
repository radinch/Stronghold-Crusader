package Model.gameandbattle;

import Model.gameandbattle.battle.Weapon;
import Model.gameandbattle.stockpile.Food;
import Model.gameandbattle.stockpile.Resource;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class ShopImages extends ImageView {
    public ShopImages(String s, Food food, Weapon weapon, Resource resource) {
        super(s);
        this.food = food;
        this.weapon = weapon;
        this.resource = resource;
    }

    private Food food;
    private Weapon weapon;
    private Resource resource;

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}