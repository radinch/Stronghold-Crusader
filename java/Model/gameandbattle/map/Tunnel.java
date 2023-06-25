package Model.gameandbattle.map;

public class Tunnel {
    public final static int limitLength =3;
    private int length;

    public Tunnel(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
