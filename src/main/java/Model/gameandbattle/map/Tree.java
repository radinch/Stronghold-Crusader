package Model.gameandbattle.map;

import java.util.ArrayList;
import java.util.List;

public enum Tree {
    DESERT_SHRUB("desert shrub"),
    OLIVE_TREE("olive tree"),
    PALM_TREE("palm tree"),
    COCONUT_TREE("coconut tree"),
    CHERRY_PALM("cherry palm");
    private String name;
    private static final ArrayList<Texture> notAllowedTexture = new ArrayList<>(List.of(Texture.METAL, Texture.ROCK,
            Texture.PETROLEUM, Texture.PLAIN, Texture.SHALLOW_WATER, Texture.RIVER,
            Texture.SMALL_POUND, Texture.LARGE_POUND, Texture.BEACH, Texture.SEE));
    private static final ArrayList<Tree> trees = new ArrayList<>(List.of(DESERT_SHRUB,
            OLIVE_TREE, PALM_TREE, COCONUT_TREE, CHERRY_PALM));

    Tree(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Tree getTreeByName(String name) {
        for (Tree tree : trees) {
            if (tree.getName().equals(name))
                return tree;
        }
        return null;
    }

    public static ArrayList<Texture> getNotAllowedTexture() {
        return notAllowedTexture;
    }
}
