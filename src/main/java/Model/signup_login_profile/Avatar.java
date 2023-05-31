package Model.signup_login_profile;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class Avatar extends Circle {
    private File file;
    public Avatar(int x,int y,String address) {
        super(x,y,40);
        this.setFill(new ImagePattern(
                new Image(address)));
    }
    public Avatar(int x,int y,File file)
    {
        super(x,y,40);
        this.file = file;
        this.setFill(new ImagePattern(
                new Image(file.getPath())));
    }
}
