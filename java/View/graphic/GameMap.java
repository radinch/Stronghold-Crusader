package View.graphic;
import Model.buildings.BuildingImage;
import Model.gameandbattle.map.Map;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameMap extends Application {

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
    private int zoomLevel=0;
    private int stageWidth=0;
    private int stageHeight=0;
    private int imageWidth=120;
    private int imageHeight=75;
    private int startRow=49;
    private int startCol=8;
    private int size=10;
    private ImageView[][] images;
    private Scene scene;
    private Pane pane;
    {
        images=new ImageView[size][size];
        stageHeight=imageHeight*size;
        stageWidth=imageWidth*size;
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
        scene.getStylesheets().add(String.valueOf(GameMap.class.getResource("/CSS/style1.css")));
        stage.setScene(scene);
        drag();
        stage.show();
    }

    private void addBuildingFeature(BuildingImage buildingImage, String buildingName) {
        switch (buildingName) {
            case "Small stone gatehouse", "big stone gatehouse" ->
                    buildingImage.setOnMouseClicked(me -> goToGovernmentMenu());
        }
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

    private void initialize() {
        for (int i=startRow;i<Math.min(startRow+size,200);i++){
            for (int j=startCol;j<Math.min(size+startCol,200);j++){
                images[i-startRow][j-startCol]=new ImageView(new Image(Map.MAP_NUMBER_ONE.getACell(i,j).getTexture().getImageAddress()));
                images[i-startRow][j-startCol].setFitWidth(imageWidth);
                images[i-startRow][j-startCol].setFitHeight(imageHeight);
                images[i-startRow][j-startCol].setX((i-startRow)*imageWidth);
                images[i-startRow][j-startCol].setY((j-startCol)*imageHeight);
                pane.getChildren().add(images[i-startRow][j-startCol]);
            }
        }
        createBuildingMenuBar();
    }

    private void update(){
        //images should be loaded before this
        pane.getChildren().clear();
        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                images[i][j].setFitWidth(imageWidth);
                images[i][j].setFitHeight(imageHeight);
                images[i][j].setX(i*imageWidth);
                images[i][j].setY(j*imageHeight);
                pane.getChildren().add(images[i][j]);
            }
        }
        createBuildingMenuBar();
    }

    private void drag() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName=keyEvent.getCode().getName();
                if (keyName.equals("Right")){
                    right();
                }
                else if (keyName.equals("Left")) left();
                else if (keyName.equals("Up")) up();
                else if (keyName.equals("Down")) down();
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

    private void up() {
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

    private void down() {
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

    private void left() {
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

    private void right() {
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
        back.addEventFilter(KeyEvent.KEY_PRESSED, Event::consume);
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
        slider.addEventFilter(KeyEvent.KEY_PRESSED, Event::consume);
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
    }

    public void setBuildingImages (HBox hBox) {
        for (int i = startIndexForBuilding; i < Math.min(startIndexForBuilding + 5,buildingImages.size()); i++) {
            hBox.getChildren().add(buildingImages.get(i));
        }
    }
}