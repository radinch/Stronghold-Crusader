package View.graphic;
import Controller.MapMenuController;
import Model.HoverRectangle;
import Model.buildings.BuildingImage;
import Model.gameandbattle.map.Map;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class GameMap extends Application {

    private double maxHeight = 0;
    private double maxWidth = 0;
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
    private ArrayList<HoverRectangle> hoverRectangles;
    private Pane pane;
    {
        images=new ImageView[size][size];
        stageHeight=imageHeight*size;
        stageWidth=imageWidth*size;
        hoverRectangles=new ArrayList<>();
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
        createMenuBar();
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
        createMenuBar();
    }

    private void drag() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName=keyEvent.getCode().getName();
                if (keyName.equals("Right")){
                    try {
                        right();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (keyName.equals("Left")) {
                    try {
                        left();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (keyName.equals("Up")) {
                    try {
                        up();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (keyName.equals("Down")) {
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

    public void createMenuBar() {
        javafx.scene.image.ImageView menu = new ImageView(new Image(
                Objects.requireNonNull(ProfileMenu.class.getResource("/IMAGE/Buildings/building menu.png")).toString(), 1200, 182, false, false));
        javafx.scene.image.ImageView front = new ImageView(new Image(
                Objects.requireNonNull(ProfileMenu.class.getResource("/IMAGE/ICONS/front.png")).toString(), 22.5, 22.5, false, false));
        HBox hBox = new HBox();
        front.setOnMouseClicked(mouseEvent -> changeMenu(hBox));
        menu.setX(0);
        menu.setY(532.5);
        front.setX(635.5);
        front.setY(688.5);
        pane.getChildren().addAll(menu,front);
        setBuildingImages(hBox);
    }

    private String getBuildingImageAddress(String name) {
        return getClass().getResource("/IMAGE/Buildings/Other Buildings/" + name + ".png").toExternalForm();
    }

    private void changeMenu(HBox hBox) {
        hBox.getChildren().clear();
    }

    public void setBuildingImages (HBox hBox) {
        hBox.setAlignment(Pos.CENTER);
        hBox.setLayoutX(267.5);
        hBox.setLayoutY(612.5);
        ArrayList<BuildingImage> buildingImages = new ArrayList<>();
        buildingImages.add(new BuildingImage(getBuildingImageAddress("Small stone gatehouse")));
        buildingImages.add(new BuildingImage(getBuildingImageAddress("big stone gatehouse")));
        buildingImages.add(new BuildingImage(getBuildingImageAddress("Mercenary Post")));
        buildingImages.add(new BuildingImage(getBuildingImageAddress("barrack")));
        buildingImages.add(new BuildingImage(getBuildingImageAddress("engineer guild")));
        hBox.setSpacing(10);
        for (BuildingImage buildingImage : buildingImages) {
            hBox.getChildren().add(buildingImage);
        }
        pane.getChildren().add(hBox);
    }
}