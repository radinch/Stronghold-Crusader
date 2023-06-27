package Model.buildings;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class BuildingImage extends Rectangle {
    public BuildingImage(String address) {
        super(75,75);
        this.setFill(new ImagePattern(new Image(address)));
    }
}
