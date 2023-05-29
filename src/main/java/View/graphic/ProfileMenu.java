package View.graphic;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class ProfileMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(String.valueOf(ProfileMenu.class.getResource("/CSS/style1.css")));
        stage.setScene(scene);
        stage.setWidth(1200);
        stage.setHeight(700);
        javafx.scene.image.ImageView background = new ImageView(new Image(
                Objects.requireNonNull(ProfileMenu.class.getResource("/IMAGE/02.jpg")).toString(), 1200 ,700, false, false));
        pane.getChildren().add(background);
        createLabels(pane);
        stage.show();
    }

    private void createLabels(Pane pane) {
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setLayoutY(20);
        vBox.setLayoutX(20);
        createAttribute("Username",vBox);
        createAttribute("Password",vBox);
        createAttribute("Nickname",vBox);
        createAttribute("Email",vBox);
        pane.getChildren().add(vBox);
    }

    private void createAttribute(String attribute, VBox vBox) {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        Text userNameText  = new Text();
        userNameText.setText(attribute + ": ");
        userNameText.setFill(new Color(0.54,0,0,1));
        userNameText.setId("attribute");
        TextField textField = new TextField();
        textField.setPromptText(attribute);
        hBox.getChildren().addAll(userNameText,textField);
        vBox.getChildren().add(hBox);
    }
}
