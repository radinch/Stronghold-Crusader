package Model.gameandbattle.map;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Texture {
    GROUND("ground"),
    GRAVEL_GROUND("gravel ground"),
    ROCK("rock"),
    ROCKY_GROUND("rocky ground"),
    METAL("metal"),
    GRASS("grass"),
    HIGH_DENSITY_GRASSLAND("high density grassland"),
    LOW_DENSITY_GRASSLAND("low density grassland"),
    PETROLEUM("petroleum"),
    PLAIN("plain"),
    SHALLOW_WATER("shallow water"),
    RIVER("river"),
    SMALL_POUND("small pound"),
    LARGE_POUND("large pound"),
    BEACH("beach"),
    SEE("see");

    private String name;
    private String direction;
    private static final ArrayList<Texture> textures = new ArrayList<>(List.of(GROUND,GRAVEL_GROUND,ROCK,ROCKY_GROUND,METAL,HIGH_DENSITY_GRASSLAND,
            LOW_DENSITY_GRASSLAND,PETROLEUM,PLAIN,SHALLOW_WATER,RIVER,SMALL_POUND,LARGE_POUND,BEACH,SEE));

    Texture(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Texture getTextureByName(String name) {
        for (Texture texture : textures) {
            if(texture.getName().equals(name))
                return texture;
        }
        return null;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

}
