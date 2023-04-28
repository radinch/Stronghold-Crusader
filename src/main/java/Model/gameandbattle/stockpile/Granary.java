package Model.gameandbattle.stockpile;

public class Granary {
    private int meat;
    private int cheese;
    private int apple;
    private int bread;

    public Granary(int meat, int cheese, int apple, int bread) {
        this.meat = meat;
        this.cheese = cheese;
        this.apple = apple;
        this.bread = bread;
    }
    /////////////////getters and setters/////////////////
    public int getMeat() {
        return meat;
    }

    public void setMeat(int meat) {
        this.meat = meat;
    }

    public int getCheese() {
        return cheese;
    }

    public void setCheese(int cheese) {
        this.cheese = cheese;
    }

    public int getApple() {
        return apple;
    }

    public void setApple(int apple) {
        this.apple = apple;
    }

    public int getBread() {
        return bread;
    }

    public void setBread(int bread) {
        this.bread = bread;
    }
}
