package Model.gameandbattle.stockpile;

public class Granary {
    private double meat;
    private double cheese;
    private double apple;
    private double bread;

    public Granary(double meat, double cheese, double apple, double bread) {
        this.meat = meat;
        this.cheese = cheese;
        this.apple = apple;
        this.bread = bread;
    }

    public double getMeat() {
        return meat;
    }

    public void setMeat(double meat) {
        this.meat = meat;
    }

    public double getCheese() {
        return cheese;
    }

    public void setCheese(double cheese) {
        this.cheese = cheese;
    }

    public double getApple() {
        return apple;
    }

    public void setApple(double apple) {
        this.apple = apple;
    }

    public double getBread() {
        return bread;
    }

    public void setBread(double bread) {
        this.bread = bread;
    }

    public void setGranaryByName(String name, double finalAmount){
        if(name.equals("apple")) apple=finalAmount;
        if(name.equals("bread")) bread=finalAmount;
        if(name.equals("cheese")) cheese=finalAmount;
        if(name.equals("meat")) meat=finalAmount;
    }

    public double getTotalFood() {
        return (meat + apple + bread + cheese);
    }
}
