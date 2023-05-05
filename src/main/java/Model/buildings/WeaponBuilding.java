package Model.buildings;

import Model.gameandbattle.*;
import Model.gameandbattle.battle.Weapon;
import Model.gameandbattle.map.Building;
import Model.gameandbattle.map.Cell;
import Model.gameandbattle.map.Map;
import Model.gameandbattle.map.Texture;
import Model.gameandbattle.stockpile.Resource;

import java.util.ArrayList;
import java.util.Scanner;

public class WeaponBuilding extends Building {
    private ArrayList<Resource> consumableResources;
    private int productionRate;
    private ArrayList<Weapon> weapons;

    public WeaponBuilding(Government government, double gold, String name, int hitpoint, Resource resourceRequired, int amountOfResource, int amountOfWorkers, ArrayList<Texture> textures, Cell occupiedCell, ArrayList<Resource> consumableResources, int productionRate) {
        super(government, gold, name, hitpoint, resourceRequired, amountOfResource, amountOfWorkers, textures, occupiedCell);
        this.consumableResources = consumableResources;
        this.productionRate = productionRate;
        weapons = new ArrayList<>();
    }

    @Override
    public void whenBuildingIsSelected(int x, int y, Map map, Scanner scanner) {
        super.whenBuildingIsSelected(x, y, map, scanner);
    }

    @Override
    public void makeAffect(int x, int y, Map map) {
        makeWeapon(this);
    }

    private void makeWeapon(Building building) {
        switch (building.getName()) {
            case "armourer" -> {
                if (building.getGovernment().getStockpile().getMetal() >=
                        ((WeaponBuilding) building).getProductionRate() * Weapon.METAL_ARMOR.getStockpile().getMetal()) {
                    building.getGovernment().setCountOfWeapon(building.getGovernment().getCountOfWeapon("metal armor") +
                            ((WeaponBuilding) building).getProductionRate(), "metal armor");
                    building.getGovernment().getStockpile().setMetal(building.getGovernment().getStockpile().getMetal() -
                            ((WeaponBuilding) building).getProductionRate() * Weapon.METAL_ARMOR.getStockpile().getMetal());
                }
            }
            case "blacksmith" -> {
                if (building.getGovernment().getStockpile().getMetal() >=
                        ((WeaponBuilding) building).getProductionRate() * (Weapon.SWORDS.getStockpile().getMetal() + Weapon.MACE.getStockpile().getMetal())) {
                    building.getGovernment().setCountOfWeapon(building.getGovernment().getCountOfWeapon("mace") +
                            ((WeaponBuilding) building).getProductionRate(), "mace");
                    building.getGovernment().setCountOfWeapon(building.getGovernment().getCountOfWeapon("swords") +
                            ((WeaponBuilding) building).getProductionRate(), "swords");
                    building.getGovernment().getStockpile().setMetal(building.getGovernment().getStockpile().getMetal() -
                            ((WeaponBuilding) building).getProductionRate() * (Weapon.SWORDS.getStockpile().getMetal() + Weapon.MACE.getStockpile().getMetal()));
                }
            }
            case "Fletcher" -> {
                if (building.getGovernment().getStockpile().getWood() >=
                        ((WeaponBuilding) building).getProductionRate() * Weapon.BOW.getStockpile().getWood()) {
                    building.getGovernment().setCountOfWeapon(building.getGovernment().getCountOfWeapon("bow") +
                            ((WeaponBuilding) building).getProductionRate(), "bow");
                    building.getGovernment().getStockpile().setWood(building.getGovernment().getStockpile().getWood() -
                            ((WeaponBuilding) building).getProductionRate() * Weapon.BOW.getStockpile().getWood());
                }
            }
            case "Poleturner" -> {
                if (building.getGovernment().getStockpile().getWood() >=
                        ((WeaponBuilding) building).getProductionRate() * (Weapon.SPEAR.getStockpile().getWood() + Weapon.PIKE.getStockpile().getWood())) {
                    building.getGovernment().setCountOfWeapon(building.getGovernment().getCountOfWeapon("spear") +
                            ((WeaponBuilding) building).getProductionRate(), "spear");
                    building.getGovernment().setCountOfWeapon(building.getGovernment().getCountOfWeapon("pike") +
                            ((WeaponBuilding) building).getProductionRate(), "pike");
                    building.getGovernment().getStockpile().setWood(building.getGovernment().getStockpile().getWood() -
                            ((WeaponBuilding) building).getProductionRate() * (Weapon.SPEAR.getStockpile().getWood() + Weapon.PIKE.getStockpile().getWood()));
                }
            }
        }
    }

    public ArrayList<Resource> getConsumableResources() {
        return consumableResources;
    }

    public void setConsumableResources(ArrayList<Resource> consumableResources) {
        this.consumableResources = consumableResources;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public void setProductionRate(int productionRate) {
        this.productionRate = productionRate;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(ArrayList<Weapon> weapons) {
        this.weapons = weapons;
    }

    public void createUnit(Building building) {

    }
}
