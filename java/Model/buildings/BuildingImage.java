package Model.buildings;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class BuildingImage extends Rectangle {
    int i;
    int j;
    public BuildingImage(String address) {
        super(75,75);
        this.setFill(new ImagePattern(new Image(address)));
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void setCoordinates(int i, int j) {
        this.i = i;
        this.j = j;
    }
}
