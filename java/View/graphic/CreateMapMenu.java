package View.graphic;

import javafx.application.Application;
import Controller.DataBank;
import Controller.DropMenuController;
import Controller.MainMenuController;
import Model.gameandbattle.map.Cell;
import Model.gameandbattle.map.Map;
import Model.gameandbattle.map.Texture;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.User;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class CreateMapMenu extends Application {
    private VBox vBox;
    Button change;
    Button back;
    Button save;
    TextField textFieldX;
    TextField textFieldY;
    TextField textFieldTexture;
    TextField mapNumber;
    @Override
    public void start(Stage stage) throws Exception {
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                Map.MAP_NUMBER_TWO.setACell(i,j,Map.MAP_NUMBER_ONE.getACell(i,j));
                Map.MAP_NUMBER_THREE.setACell(i,j,Map.MAP_NUMBER_ONE.getACell(i,j));
            }
        }
        BorderPane borderPane=new BorderPane();
        javafx.scene.image.ImageView background = new ImageView(new Image(
                Objects.requireNonNull(ProfileMenu.class.getResource("/IMAGE/04.jpg")).toString(), 1200, 700, false, false));
        borderPane.getChildren().add(background);
        vBox=new VBox();
        change=new Button("Change Texture");
        back = new Button("Back");
        save = new Button("SAVE");
        textFieldX = new TextField();
        textFieldX.setPromptText("select X");
        textFieldY = new TextField();
        textFieldY.setPromptText("select Y");
        textFieldTexture = new TextField();
        textFieldTexture.setPromptText("Texture");
        mapNumber = new TextField();
        mapNumber.setPromptText("Choose Map number");
        textFieldX.setMaxWidth(300);textFieldY.setMaxWidth(300);textFieldTexture.setMaxWidth(300);mapNumber.setMaxWidth(300);
        change.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText(DropMenuController.setTileTexture(Integer.parseInt(textFieldX.getText()),Integer.parseInt(textFieldY.getText()),textFieldTexture.getText()));
                alert.showAndWait();
            }
        });
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new ProfileMenu().start(DataBank.getStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        save.setOnMouseClicked(new EventHandler<MouseEvent>() {
            ArrayList<String> dataMap = new ArrayList<>();
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (int i = 0; i < 200; i++) {
                    for (int j = 0; j < 200; j++) {
                        dataMap.add(Map.MAP_NUMBER_TWO.getACell(i, j).getTexture().getName());
                    }
                }
                writeToJson(dataMap,Integer.parseInt(mapNumber.getText()));
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("SAVED !");
                alert.showAndWait();
            }
        });
        change.getStyleClass().add("submit-button");
        back.getStyleClass().add("submit-button");
        save.getStyleClass().add("submit-button");
        vBox.getChildren().addAll(textFieldX,textFieldY,textFieldTexture,mapNumber,change,back,save);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);
        borderPane.setCenter(vBox);

        Scene scene=new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void writeToJson(ArrayList <String> dataCell,int number) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("Map"+number+".json"), dataCell);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<String> readFromJson(int number) {
        File file = new File("Map"+number+".json");
        ObjectMapper objectMapper = new ObjectMapper();
        if (file.length() != 0) {
            try {
                return objectMapper.readValue(file, new TypeReference<ArrayList<String>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    public static Cell[][] getCellFromJson(int number)
    {
        Gson gson = new Gson();
        ArrayList<String> data = readFromJson(number);
        Cell[][] map = Map.MAP_NUMBER_ONE.getMapCells();
        int indexI = 0;
        int indexJ = 0;
        for (int i = 0; i < data.size(); i++) {
            if(indexJ == 200)
            {
                indexJ = 0;
                indexI++;
            }
            map[indexI][indexJ].setTexture(Texture.getTextureByName(data.get(i)));
            indexJ++;
        }
        return map;
    }
}
