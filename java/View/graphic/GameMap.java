package View.graphic;
import Controller.DataBank;
import Controller.MapMenuController;
import Controller.ShopMenuController;
import Model.HoverRectangle;
import Model.buildings.BuildingImage;
import Model.gameandbattle.ShopImages;
import Model.gameandbattle.battle.Weapon;
import Model.gameandbattle.map.Map;
import Model.gameandbattle.shop.Shop;
import Model.gameandbattle.stockpile.Food;
import Model.gameandbattle.stockpile.Resource;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameMap extends Application {
    private Button sell;
    private Button buy;
    private Text buyPrice;
    private Text sellPrice;
    private HBox shopBox;
    private ShopImages selectedItem;
    private int shopStartRow=0;
    private ArrayList<ShopImages> shopImages;

    private double maxHeight = 0;
    private double maxWidth = 0;
    private int zoomLevel=0;
    private boolean isInShop;
    private int stageWidth=0;
    private int stageHeight=0;
    private int imageWidth=120;
    private int imageHeight=75;
    private int startRow=49;
    private int startCol=8;
    private int size=10;
    private ImageView[][] images;
    private Scene scene;
    private ArrayList<HoverRectangle> hoverRectangles;
    private final ArrayList<String> buildingNames = new ArrayList<>(List.of("Small stone gatehouse","big stone gatehouse",
            "Mercenary Post", "barrack","engineer guild","Quarry","Iron mine","Pitch rig","Market",
            "Woodcutter","Wheat garden","Apple garden","Hop garden","hunting post","Dairy products",
            "lookout tower","perimeter tower","defensive tower","square tower","circle tower","Hovel"
            ,"StockPile","stable","Church"));
    private final ArrayList<BuildingImage> buildingImages = new ArrayList<>();
    private BuildingImage temp;
    private HBox hBox; //hBox for buildings
    private  VBox foodRateVBox;
    private VBox taxRateVBox;
    private int startIndexForBuilding = 0;
    private boolean isBuildingDragged = false;
    private Pane pane;
    {
        shopBox=new HBox(); shopBox.setLayoutX(350); shopBox.setLayoutY(610);
        shopImages=new ArrayList<>();
        images=new ImageView[size][size];
        stageHeight=imageHeight*size;
        stageWidth=imageWidth*size;
        hoverRectangles=new ArrayList<>();
        int counter = 0;
        for (String buildingName : buildingNames) {
            counter++;
            if(counter == 6)
                counter=1;
            BuildingImage buildingImage = new BuildingImage(getBuildingImageAddress(buildingName));
            int finalCounter = counter;
            buildingImage.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Point2D sceneCoordinates = buildingImage.localToScreen(mouseEvent.getX(), mouseEvent.getSceneY());
                    temp = new BuildingImage(getBuildingImageAddress(buildingName));
                    temp.setLayoutX(91 + (finalCounter%6)*42.5);
                    temp.setLayoutY(300);
                    temp.setUserData(new Point2D(mouseEvent.getSceneX() -
                            temp.getLayoutX(), mouseEvent.getSceneY() - temp.getLayoutY()));
                    addBuildingFeature(temp,buildingName);
                }
            });
            buildingImage.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    isBuildingDragged = true;
                    if(!pane.getChildren().contains(temp)) {
                        pane.getChildren().add(temp);
                    }
                    Point2D mousePosition = new Point2D(event.getSceneX() - temp.getLayoutX(),
                            event.getSceneY() - temp.getLayoutY());
                    Point2D imagePosition = (Point2D) temp.getUserData();
                    double newX = temp.getLayoutX() + mousePosition.getX() - imagePosition.getX();
                    double newY = temp.getLayoutY() + mousePosition.getY() - imagePosition.getY();
                    temp.setX(newX);
                    temp.setY(newY);
                }
            });
            buildingImage.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(isBuildingDragged) {
                        Pair<Integer,Integer> coordinates = getBuildingDestination(temp);

                    }
                    isBuildingDragged = false;
                }
            });
            buildingImages.add(buildingImage);
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
        pane=new Pane();
        stage.setHeight(stageHeight); //change whenever you want
        stage.setWidth(stageWidth);
        initialize();
        scene=new Scene(pane);
        stage.setScene(scene);
        drag();
        scene.getStylesheets().add(String.valueOf(GameMap.class.getResource("/CSS/style1.css")));
        stage.show();
    }

    private void initialize() {
        for (int i=startRow;i<Math.min(startRow+size,200);i++){
            for (int j=startCol;j<Math.min(size+startCol,200);j++){
                images[i-startRow][j-startCol]=new ImageView(new Image(Map.MAP_NUMBER_ONE.getACell(i,j).getTexture().getImageAddress()));
                images[i-startRow][j-startCol].setFitWidth(imageWidth);
                images[i-startRow][j-startCol].setFitHeight(imageHeight);
                images[i-startRow][j-startCol].setX((i-startRow)*imageWidth);
                images[i-startRow][j-startCol].setY((j-startCol)*imageHeight);
                pane.getChildren().add(images[i-startRow][j-startCol]);
                setOnHover(images[i-startRow][j-startCol],i,j);
            }
        }
        createBuildingMenuBar();
    }

    private void setOnHover(ImageView imageView,int i,int j) {
        imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(imageView.getX()+"========="+imageView.getY());
                HoverRectangle hoverRectangle=new HoverRectangle(new Text(MapMenuController.showDetails(Map.MAP_NUMBER_ONE,i,j)));
                hoverRectangle.setX(imageView.getX()); hoverRectangle.setY(imageView.getY()); hoverRectangle.setFill(Color.BLACK);
                hoverRectangle.setHeight(imageHeight); hoverRectangle.setWidth(imageWidth); hoverRectangle.setAllTextProperties();
                pane.getChildren().add(hoverRectangle); pane.getChildren().add(hoverRectangle.getText());
                hoverRectangles.add(hoverRectangle);
                hoverRectangle.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        clearAllRectangles();
                    }
                });
            }
        });
    }

    private void clearAllRectangles() {
        System.out.println(hoverRectangles.size());
        try {
            for (HoverRectangle rectangle : hoverRectangles) {
                pane.getChildren().remove(rectangle.getText());
                pane.getChildren().remove(rectangle);
            }
        }
        catch (Exception e){

        }
        hoverRectangles.clear();
    }

    private void update() throws InterruptedException {
        clearAllRectangles();
        //images should be loaded before this
        pane.getChildren().clear();
        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                images[i][j].setFitWidth(imageWidth);
                images[i][j].setFitHeight(imageHeight);
                images[i][j].setX(i*imageWidth);
                images[i][j].setY(j*imageHeight);
                setOnHover(images[i][j],i+startRow,j+startCol);
                pane.getChildren().add(images[i][j]);
            }
        }
        createBuildingMenuBar();
    }
    private void addBuildingFeature(BuildingImage buildingImage, String buildingName) {
        switch (buildingName) {
            case "Small stone gatehouse", "big stone gatehouse" ->
                    buildingImage.setOnMouseClicked(me -> goToGovernmentMenu());
            case "Market" ->
                buildingImage.setOnMouseClicked(me->goToShopMenu());
        }
    }

    private void goToShopMenu() {
        pane.getChildren().add(shopBox);
        createShopImages();
        pane.getChildren().remove(hBox);
        createShopButtonsAndText();
        updateShopImages(0);
    }

    private void createShopButtonsAndText() {
        sell=new Button("Sell"); sell.setLayoutX(460); sell.setLayoutY(655);
        buy=new Button("Buy");  buy.setLayoutX(460); buy.setLayoutY(680);
        sellPrice=new Text("-"); sellPrice.setLayoutX(510); sellPrice.setLayoutY(675); sellPrice.setFill(Color.DARKORANGE); sellPrice.setFont(Font.font(15));
        buyPrice=new Text("-"); buyPrice.setLayoutX(510); buyPrice.setLayoutY(702); buyPrice.setFill(Color.DARKORANGE); buyPrice.setFont(Font.font(15));
        pane.getChildren().addAll(buy,buyPrice,sell,sellPrice);
        Button back=new Button("Back");  pane.getChildren().add(back);  back.setLayoutY(610);  back.setLayoutX(275);
        back.setOnMouseClicked(me -> backToBuildingMenuFromShop());
        sell.setOnMouseClicked(e->sellClicked());
    }

    private void sellClicked() {
        Alert alert=new Alert(Alert.AlertType.ERROR);
        if (selectedItem==null) {
            alert.setContentText("you did not choose an item");
            alert.showAndWait();
        }
        else {
            String name=getSelectedItemName();
            if (ShopMenuController.sell(name,DataBank.getCurrentGovernment())){
                alert=new Alert(Alert.AlertType.CONFIRMATION); alert.setContentText("successful"); alert.showAndWait();
            }
            else {
                alert.setContentText("dont have enough items");
                alert.showAndWait();
            }
        }
    }
    private void buyClicked(){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        if (selectedItem==null) {
            alert.setContentText("you did not choose an item");
            alert.showAndWait();
        }
        else {
            String name=getSelectedItemName();
            if (ShopMenuController.buy(name,DataBank.getCurrentGovernment())){
                alert=new Alert(Alert.AlertType.CONFIRMATION); alert.setContentText("successful"); alert.showAndWait();
            }
            else {
                alert.setContentText("dont have enough golds");
                alert.showAndWait();
            }
        }
    }


    private String getSelectedItemName() {
        if (selectedItem.getFood()!=null) return selectedItem.getFood().getName();
        else if(selectedItem.getWeapon()!=null) return selectedItem.getWeapon().getName();
        else return selectedItem.getResource().getName();
    }

    private void backToBuildingMenuFromShop() {
        pane.getChildren().removeAll(shopBox,sell,buy,sellPrice,buyPrice);
        createBuildingMenuBar();
    }

    private void updateShopImages(int start) {
        shopBox.getChildren().clear();
        if (start<=0) {
            shopStartRow=shopStartRow+shopImages.size();
            start=start+shopImages.size();
        }
        for (int i=start;i<start+6;i++){
            ShopImages shopImages1=shopImages.get(i%(shopImages.size()));
            shopBox.getChildren().add(shopImages.get(i%(shopImages.size())));
            shopImages.get(i%shopImages.size()).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (shopImages1.getFood()!=null) {
                        sellPrice.setText(shopImages1.getFood().getSellPrice()+"");
                        buyPrice.setText(shopImages1.getFood().getPrice()+"");
                    }
                    else if (shopImages1.getWeapon()!=null) {
                        sellPrice.setText(shopImages1.getWeapon().getSellPrice()+"");
                        buyPrice.setText(shopImages1.getWeapon().getPrice()+"");
                    }
                    else {
                        sellPrice.setText(shopImages1.getResource().getSellPrice()+"");
                        buyPrice.setText(shopImages1.getResource().getPrice()+"");
                    }
                    selectedItem=shopImages1;
                }
            });
        }
    }

    private void createShopImages() {
        Shop shop=Shop.getShop();
        for(Food food:shop.getFoods()) shopImages.add(new ShopImages(getClass().getResource("/IMAGE/Shop/"+food.getName()+".png").toExternalForm(),food,null,null));
        for(Resource food:shop.getResources()) shopImages.add(new ShopImages(getClass().getResource("/IMAGE/Shop/"+food.getName()+".png").toExternalForm(),null,null,food));
        for(Weapon weapon:shop.getWeapons()) shopImages.add(new ShopImages(getClass().getResource("/IMAGE/Shop/"+weapon.getName()+".png").toExternalForm(),null,weapon,null));
    }

    private void goToGovernmentMenu() {
        pane.getChildren().remove(hBox);
        if(!pane.getChildren().contains(taxRateVBox)) {
            foodRateVBox=new VBox();
            taxRateVBox = new VBox();
            pane.getChildren().add(taxRateVBox);
            showTaxRate();
        }
    }

    private void drag() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName=keyEvent.getCode().getName();
                if (keyName.equals("D")){
                    try {
                        right();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (keyName.equals("A")) {
                    try {
                        left();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (keyName.equals("W")) {
                    try {
                        up();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (keyName.equals("S")) {
                    try {
                        down();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (keyName.equals("Z")) zoomIn();
                else if (keyName.equals("X")) zoomOut();
            }
        });
    }

    private void zoomOut() {
        size++;
        zoomLevel--;
        imageWidth=stageWidth/size;
        imageHeight=stageHeight/size;
        images=new ImageView[size][size];
        pane.getChildren().clear();
        initialize();
    }

    private void zoomIn() {
        size--;
        zoomLevel++;
        imageWidth=stageWidth/size;
        imageHeight=stageHeight/size;
        images=new ImageView[size][size];
        pane.getChildren().clear();
        initialize();
    }

    private void up() throws InterruptedException {
        startCol--;
        for (int i=size-1;i>0;i--){
            for (int j=0;j<size;j++){
                images[j][i]=images[j][i-1];
            }
        }
        for (int j=0;j<size;j++){
            images[j][0]=new ImageView(new Image(Map.MAP_NUMBER_ONE.getACell(startRow+j,startCol).getTexture().getImageAddress()));
        }
        update();

    }

    private void down() throws InterruptedException {
        startCol++;
        for (int i=0;i<size-1;i++){
            for (int j=0;j<size;j++){
                images[j][i]=images[j][i+1];
            }
        }
        for (int j=0;j<size;j++){
            images[j][size-1]=new ImageView(new Image(Map.MAP_NUMBER_ONE.getACell(startRow+j,startCol+size-1).getTexture().getImageAddress()));
        }
        update();
    }

    private void left() throws InterruptedException {
        startRow--;
        for (int i=size-1;i>0;i--){
            for (int j=0;j<size;j++){
                images[i][j]=images[i-1][j];
            }
        }
        for (int j=0;j<size;j++){
            images[0][j]=new ImageView(new Image(Map.MAP_NUMBER_ONE.getACell(startRow,j+startCol).getTexture().getImageAddress()));
        }
        update();
    }

    private void right() throws InterruptedException {
        startRow++;
        for (int i=0;i<size-1;i++){
            for (int j=0;j<size;j++){
                images[i][j]=images[i+1][j];
            }
        }
        for (int j=0;j<size;j++){
            images[size-1][j]=new ImageView(new Image(Map.MAP_NUMBER_ONE.getACell(size-1+startRow,j+startCol).getTexture().getImageAddress()));
        }
        update();
    }


    private void changeMenu(HBox hBox) {
        hBox.getChildren().clear();
    }
    private Pair<Integer,Integer> getBuildingDestination(BuildingImage buildingImage) {
        double maxIntersectionArea = 0;
        int finalI = 0;
        int finalJ = 0;
        for (int i = 0; i < images.length; i++) {
            for (int j = 0; j < images[i].length; j++) {
                Bounds buildingBounds = buildingImage.getBoundsInParent();
                Bounds imageBounds = images[i][j].getBoundsInParent();
                if(buildingBounds.intersects(imageBounds)) {
                    double startX = Math.max(buildingBounds.getMinX(), imageBounds.getMinX());
                    double endX = Math.min(buildingBounds.getMaxX(), imageBounds.getMaxX());
                    double startY = Math.max(buildingBounds.getMinY(), imageBounds.getMinY());
                    double endY = Math.min(buildingBounds.getMaxY(), imageBounds.getMaxY());
                    double width = endX - startX;
                    double height = endY - startY;
                    double intersectionArea = width * height;
                    if(maxIntersectionArea < intersectionArea) {
                        maxIntersectionArea = intersectionArea;
                        finalI = i;
                        finalJ = j;
                    }
                }
            }
        }
        double deltaX = images[finalI][finalI].getBoundsInParent().getCenterX() -
                buildingImage.getBoundsInParent().getCenterX();
        double deltaY = images[finalI][finalJ].getBoundsInParent().getCenterY() -
                buildingImage.getBoundsInParent().getCenterY();
        buildingImage.setX(buildingImage.getX() + deltaX);
        buildingImage.setY(buildingImage.getY() + deltaY);
        return new Pair<>(finalI,finalJ);
    }

    public void createBuildingMenuBar() {
        javafx.scene.image.ImageView menu = new ImageView(new Image(
                Objects.requireNonNull(ProfileMenu.class.getResource("/IMAGE/Buildings/building menu.png")).toString(), 1200, 182, false, false));
        javafx.scene.image.ImageView front = new ImageView(new Image(
                Objects.requireNonNull(ProfileMenu.class.getResource("/IMAGE/ICONS/front.png")).toString(), 22.5, 22.5, false, false));
        javafx.scene.image.ImageView back = new ImageView(new Image(
                Objects.requireNonNull(ProfileMenu.class.getResource("/IMAGE/ICONS/back.png")).toString(), 22.5, 22.5, false, false));
        hBox = new HBox(); //hBox for buildings
        hBox.setAlignment(Pos.CENTER);
        hBox.setLayoutX(267.5);
        hBox.setLayoutY(612.5);
        hBox.setSpacing(10);
        front.setOnMouseClicked(mouseEvent -> moveMenuToRight(hBox));
        back.setOnMouseClicked(mouseEvent -> moveMenuToLeft(hBox));
        menu.setX(0);
        menu.setY(532.5);
        front.setX(635.5);
        front.setY(688.5);
        back.setY(688.5);
        back.setX(265.5);
        pane.getChildren().addAll(menu,front,back);
        setBuildingImages(hBox);
        pane.getChildren().add(hBox);
    }

    private void showFoodRate() {
        createBackButton();
        foodRateVBox.setSpacing(10);
        foodRateVBox.setAlignment(Pos.CENTER);
        foodRateVBox.setLayoutX(400);
        foodRateVBox.setLayoutY(620);
        Slider slider = new Slider(-2,2,5);
        setSlider(slider);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int roundedValue = (int) Math.round(newValue.doubleValue());
            slider.setValue(roundedValue);
            //todo complete here by invoking government methods
        });
        Text text = new Text();
        text.setText("Food Rate");
        text.setFont(new Font("Callibri",20));
        foodRateVBox.getChildren().addAll(text,slider);
    }

    private void createBackButton() {
        Button back = new Button("back");
        back.getStyleClass().add("button2");
        pane.getChildren().add(back);
        back.setLayoutY(610);
        back.setLayoutX(275);
        back.setOnMouseClicked(me -> backToBuildingMenu());
    }

    private void showTaxRate() {
        createBackButton();
        taxRateVBox.setAlignment(Pos.CENTER);
        taxRateVBox.setSpacing(10);
        taxRateVBox.setLayoutX(400);
        taxRateVBox.setLayoutY(620);
        Slider slider = new Slider(-3,8,12);
        setSlider(slider);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int roundedValue = (int) Math.round(newValue.doubleValue());
            slider.setValue(roundedValue);
            //todo complete here by invoking government methods
        });
        Text text = new Text();
        text.setText("Tax Rate");
        text.setFont(new Font("Callibri",20));
        taxRateVBox.getChildren().addAll(text,slider);
    }

    private void backToBuildingMenu() {
        pane.getChildren().remove(foodRateVBox);
        pane.getChildren().remove(taxRateVBox);
        createBuildingMenuBar();
    }

    private void setSlider(Slider slider) {
        slider.setPrefWidth(175);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setBlockIncrement(1);
        slider.setValue(0);
    }

    private String getBuildingImageAddress(String name) {
        return getClass().getResource("/IMAGE/Buildings/Other Buildings/" + name + ".png").toExternalForm();
    }

    private void moveMenuToRight(HBox hBox) {
        if(pane.getChildren().contains(hBox)) {
            if (startIndexForBuilding != 20) {
                startIndexForBuilding += 5;
                hBox.getChildren().clear();
                setBuildingImages(hBox);
            }
        } else if(pane.getChildren().contains(taxRateVBox)) {
            pane.getChildren().remove(taxRateVBox);
            pane.getChildren().add(foodRateVBox);
            showFoodRate();
        }
        else if (pane.getChildren().contains(sell)){
            updateShopImages(++shopStartRow);
        }
    }
    private void moveMenuToLeft(HBox hBox) {
        if(pane.getChildren().contains(hBox)) {
            if (startIndexForBuilding != 0) {
                startIndexForBuilding -= 5;
                hBox.getChildren().clear();
                setBuildingImages(hBox);
            }
        } else if(pane.getChildren().contains(foodRateVBox)) {
            pane.getChildren().remove(foodRateVBox);
            pane.getChildren().add(taxRateVBox);
            showTaxRate();
        }
        else if (pane.getChildren().contains(sell)){
            updateShopImages(--shopStartRow);
        }
    }

    public void setBuildingImages (HBox hBox) {
        for (int i = startIndexForBuilding; i < Math.min(startIndexForBuilding + 5,buildingImages.size()); i++) {
            hBox.getChildren().add(buildingImages.get(i));
        }
    }
}