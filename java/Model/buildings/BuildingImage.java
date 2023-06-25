package Model.buildings;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class BuildingImage extends Rectangle {

    private final ImagePattern imagePattern;
    public BuildingImage(String address) {
        super(75,75);
        this.imagePattern = new ImagePattern(new Image(address));
        this.setFill(imagePattern);
    }

    public ImagePattern getImagePattern() {
        return imagePattern;
    }
}
