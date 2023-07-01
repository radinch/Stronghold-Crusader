package Controller;

import Model.gameandbattle.Government;
import Model.gameandbattle.map.Map;
import Model.gameandbattle.map.Texture;
import Model.gameandbattle.map.Tree;
import View.menus.DropElementMenu;

import java.util.Random;
import java.util.regex.Matcher;

public class DropMenuController {
    private Map map;
    private Government government;
    private DropElementMenu dropElementMenu;

    public DropMenuController(Map map, Government government) {
        this.map = map;
        this.government = government;
        dropElementMenu = new DropElementMenu(this);
    }

    public static String setTileTexture(int x,int y,String textureName) {
        Texture texture = Texture.getTextureByName(textureName);
        if(texture == null)
            return "invalid texture";
        if(!isCoordinateValid(x,y))
            return "invalid coordinate";
        if(isThereABuilding(x,x+1,y,y+1))
            return "this place has a building";
        DataBank.putTexture(Map.MAP_NUMBER_TWO.getMapCells(),x,x + 1,y,y + 1,texture);
        return "successful";
    }

    public String setGroupTexture(Matcher matcher) {
        int x1 = Integer.parseInt(matcher.group("y1"));
        int x2 = Integer.parseInt(matcher.group("y2"));
        int y1 = Integer.parseInt(matcher.group("x1"));
        int y2 = Integer.parseInt(matcher.group("x2"));
        Texture texture = Texture.getTextureByName(matcher.group("type"));
        if(!isCoordinateValid(x1,y1) || !isCoordinateValid(x2,y2))
            return "invalid coordinate";
        if(isThereABuilding(x1,x2,y1,y2))
            return "in this region a building exists";
        DataBank.putTexture(map.getMapCells(),x1,x2,y1,y2,texture);
        return "successful";
    }

    public String clear(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));
        if(x < 0 || y < 0)
        return "invalid x,y (less than zero)";
        else if(x >= map.getSize() || y >= map.getSize())
        return "invalid x,y (bigger that map size)";
        else {
            map.setACell(x,y,DataBank.getMapNumberOne()[x][y]);
            return "success";
        }
    }

    public String dropRock(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));
        String direction = matcher.group("d");
        if(x < 0 || y < 0)
        return "invalid x,y (less than zero)";
        else if(x >= map.getSize() ||  y >= map.getSize())
        return "invalid x,y (bigger that map size)";
        else if(isThereABuilding(x,x + 1,y, y +1))
            return "in this region a building exists";
        else {
            Texture rock = Texture.ROCK;
            if(direction.equals("r"))
            {
                Random r = new Random();
                String alphabet = "swne";
                direction = String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
            }
            rock.setDirection(direction);
            map.getACell(x,y).setTexture(rock);
            return "success";
        }
    }

    public String dropTree(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));
        if(!isCoordinateValid(x,y))
            return "invalid coordinate";
        if(!isCellMaterialOKForTrees(x,y))
            return "cell texture is not ok for dropping tree";
        if(isThereABuilding(x,x+1,y,y+1))
            return "in this region a building exists";
        map.getMapCells()[x][y].setTree(Tree.getTreeByName(matcher.group("type")));
        return "successful";
    }

    public String dropWater(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("y"));
        int y = Integer.parseInt(matcher.group("x"));
        String waterType = matcher.group("type");
        if(!isCoordinateValid(x,y))
            return "invalid coordinate";
        if(!isCellMaterialOKForTrees(x,y))
            return "cell texture is not ok for dropping tree";
        return "successful";
    }

    public String dropBuilding(Matcher matcher) {
        return null;
    }

    public String dropUnit(Matcher matcher) {
        return null;
    }

    private static boolean isCoordinateValid(int x, int y) {
        return x >= 0 && y >= 0 && x < Map.MAP_NUMBER_TWO.getSize() && y < Map.MAP_NUMBER_TWO.getSize();
    }
    private static boolean isThereABuilding(int startX, int endX, int startY, int endY) {
        for (int i = startX; i < endX ; i++) {
            for (int j = startY; j <endY ; j++) {
                if (Map.MAP_NUMBER_TWO.getACell(i,j).getBuilding() != null)
                    return true;
            }
        }
        return false;
    }
    private boolean isCellMaterialOKForTrees(int x, int y) {
        for (Texture texture : Tree.getNotAllowedTexture()) {
            if(texture.getName().equals(map.getACell(x,y).getTexture().getName()))
                return false;
        }
        return true;
    }

    private boolean isCellMaterialOKForBuildings(int x, int y) {
        return false;
    }

    private boolean isCellMaterialOKForUnits(int x, int y) {
        return false;
    }
}
