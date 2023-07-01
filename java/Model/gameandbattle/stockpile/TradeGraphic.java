package Model.gameandbattle.stockpile;

import Model.gameandbattle.Government;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class TradeGraphic extends HBox {
    private Text text;
    private Government government;
    private Button button;

    public TradeGraphic(Government government) {
        this.government = government;
        text=new Text(government.getRuler().getUsername()); text.setFont(Font.font(20));
        button=new Button("select");
        super.getChildren().add(text); super.getChildren().add(button);
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
