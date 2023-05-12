package Model.signup_login_profile;

public enum Slogan {
    ONE("i will kill you all!", 0),
    TWO("this city is mine!", 1),
    Three("i was so dumb that i could not choose a slogan!", 2),
    FOUR("i will lose to you all", 3);
    private int number;
    private String slogan;

    Slogan(String slogan, int number) {
        this.number = number;
        this.slogan = slogan;
    }

    public int getNumber() {
        return number;
    }

    public String getSlogan() {
        return slogan;
    }

    public static String randomSlogan(int num) {
        if (num == 0) return ONE.slogan;
        if (num == 1) return TWO.slogan;
        if (num == 2) return Three.slogan;
        return FOUR.slogan;
    }
}
