package Controller;

import Model.buildings.OtherBuildingsMethods;
import Model.gameandbattle.Government;
import Model.gameandbattle.battle.Troop;
import Model.gameandbattle.battle.Weapon;
import Model.gameandbattle.map.Building;
import Model.gameandbattle.map.Map;

import java.util.regex.Matcher;

public class SelectBuildingController {

    public void repair(Government government, Building building, int x, int y, Map map) {
        OtherBuildingsMethods.repair(government, building, x, y, map);
    }

    public void changeTaxRate(Government government, Matcher matcher) {
        OtherBuildingsMethods.changeTaxRate(government, matcher);
    }

    public String createUnit(Government government,Matcher matcher,Building building) {
        String type= matcher.group("type");
        int count = Integer.parseInt(matcher.group("count"));
        if(DataBank.getUnitByName(type) == null)
            return "invalid name of troop";
        else if(count <= 0)
            return "invalid amount";
        else if(government.getCoin() < count * ((Troop)DataBank.getUnitByName(type)).getCost())
            return "not enough coin";
        else if(!isWeaponEnough(government,((Troop)DataBank.getUnitByName(type)),count, building.getName()))
            return "not enough weapon";
        else if(government.getUnEmployedUnit() < count) {
            System.out.println(count);
            System.out.println(government.getUnEmployedUnit());
            return "not enough people";
        }
        else if ((building.getName().equals("Mercenary Post") || building.getName().equals("engineer guild")) &&
                ((Troop) DataBank.getUnitByName(type)).getWeapons().size() != 0)
            return "you can't create this unit in this building";
        else if (building.getName().equals("barrack") && ((Troop) DataBank.getUnitByName(type)).getWeapons().size() == 0)
            return "you can't create this unit in this building";
        else if((type.equals("Ladderman") || type.equals("Engineer")) && !building.getName().equals("engineer guild"))
            return "you can't create this unit in this building";
        else {
            Troop troop = ((Troop)DataBank.getUnitByName(type));
            government.setCoin(government.getCoin() - count * ((Troop)DataBank.getUnitByName(type)).getCost());
            for (Weapon weapon : ((Troop) DataBank.getUnitByName(type)).getWeapons()) {
                government.setCountOfWeapon((-1)*count,weapon.getName());
            }
            for (int i = 0; i <count ; i++) {
                government.addUnit(new Troop(troop.getName(), troop.getHp(), government, troop.isBusy(), building,
                        troop.getAttackStrength(), troop.getSpeed(), troop.getDefenseStrength(), troop.getAttackRange(),
                        troop.getCost(), troop.getWeapons()));
                building.addUnit(new Troop(troop.getName(), troop.getHp(), government, troop.isBusy(), building,
                        troop.getAttackStrength(), troop.getSpeed(), troop.getDefenseStrength(), troop.getAttackRange(),
                        troop.getCost(), troop.getWeapons()));
                building.getOccupiedCell().addUnit(new Troop(troop.getName(), troop.getHp(), government, troop.isBusy(), building,
                        troop.getAttackStrength(), troop.getSpeed(), troop.getDefenseStrength(), troop.getAttackRange(),
                        troop.getCost(), troop.getWeapons()));
            }
            return "successful";
        }
    }

    public boolean isWeaponEnough (Government government,Troop troop,int count,String name) {
        if(!name.equals("barrack"))
            return true;
        for (Weapon weapon : troop.getWeapons()) {
            if(government.getCountOfWeapon(weapon.getName()) < count)
                return false;
        }
        return true;
    }
}
