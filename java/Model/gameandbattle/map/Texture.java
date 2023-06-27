package Model.gameandbattle.map;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Texture {
    GROUND("ground", Texture.class.getResource("/IMAGE/Tile/Ground.png").toExternalForm()),
    GRAVEL_GROUND("gravel ground",Texture.class.getResource("/IMAGE/Tile/Gravel.png").toExternalForm()),
    ROCK("rock",Texture.class.getResource("/IMAGE/Tile/Rock.png").toExternalForm()),
    ROCKY_GROUND("rocky ground",Texture.class.getResource("/IMAGE/Tile/Ground.png").toExternalForm()),
    METAL("metal",Texture.class.getResource("/IMAGE/Tile/metal.png").toExternalForm()),
    GRASS("grass",Texture.class.getResource("/IMAGE/Tile/Grass.png").toExternalForm()),
    HIGH_DENSITY_GRASSLAND("high density grassland",Texture.class.getResource("/IMAGE/Tile/Grass.png").toExternalForm()),
    LOW_DENSITY_GRASSLAND("low density grassland",Texture.class.getResource("/IMAGE/Tile/Grass.png").toExternalForm()),
    PETROLEUM("petroleum",null),
    PLAIN("plain",Texture.class.getResource("/IMAGE/Tile/SmallPound.png").toExternalForm()),
    SHALLOW_WATER("shallow water",Texture.class.getResource("/IMAGE/Tile/SmallPound.png").toExternalForm()),
    RIVER("river",Texture.class.getResource("/IMAGE/Tile/Sea.png").toExternalForm()),
    SMALL_POUND("small pound",Texture.class.getResource("/IMAGE/Tile/SmallPound.png").toExternalForm()),
    LARGE_POUND("large pound",Texture.class.getResource("/IMAGE/Tile/LargePound.png").toExternalForm()),
    BEACH("beach",Texture.class.getResource("/IMAGE/Tile/Ground.png").toExternalForm()),
    SEE("see",Texture.class.getResource("/IMAGE/Tile/Sea.png").toExternalForm());

    private String name;
    private String imageAddress;
    private String direction;
    private static final ArrayList<Texture> textures = new ArrayList<>(List.of(GROUND,GRAVEL_GROUND,GRASS,ROCK,ROCKY_GROUND,METAL,HIGH_DENSITY_GRASSLAND,
            LOW_DENSITY_GRASSLAND,PETROLEUM,PLAIN,SHALLOW_WATER,RIVER,SMALL_POUND,LARGE_POUND,BEACH,SEE));

    Texture(String name,String imageAddress) {
        this.name = name;
        this.imageAddress = imageAddress;
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

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

}
