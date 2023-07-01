package Model.gameandbattle.stockpile;

import Model.gameandbattle.shop.Request;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class TradeRequests extends HBox {
    private Request request;
    private Button accept;

    public TradeRequests(Request request) {
        this.request = request;
        accept=new Button("accept");
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Button getAccept() {
        return accept;
    }

    public void setAccept(Button accept) {
        this.accept = accept;
    }
}