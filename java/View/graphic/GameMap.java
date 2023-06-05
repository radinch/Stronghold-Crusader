package View.graphic;
import Model.gameandbattle.map.Map;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class GameMap extends Application {
    private int zoomLevel=0;
    private int stageWidth=0;
    private int stageHeight=0;
    private int imageWidth=100;
    private int imageHeight=100;
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
                System.out.println(i+"============="+j);
                images[i-startRow][j-startCol]=new ImageView(new Image(Map.MAP_NUMBER_ONE.getACell(i,j).getTexture().getImageAddress()));
                images[i-startRow][j-startCol].setFitWidth(imageWidth);
                images[i-startRow][j-startCol].setFitHeight(imageHeight);
                images[i-startRow][j-startCol].setX((i-startRow)*imageWidth);
                images[i-startRow][j-startCol].setY((j-startCol)*imageHeight);
                pane.getChildren().add(images[i-startRow][j-startCol]);
            }
        }
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
}