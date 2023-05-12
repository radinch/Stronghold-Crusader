package Model.gameandbattle.stockpile;

public enum Resource {
    WOOD("wood", 10,5),
    METAL("metal",20,10),
    STONE("stone",5,3),
    PITCH("pitch",6,3),
    WHEAT("wheat",14,7),
    FLOOR("floor",20,10),
    ALE("ale",30,25),
    HOPS("hops",8,4),
    OIl("oil",0,0);
    private final String name;
    private final int price;
    private final int sellPrice;

    Resource(String name, int price, int sellPrice) {
        this.name = name;
        this.price = price;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getSellPrice() {
        return sellPrice;
    }
    public static Resource[] getAllResources(){
        Resource[] resource={Resource.WOOD,Resource.METAL,Resource.STONE,Resource.PITCH,Resource.WHEAT,Resource.FLOOR,Resource.ALE,Resource.HOPS};
        return resource;
    }
    public static Resource getByName(String name){
        if(name.equals("wood")) return WOOD;
        if(name.equals("pitch")) return PITCH;
        if(name.equals("metal")) return METAL;
        if(name.equals("stone")) return STONE;
        if(name.equals("wheat")) return WHEAT;
        if(name.equals("floor")) return FLOOR;
        if(name.equals("hops")) return HOPS;
        if(name.equals("ale")) return ALE;
        return null;
    }
}
