package View.graphic;

import Controller.DataBank;
import Controller.ProfileMenuController;
import Controller.SignUpController;
import Model.signup_login_profile.User;
import View.menus.LoginMenu;
import View.menus.SignUpMenu;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main extends Application {

    public static void main(String[] args) throws IOException {
        DataBank.setAllUsers(SignUpController.readFromJson());
        ObjectMapper mapper = new ObjectMapper();
        Scanner scanner = new Scanner(System.in);
        File loggedInUser = new File("loggedInUser.json");
        launch(args);
        // TODO: 4/12/2023 1.unit 2.final check 3.package 4.errors method
    }

    @Override
    public void start(Stage stage) throws Exception {
        DataBank.setStage(stage);
        BorderPane borderPane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/FXML/main.fxml")));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.getIcons().add(new Image(Main.class.getResource("/IMAGE/icon.png").openStream()));
        stage.show();
    }

    public void signup(MouseEvent mouseEvent) throws Exception {
        new SignupMenu().start(DataBank.getStage());
    }

    public void login(MouseEvent mouseEvent) throws Exception {
        new View.graphic.LoginMenu().start(DataBank.getStage());
    }

    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }


}