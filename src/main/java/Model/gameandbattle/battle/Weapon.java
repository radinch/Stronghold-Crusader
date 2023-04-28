package Model.gameandbattle.battle;

import Model.gameandbattle.stockpile.Resource;
import Model.gameandbattle.stockpile.Stockpile;

public enum Weapon {
    BOW("bow",100,new Stockpile(2,0,0,0,0,0,0,0),100,50),
    CROSSBOW("crossbow",100,new Stockpile(3,0,0,0,0,0,0,0),100,50),
    SPEAR("spear",100,new Stockpile(1,0,0,0,0,0,0,0),100,50),
    PIKE("pike",100,new Stockpile(2,0,0,0,0,0,0,0),100,50),
    MACE("mace",100,new Stockpile(0,0,1,0,0,0,0,0),100,50),
    SWORDS("swords",100,new Stockpile(0,0,1,0,0,0,0,0),100,50),
    LEATHER_ARMOR("leather armor",100,new Stockpile(2,0,0,0,0,0,0,0),100,50),
    METAL_ARMOR("metal armor",100,new Stockpile(0,0,1,0,0,0,0,0),100,50);
    private String name;
    private int damage;
    private Stockpile stockpile;
    private int price;
    private int sellPrice;

    Weapon(String name, int damage, Stockpile stockpile, int price, int sellPrice) {
        this.name = name;
        this.damage = damage;
        this.stockpile = stockpile;
        this.price = price;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Stockpile getStockpile() {
        return stockpile;
    }

    public void setStockpile(Stockpile stockpile) {
        this.stockpile = stockpile;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public static Weapon[] getAllWeapons(){
        Weapon[] weapons={Weapon.BOW,Weapon.CROSSBOW,Weapon.SPEAR,Weapon.PIKE,Weapon.MACE,Weapon.SWORDS,Weapon.LEATHER_ARMOR,Weapon.METAL_ARMOR};
        return weapons;
    }

}
