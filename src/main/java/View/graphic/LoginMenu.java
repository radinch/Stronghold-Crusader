package View.graphic;

import Controller.DataBank;
import Controller.LoginController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.Objects;

public class LoginMenu extends Application {

    private static int random_int = (int) Math.floor(Math.random() * (DataBank.captcha.size() - 1) + 1);
    private static javafx.scene.image.ImageView captcha = new ImageView(new Image(
            Objects.requireNonNull(LoginMenu.class.getResource("/IMAGE/Captcha/" + DataBank.captcha.get(random_int) +
                    ".png" )).toString(), 160 ,60, false, false));

    LoginController loginController = new LoginController();

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        stage.setWidth(700);
        stage.setHeight(550);
        scene.getStylesheets().add(String.valueOf(LoginMenu.class.getResource("/CSS/style1.css")));
        javafx.scene.image.ImageView background = new ImageView(new Image(
                Objects.requireNonNull(LoginMenu.class.getResource("/IMAGE/22.jpg")).toString(), 700 ,550, false, false));
        VBox vBox = new VBox();
        vBox.setSpacing(35);
        vBox.setAlignment(Pos.CENTER);
        Label label = new Label();
        label.setText("Welcome back");
        label.setId("label");
        TextField textField = new TextField();
        textField.setMaxWidth(300);
        textField.getStyleClass().add("text-input");
        textField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("text-input");
        passwordField.setPromptText("Password");
        Button button = new Button();
        button.setText("Submit");
        button.getStyleClass().add("submit-button");
        VBox vBox1 = new VBox();
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(20);
        TextField captchaField = new TextField();
        captchaField.getStyleClass().add("text-input");
        captchaField.setPromptText("Captcha");
        captchaField.setMaxWidth(160);
        vBox1.getChildren().addAll(captcha,captchaField);
        button.setOnMousePressed(mouseEvent -> {
            try {
                submit(textField,passwordField,captchaField,borderPane);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        vBox.getChildren().addAll(label,textField,passwordField,vBox1,button);
        borderPane.getChildren().add(background);
        borderPane.setCenter(vBox);
        stage.setScene(scene);
        stage.show();

    }

    private void submit(TextField textField, PasswordField passwordField, TextField captchaTextField,BorderPane pane) throws Exception {
        String username = textField.getText();
        String password = passwordField.getText();
        if(!loginController.doesUserExists(username)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("username not found");
            alert.showAndWait();
        }
        else if(!loginController.isPasswordCorrect(username,password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("password is not correct");
            alert.showAndWait();
        }
        else if(!captchaTextField.getText().equals(String.valueOf(DataBank.captcha.get(random_int)))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("captcha is not correct");
            alert.showAndWait();
            random_int = (int) Math.floor(Math.random() * (DataBank.captcha.size() - 1) + 1);
            captcha = new ImageView(new Image(
                    Objects.requireNonNull(LoginMenu.class.getResource("/IMAGE/Captcha/" + DataBank.captcha.get(random_int) +
                            ".png" )).toString(), 160 ,60, false, false));
        }
        else {
            loginController.graphicLogin(username,password);
        }
    }
}
