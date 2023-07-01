package View.graphic;

import Controller.DataBank;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ScoreboardController extends Application{
    public int startNumber = 0;
    public int end = 2;
    public VBox box;
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(new URL(ScoreboardController.class.getResource("/FXML/scoreboard.fxml").toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setHeight(400);
        stage.setWidth(600);
        Button button = new Button("back");
        button.getStyleClass().add("submit-button");
        button.setOnMousePressed(mouseEvent -> {
            try {
                backToProfile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        button.setLayoutY(300);
        button.setLayoutX(175);
        pane.getChildren().addAll(button);
        stage.show();
    }

    private void backToProfile() throws Exception {
        new ProfileMenu().start(DataBank.getStage());
    }

    public void initialize()
    {
        ArrayList<User> temp = new ArrayList<>();
        for (User allUser : DataBank.getAllUsers()) {
            if(!hasUser(temp,allUser))
                temp.add(allUser);
        }
        box.setLayoutY(-100);
        loadBox(temp);
        box.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                if(scrollEvent.getDeltaY() < 0) {
                    startNumber = Math.min(startNumber + 2, DataBank.getAllUsers().size() - 2);
                    end = Math.min(end + 2, DataBank.getAllUsers().size());
                    loadBox(temp);
                }
                else if(scrollEvent.getDeltaY() > 0)
                {
                    startNumber = Math.max(startNumber - 2, 0);
                    end = Math.max(end - 2, 2);
                    loadBox(temp);
                }
            }
        });
    }
    private void loadBox(ArrayList<User> temp) {
        box.getChildren().clear();
        for (int i = startNumber; i < end; i++) {
            for (int j = 0; j < temp.size(); j++) {
                if(temp.get(j).getRank() == i+1)
                {
                    Label username = new Label(i+1 + ") " + temp.get(j).getUsername() + "    " +
                            temp.get(j).getHighScore() + DataBank.isThisUserOnline(temp.get(j).getUsername())
                    + "    " + temp.get(j).getLastSeen());
                    username.getStyleClass().add("label2");
                    box.getChildren().add(username);
                }
            }
        }
    }

    private boolean hasUser(ArrayList<User> temp,User allUser) {
        for (User user : temp) {
            if(user.getUsername().equals(allUser.getUsername()))
                return true;
        }
        return false;
    }
}
