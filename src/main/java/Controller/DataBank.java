package Controller;

import Model.buildings.CastleBuilding;
import Model.buildings.OtherBuilding;
import Model.buildings.WeaponBuilding;
import Model.gameandbattle.Government;
import Model.gameandbattle.map.Building;
import Model.gameandbattle.map.Cell;
import Model.gameandbattle.map.Map;
import Model.gameandbattle.map.Texture;
import Model.gameandbattle.shop.Request;
import Model.gameandbattle.stockpile.Resource;
import Model.signup_login_profile.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBank {

    private static final HashMap<String, Building> buildingName = new HashMap<>(); // name to building
    private static ArrayList<User> allUsers = new ArrayList<>();
    private static User currentUser;
    public static ArrayList<Map> allMaps = new ArrayList<>();
    private static ArrayList<Request> requests=new ArrayList<>();
    private static ArrayList<Government> governments;
    public static ArrayList<String> colors = new ArrayList<>(List.of("Blue","Green","Red","Purple","Orange","Cyan","Yellow","Black"));
    private static final ArrayList<Texture> castleBuildingTextures = new ArrayList<>(List.of(Texture.GROUND,Texture.GRAVEL_GROUND
            ,Texture.GRASS ,Texture.HIGH_DENSITY_GRASSLAND,Texture.LOW_DENSITY_GRASSLAND,Texture.PLAIN));
    private static final ArrayList<Texture> foodAndIndustryTextures = new ArrayList<>(List.of(Texture.GROUND,Texture.GRAVEL_GROUND
            ,Texture.GRASS ,Texture.HIGH_DENSITY_GRASSLAND,Texture.LOW_DENSITY_GRASSLAND,Texture.PLAIN));
    private static final ArrayList<Texture> gardensAllowedTextures = new ArrayList<>(List.of(Texture.GRASS
            ,Texture.HIGH_DENSITY_GRASSLAND,Texture.LOW_DENSITY_GRASSLAND,Texture.LOW_DENSITY_GRASSLAND,Texture.PLAIN));
    ///////////////////////////////////// getters and setters

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ArrayList<User> allUsers) {
        DataBank.allUsers = allUsers;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        DataBank.currentUser = currentUser;
    }

    ///////////////////////////////////////////////////////
    public static User getUserByUsername(String username) {
        for (User allUser : allUsers) {
            if (allUser.getUsername().equals(username)) return allUser;
        }
        return null;
    }

    public static Cell[][] getMapNumberOne() {
        Cell[][] mapCells=new Cell[200][200];
        //you can modify these variables according to your interest
        int stoneLength = 20;
        int stoneWidth = 10;
        int ironLength = 20;
        int ironWidth = 10;
        int grassLength = 20;
        int grassWidth = 100;
        int rockLength = 15;
        int rockWidth = 5;
        int waterLength = 25;
        int waterWidth = 10;
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                mapCells[i][j] = new Cell(Texture.GROUND,null,null);
            }
        }
        // up of the map
        putTexture(mapCells,0,stoneWidth,0,stoneLength,Texture.ROCKY_GROUND);
        putTexture(mapCells,0,stoneWidth,200 - stoneLength, 200, Texture.ROCKY_GROUND);
        putTexture(mapCells, stoneWidth , stoneWidth + ironWidth, 0, ironLength,Texture.METAL);
        putTexture(mapCells,stoneWidth, stoneWidth + ironWidth, 200 - ironLength, 200, Texture.METAL);
        putTexture(mapCells,49, 49 + grassWidth, 0, grassLength, Texture.GRASS);
        putTexture(mapCells, 49, 49 + grassWidth, 200 - grassLength, 200, Texture.GRASS);
        ///// down of the map
        putTexture(mapCells,200 - stoneWidth,200,0,stoneLength,Texture.ROCKY_GROUND);
        putTexture(mapCells,200 - stoneWidth, 200,200 - stoneLength, 200, Texture.ROCKY_GROUND);
        putTexture(mapCells, 200 - stoneWidth - ironWidth , 200 - stoneWidth, 0, ironLength,Texture.METAL);
        putTexture(mapCells,200 - stoneWidth - ironWidth, 200 - stoneWidth, 200 - ironLength, 200, Texture.METAL);
        return mapCells;
    }

    public static void putTexture(Cell[][] mapCells,int startX,int endX, int startY, int endY, Texture texture) {
        for (int i = startX; i < endX ; i++) {
            for (int j = startY; j <endY ; j++) {
                mapCells[i][j].setTexture(texture);
            }
        }
    }

    public static ArrayList<Government> getGovernments() {
        return governments;
    }

    public static void setGovernments(ArrayList<Government> governments) {
        DataBank.governments = governments;
    }

    public static ArrayList<Request> getRequests() {
        return requests;
    }

    public static void setRequests(ArrayList<Request> requests) {
        DataBank.requests = requests;
    }

    public static void initializeBuildingName() {
        //todo 1.textures 2.production rate for weapon buildings
        //damage of Siege tent is not correct
        //rate of apple garden is not correct
        //rates are not correct

        // defend and attack range of towers
        // capacity of armoury
        // damages of towers and killing pit
        // rate of mill and Inn and Iron mine and Ox tether
        // TODO : sepehr please write the cost for barrack and Mercenary Post and engineer guild
        buildingName.put("Small stone gatehouse",new CastleBuilding(null,0,"Small stone gatehouse",
                75, Resource.WOOD,0,8,castleBuildingTextures,
                null,null,null,null,null,null,null, null));
        buildingName.put("big stone gatehouse",new CastleBuilding(null,0,"big stone gatehouse",
                75, Resource.STONE,20,10,castleBuildingTextures,
                null,null,null,null,null,null,null, null));
        buildingName.put("Drawbridge",new CastleBuilding(null,0,"Drawbridge",
                75, Resource.WOOD,10,0,castleBuildingTextures,
                null,null,null,null,null,5,null, null));
        buildingName.put("lookout tower",new CastleBuilding(null,0,"lookout tower",
                75, Resource.STONE,10,0,castleBuildingTextures,
                null,null,10,10,null,null,50, null));
        buildingName.put("defensive tower",new CastleBuilding(null,0,"defensive tower",
                100, Resource.STONE,15,0,castleBuildingTextures,
                null,null,10,10,null,null,50, null));
        buildingName.put("square tower",new CastleBuilding(null,0,"square tower",
                100, Resource.STONE,35,0,castleBuildingTextures,
                null,null,10,10,null,null,50, null));
        buildingName.put("circle tower",new CastleBuilding(null,0,"circle tower",
                100, Resource.STONE,40,0,castleBuildingTextures,
                null,null,10,10,null,null,50, null));
        buildingName.put("armoury",new CastleBuilding(null,0,"armoury",
                75, Resource.WOOD,5,0,castleBuildingTextures,
                null,20,0,0,null,null,null, null));
        buildingName.put("barrack",new CastleBuilding(null,0,"barrack",
                75, Resource.STONE,15,0,castleBuildingTextures,
                null,0,0,0,null,null,null, null));
        buildingName.put("Mercenary Post",new CastleBuilding(null,0,"Mercenary Post",
                75, Resource.WOOD,10,0,castleBuildingTextures,
                null,0,0,0,null,null,null, null));
        buildingName.put("engineer guild",new CastleBuilding(null,100,"engineer guild",
                75, Resource.WOOD,10,0,castleBuildingTextures,
                null,0,0,0,null,null,null, null));
        buildingName.put("killing pit",new CastleBuilding(null,0,"killing pit",
                75, Resource.WOOD,6,0,castleBuildingTextures,
                null,0,0,0,null,null,50, null));
        buildingName.put("Inn",new OtherBuilding(null,100,"Inn",
                75, Resource.WOOD,20,1,castleBuildingTextures,
                null,2,0));
        buildingName.put("Mill",new OtherBuilding(null,0,"Mill",
                75, Resource.WOOD,20,3,foodAndIndustryTextures,
                null,2,0));
        buildingName.put("Iron mine",new OtherBuilding(null,0,"Iron mine",
                75, Resource.WOOD,20,2,new ArrayList<Texture>(List.of(Texture.METAL)),
                null,2,0));
        buildingName.put("Market",new OtherBuilding(null,0,"Market",
                75, Resource.WOOD,5,1,foodAndIndustryTextures,
                null,0,0));
        buildingName.put("Ox tether",new OtherBuilding(null,0,"Ox tether",
                75, Resource.WOOD,5,1,foodAndIndustryTextures,
                null,12,0));
        buildingName.put("Pitch rig",new OtherBuilding(null,0,"Pitch rig",
                75, Resource.WOOD,20,1,new ArrayList<Texture>(List.of(Texture.PLAIN)),
                null,2,0));
        buildingName.put("Quarry",new OtherBuilding(null,0,"Quarry",
                75, Resource.WOOD,20,3,new ArrayList<Texture>(List.of(Texture.ROCKY_GROUND)),
                null,2,0));
        buildingName.put("Stockpile",new OtherBuilding(null,0,"Stockpile",
                75, Resource.WOOD,0,0,foodAndIndustryTextures,
                null,0,100));
        buildingName.put("Woodcutter",new OtherBuilding(null,0,"Woodcutter",
                75, Resource.WOOD,1,0,foodAndIndustryTextures,
                null,2,0));
        buildingName.put("Hovel",new OtherBuilding(null,0,"Hovel",39, Resource.WOOD,
                6,0,foodAndIndustryTextures,null,null,8));
        buildingName.put("Church",new OtherBuilding(null,250,"Church",309,
                null,0,0,
                foodAndIndustryTextures,null,2,null));
        buildingName.put("Cathedral",new OtherBuilding(null,1000,"Cathedral",459,
                null,0,0,
                foodAndIndustryTextures,null,2,null));
        buildingName.put("armourer",new WeaponBuilding(null,100,"armourer",114,Resource.WOOD,
                20,1,new ArrayList<Texture>(List.of(Texture.GROUND)),
                null,new ArrayList<>(List.of(Resource.METAL)),10));// production rate???
        buildingName.put("blacksmith",new WeaponBuilding(null,100,"blacksmith",114,Resource.WOOD,
                20,1,foodAndIndustryTextures,
                null,new ArrayList<>(List.of(Resource.METAL)),10));
        buildingName.put("Fletcher",new WeaponBuilding(null,100,"Fletcher",114,Resource.WOOD,
                20,1,foodAndIndustryTextures,
                null,new ArrayList<>(List.of(Resource.METAL)),10));
        buildingName.put("Poleturner",new WeaponBuilding(null,100,"Poleturner",114,Resource.WOOD,
                10,1,foodAndIndustryTextures,
                null,new ArrayList<>(List.of(Resource.METAL)),10));
        buildingName.put("oil smelter",new CastleBuilding(null,100,"oil smelter",114,Resource.METAL,
                10,1,castleBuildingTextures,
                null,null,null,null,null,null,null,null));
        buildingName.put("pitch ditch",new CastleBuilding(null,0,"pitch ditch",1000000,Resource.PITCH,
                2,0,castleBuildingTextures,
                null,null,null,null,null,null,null,null));
        buildingName.put("Caged War Dogs",new CastleBuilding(null,100,"Caged War Dogs",39,Resource.WOOD,
                10,0,castleBuildingTextures,
                null,null,null,null,null,null,null,null));
        buildingName.put("Siege tent",new CastleBuilding(null,0,"Siege tent",1,null,
                0,1,castleBuildingTextures,
                null,null,null,null,null,null,50,null));
        buildingName.put("stable",new CastleBuilding(null,400,"stable",114,Resource.WOOD,
                20,1,castleBuildingTextures,
                null,null,null,null,null,null,null,4));
        buildingName.put("Apple garden",new OtherBuilding(null,0,"Apple garden",39, Resource.WOOD,
                5,1,gardensAllowedTextures,
                null,2,null));
        buildingName.put("Hop garden",new OtherBuilding(null,0,"Hop garden",39, Resource.WOOD,
                15,1,gardensAllowedTextures,
                null,2,null));
        buildingName.put("hunting post",new OtherBuilding(null,0,"hunting post",39, Resource.WOOD,
                5,1,new ArrayList<Texture>(List.of(Texture.GROUND)),
                null,2,null));
        buildingName.put("Wheat garden",new OtherBuilding(null,0,"Wheat garden",39, Resource.WOOD,
                15,1,castleBuildingTextures,
                null,2,null));
        buildingName.put("Bakery",new OtherBuilding(null,0,"Bakery",39, Resource.WOOD,
                10,1,foodAndIndustryTextures,
                null,2,null));
        buildingName.put("Brewery",new OtherBuilding(null,0,"Brewery",39, Resource.WOOD,
                10,1,foodAndIndustryTextures,
                null,2,null));
        buildingName.put("Food StockPile",new OtherBuilding(null,0,"Food StockPile",39, Resource.WOOD,
                5,0,foodAndIndustryTextures,
                null,null,100));


    }

    public static HashMap<String, Building> getBuildingName() {
        return buildingName;
    }
}
