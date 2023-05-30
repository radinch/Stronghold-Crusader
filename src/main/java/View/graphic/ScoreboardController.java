package View.graphic;

import Controller.DataBank;
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

import java.net.URL;
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
        box.setLayoutY(-100);
        loadBox();
        box.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                if(scrollEvent.getDeltaY() < 0) {
                    startNumber = Math.min(startNumber + 2, DataBank.getAllUsers().size() - 2);
                    end = Math.min(end + 2, DataBank.getAllUsers().size());
                    loadBox();
                }
                else if(scrollEvent.getDeltaY() > 0)
                {
                    startNumber = Math.max(startNumber - 2, 0);
                    end = Math.max(end - 2, 2);
                    loadBox();
                }
            }
        });
    }
    private void loadBox() {
        box.getChildren().clear();
        for (int i = startNumber; i < end; i++) {
            for (int j = 0; j < DataBank.getAllUsers().size(); j++) {
                if(DataBank.getAllUsers().get(j).getRank() == i+1)
                {
                    Label username = new Label(i+1 + ") " + DataBank.getAllUsers().get(j).getUsername() + "         " +
                            DataBank.getAllUsers().get(j).getHighScore());
                    username.getStyleClass().add("label2");
                    box.getChildren().add(username);
                }
            }
        }
    }
}
