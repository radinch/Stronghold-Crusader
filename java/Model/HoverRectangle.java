package Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HoverRectangle extends Rectangle {
    private Text text;

    public HoverRectangle(Text text) {
        this.text = text;
        text.setX(super.getX());
        text.setY(super.getY());
        text.setFill(Color.WHITE);
        text.setFont(Font.font(10));
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
    public void setAllTextProperties(){
        text.setX(super.getX()+super.getWidth()/2-20);
        text.setY(super.getY()+super.getHeight()/2);
        text.setFill(Color.WHITE);
        text.setFont(Font.font(10));
    }
}
