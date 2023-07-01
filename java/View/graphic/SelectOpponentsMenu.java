package View.graphic;

import Controller.DataBank;
import Controller.MainMenuController;
import Model.gameandbattle.map.Cell;
import Model.gameandbattle.map.Map;
import javafx.scene.control.TextField;
import org.example.User;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;


public class SelectOpponentsMenu extends Application {
    Button ChangeMap;
    TextField mapNumber;
    private VBox vBox;
    private HBox hBox;
    Button startGame;
    Button playerName;
    CheckBox checkBox;
    ArrayList<User> players;
    {
        players=new ArrayList<>();
    }
    int counter=0;
    @Override
    public void start(Stage stage) throws Exception {
        ChangeMap = new Button("Change Map");
        mapNumber = new TextField("Choose Map Number");
        mapNumber.setMaxWidth(300);
        BorderPane borderPane=new BorderPane();
        vBox=new VBox();
        hBox=new HBox();
        startGame=new Button("start game");
        ChangeMap.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Cell[][] newMap;
                int mapNum = Integer.parseInt(mapNumber.getText());
                if(mapNum != 1) {
                    newMap = CreateMapMenu.getCellFromJson(mapNum);
                    for (int i = 0; i < 200; i++) {
                        for (int j = 0; j < 200; j++) {
                            Map.MAP_NUMBER_ONE.setACell(i, j, newMap[i][j]);
                        }
                    }
                }
            }
        });
        startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkBox.isSelected()) players.add(DataBank.getAllUsers().get(counter));
                else players.remove(DataBank.getAllUsers().get(counter));
                ///// use players arraylist
                MainMenuController mainMenuController=new MainMenuController();
                mainMenuController.setPlayers(players);
                DataBank.setGovernments(mainMenuController.createGovernments());
                try {
                    DataBank.initializeBuildingName();
                    DataBank.initializeWalls();
                    DataBank.initializeAllUnits();
                    DataBank.setCurrentGovernment(DataBank.getGovernments().get(0));
                    new GameMap().start(DataBank.getStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        vBox.getChildren().addAll(hBox,startGame,mapNumber,ChangeMap);
        vBox.setAlignment(Pos.CENTER);
        createHbox();
        borderPane.setCenter(vBox);
        Scene scene=new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    private void createHbox() {
        javafx.scene.image.ImageView front = new ImageView(new Image(
                Objects.requireNonNull(ProfileMenu.class.getResource("/IMAGE/ICONS/front.png")).toString(), 22.5, 22.5, false, false));
        playerName=new Button(DataBank.getAllUsers().get(counter).getUsername());
        checkBox=new CheckBox();
        front.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (checkBox.isSelected()) players.add(DataBank.getAllUsers().get(counter));
                else players.remove(DataBank.getAllUsers().get(counter));
                counter=(counter+1)%DataBank.getAllUsers().size();
                playerName.setText(DataBank.getAllUsers().get(counter).getUsername());
            }
        });
        hBox.getChildren().addAll(playerName,checkBox,front);
        hBox.setAlignment(Pos.CENTER);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
