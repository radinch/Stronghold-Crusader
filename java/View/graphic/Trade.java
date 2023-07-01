package View.graphic;

import Controller.DataBank;
import Controller.TradeMenuController;
import Model.gameandbattle.Government;
import Model.gameandbattle.ShopImages;
import Model.gameandbattle.battle.Weapon;
import Model.gameandbattle.shop.Request;
import Model.gameandbattle.shop.Shop;
import Model.gameandbattle.stockpile.Food;
import Model.gameandbattle.stockpile.Resource;
import Model.gameandbattle.stockpile.TradeGraphic;
import Model.gameandbattle.stockpile.TradeRequests;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Trade extends Application {
    private ArrayList<TradeGraphic> tradeGraphics;
    private Resource selectedResource;
    private Pane pane;
    private HBox firstHBox;
    private VBox playersVBox;
    private VBox photosAndButtons;
    private HBox resources;
    private Government selectedGovernment;
    private HBox amount;
    private ArrayList<ShopImages> shopImages;
    private int amountOfTrade=0;
    private TextField message;
    private TextField price;
    private HBox messageAndPrice;
    private HBox requestAndDonate;
    private Scene scene;
    private VBox requests;
    private Text notification;

    {
        notification = new Text("");
        requests=new VBox();
        requests.setSpacing(10); requests.setLayoutX(600); requests.setLayoutY(250);
        requestAndDonate=new HBox();
        messageAndPrice=new HBox();
        amount=new HBox();
        resources=new HBox();
        shopImages=new ArrayList<>();
        photosAndButtons=new VBox(); photosAndButtons.setSpacing(15);
        playersVBox =new VBox();
        tradeGraphics=new ArrayList<>();
        for (Government government:DataBank.getGovernments()) tradeGraphics.add(new TradeGraphic(government));
        pane=new Pane();
        messageAndPrice.setSpacing(20);
        playersVBox.setLayoutX(60); playersVBox.setLayoutY(90);
    }
    @Override
    public void start(Stage stage) throws Exception {
        firstButtons(); pane.getChildren().add(firstHBox);
        scene=new Scene(pane);
        stage.setScene(scene); stage.setWidth(1200); stage.setHeight(750); pane.setStyle("-fx-background-color: #c05700");
        stage.show();
    }

    private void firstButtons(){
        firstHBox=new HBox();
        firstHBox.setLayoutX(500); firstHBox.setLayoutY(350); firstHBox.setSpacing(20);
        Button create=new Button("create"); Button see=new Button("see"); Button back=new Button("back"); firstHBox.getChildren().addAll(create,see,back);
        create.setOnMouseClicked(e->createButtonPressed());
        see.setOnMouseClicked(e->seeButtonPressed());
        back.setOnMouseClicked(e-> {
            try {
                backButtonPressed();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        if(DataBank.getCurrentGovernment().getUnseenRequests().size() > 0) {
            notification.setText("you have " + DataBank.getCurrentGovernment().getUnseenRequests().size() + " new massages!");
            firstHBox.getChildren().add(notification);
        }
    }

    private void backButtonPressed() throws Exception {
        new GameMap().start(DataBank.getStage());
    }

    private void seeButtonPressed() {
        pane.getChildren().clear();
        pane.getChildren().add(requests);
        if(requests.getChildren().size() == 0)
            prepareRequests();
        Button back = new Button("back");
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pane.getChildren().clear();
                pane.getChildren().add(firstHBox);
                pane.getChildren().remove(back);
                notification.setText("");
            }
        });
        pane.getChildren().add(back);
    }

    private void prepareRequests() {
        for (Request request:DataBank.getCurrentGovernment().getRequestsToMe()){
            TradeRequests tradeRequests=new TradeRequests(request);
            Text text=new Text("type: "+request.getResource().getName()+" amount: "+request.getAmount()+" price: "+request.getPrice()+
                    " by: "+request.getSender().getRuler().getUsername()+" the message: "+request.getSenderMessage());
            text.setFont(Font.font(15));
            if (DataBank.getCurrentGovernment().getUnseenRequests().contains(request)) text.setText(text.getText()+" (unseen)");
            tradeRequests.getChildren().addAll(text,tradeRequests.getAccept());
            tradeRequests.getAccept().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    TradeMenuController tradeMenuController=new TradeMenuController(DataBank.getCurrentGovernment(),request.getSender());
                    tradeMenuController.acceptTrade(request);
                    tradeRequests.getChildren().remove(tradeRequests.getAccept());
                    text.setText(text.getText()+" accepted");
                }
            });
            requests.getChildren().add(tradeRequests);
        }
        DataBank.getCurrentGovernment().getUnseenRequests().clear();
    }

    private void createButtonPressed() {
        pane.getChildren().clear();
        pane.getChildren().add(playersVBox);
        pane.getChildren().add(photosAndButtons);
        if (!photosAndButtons.getChildren().contains(amount)) {
            addAllNames();
            createPhotos();
            createAmountHBox();
            createMessageAndPrice();
            createRequest();
            createBack();
        }
    }

    private void createBack() {
        Button button=new Button("back");
        photosAndButtons.getChildren().add(button);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pane.getChildren().clear();
                pane.getChildren().add(firstHBox);
            }
        });
    }

    private void createRequest() {
        Button request=new Button("request");
        Button donate=new Button("donate");
        requestAndDonate.getChildren().addAll(request,donate);
        requestAndDonate.setSpacing(20);
        photosAndButtons.getChildren().add(requestAndDonate);
        request.setOnMouseClicked(e->requestClicked());
        donate.setOnMouseClicked(e->donateClicked());
    }

    private void donateClicked() {
        TradeMenuController tradeMenuController=new TradeMenuController(DataBank.getCurrentGovernment(),selectedGovernment);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure?");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        // Show the dialog and wait for a response
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                tradeMenuController.trade(selectedResource.getName(),amountOfTrade, 0,message.getText());
            } else if (response == buttonTypeNo) {
                System.out.println("User clicked No");
            }
        });
    }

    private void requestClicked() {
        TradeMenuController tradeMenuController=new TradeMenuController(DataBank.getCurrentGovernment(),selectedGovernment);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure?");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        // Show the dialog and wait for a response
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                tradeMenuController.trade(selectedResource.getName(),amountOfTrade, Integer.parseInt(price.getText()),message.getText());
            } else if (response == buttonTypeNo) {
                System.out.println("User clicked No");
            }
        });
    }

    private void createMessageAndPrice() {
        message=new TextField(); message.setPromptText("message"); message.setPrefWidth(300);
        price=new TextField(); price.setPromptText("price"); price.setPrefWidth(50);
        messageAndPrice.getChildren().addAll(message,price);
        photosAndButtons.getChildren().add(messageAndPrice);
    }

    private void createAmountHBox() {
        Text text=new Text("amount: "+amountOfTrade); text.setFont(Font.font(15));
        ImageView up=new ImageView(getClass().getResource("/IMAGE/ICONS/up.png").toExternalForm()); up.setFitWidth(25); up.setFitHeight(25);
        ImageView down=new ImageView(getClass().getResource("/IMAGE/ICONS/down.png").toExternalForm()); down.setFitWidth(25); down.setFitHeight(25);
        up.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                amountOfTrade++;
                text.setText("amount: "+amountOfTrade);
            }
        });
        down.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (amountOfTrade>1) amountOfTrade--;
                text.setText("amount: "+amountOfTrade);
            }
        });
        amount.getChildren().addAll(text,up,down);
        photosAndButtons.getChildren().add(amount);
    }

    private void createPhotos() {
        int i=0;
        Shop shop=Shop.getShop();
        for(Resource food:shop.getResources()) {
            shopImages.add(new ShopImages(getClass().getResource("/IMAGE/Shop/"+food.getName()+".png").toExternalForm(),null,null,food));
            resources.getChildren().add(shopImages.get(i)); i++;
        }
        for (ShopImages shopImages1:shopImages){
            shopImages1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedResource=shopImages1.getResource();
                }
            });
        }
        photosAndButtons.getChildren().add(resources);
        photosAndButtons.setLayoutX(600);
        photosAndButtons.setLayoutY(150);
    }

    private void addAllNames() {
        for (TradeGraphic tradeGraphic:tradeGraphics) {
            playersVBox.getChildren().add(tradeGraphic);
            tradeGraphic.setSpacing(10);
            tradeGraphic.getButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedGovernment=tradeGraphic.getGovernment();
                }
            });
        }
        playersVBox.setSpacing(10);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
