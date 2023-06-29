package View.graphic;

import Controller.DataBank;
import Controller.LoginController;
import Controller.SignUpController;
import Model.signup_login_profile.SecurityQuestion;
import org.example.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginMenu extends Application {

    private static int random_int = (int) Math.floor(Math.random() * (DataBank.captcha.size() - 1) + 1);
    private static javafx.scene.image.ImageView captcha = new ImageView(new Image(
            Objects.requireNonNull(LoginMenu.class.getResource("/IMAGE/Captcha/" + DataBank.captcha.get(random_int) +
                    ".png" )).toString(), 160 ,60, false, false));

    LoginController loginController = new LoginController();

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = FXMLLoader.load(Objects.requireNonNull(LoginMenu.class.getResource("/FXML/loginMenu.fxml")));
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(String.valueOf(LoginMenu.class.getResource("/CSS/style1.css")));
        javafx.scene.image.ImageView background = new ImageView(new Image(
                Objects.requireNonNull(LoginMenu.class.getResource("/IMAGE/22.jpg")).toString(), 700 ,552, false, false));
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

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        Text text = new Text();
        text.setFill(new Color(1,1,1,1));
        text.setText("Don't have an account?");
        Hyperlink hyperlink = new Hyperlink();
        hyperlink.setText("Sign up");
        hyperlink.setOnMousePressed(mouseEvent -> {
            try {
                changeToSignUp();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        hBox.getChildren().addAll(text,hyperlink);

        Hyperlink forgetPassword = new Hyperlink();
        forgetPassword.setText("Forget your password?");
        forgetPassword.setOnMousePressed(mouseEvent -> passwordRecovery(borderPane,vBox));

        vBox1.getChildren().addAll(captcha,captchaField,hBox,forgetPassword);
        button.setOnMousePressed(mouseEvent -> {
            try {
                submit(textField,passwordField,captchaField,borderPane,vBox1);
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

    private void passwordRecovery(BorderPane borderPane, VBox vBox) {
        borderPane.getChildren().remove(vBox);
        VBox vBox1 = new VBox();
        vBox1.setSpacing(45);
        vBox1.setAlignment(Pos.CENTER);
        Text text = new Text();
        text.setFill(new Color(1,1,1,1));
        text.setFont(new Font("Calibri",30));
        text.setText("Enter your username");
        TextField textField = new TextField();
        textField.setMaxWidth(300);
        textField.getStyleClass().add("text-input");
        textField.setPromptText("Username");
        Button button = new Button();
        button.setText("Continue");
        button.getStyleClass().add("submit-button");
        button.setOnMousePressed(mouseEvent -> askQuestion(borderPane,vBox1,textField));
        vBox1.getChildren().addAll(text,textField,button);
        borderPane.setCenter(vBox1);
    }

    private void askQuestion(BorderPane borderPane, VBox vBox, TextField textField) {
        String username = textField.getText();
        if(!loginController.doesUserExists(username)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("username not found");
            alert.showAndWait();
        }
        else {
            borderPane.getChildren().remove(vBox);
            VBox vBox1 = new VBox();
            vBox1.setSpacing(45);
            vBox1.setAlignment(Pos.CENTER);
            Text text = new Text();
            text.setFill(new Color(1,1,1,1));
            text.setFont(new Font("Calibri",30));
            text.setText(SecurityQuestion.getQuestionByNUmber(Objects.requireNonNull(
                    DataBank.getUserByUsername(username)).getSecurityQuestion()) + "?");
            textField.setText("");
            TextField textField1 = new TextField();
            textField1.setMaxWidth(300);
            textField1.getStyleClass().add("text-input");
            textField1.setPromptText("Answer");
            TextField textField2 = new TextField();
            textField2.setMaxWidth(300);
            textField2.getStyleClass().add("text-input");
            textField2.setPromptText("New Password");
            Button button = new Button();
            button.setText("Submit");
            button.getStyleClass().add("submit-button");
            button.setOnMousePressed(mouseEvent -> {
                try {
                    checkAnswer(username,textField1,textField2);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            vBox1.getChildren().addAll(text,textField1,textField2,button);
            borderPane.setCenter(vBox1);
        }
    }

    private void checkAnswer(String username,TextField textField1, TextField textField2) throws Exception {
        if(!textField1.getText().equals(Objects.requireNonNull(
                DataBank.getUserByUsername(username)).getAnswer())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("answer in not correct");
            alert.showAndWait();
        }
        else if(!SignUpController.isPasswordStrong(textField2.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("password is weak");
            alert.showAndWait();
        }
        else {
            Objects.requireNonNull(DataBank.getUserByUsername(username)).
                    setCodedPassword(User.hashString(textField2.getText()));
            SignUpController.writeToJson(DataBank.getAllUsers());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("change password");
            alert.setContentText("password changed successfully");
            alert.showAndWait();
            new LoginMenu().start(DataBank.getStage());
        }

    }


    private void changeToSignUp() throws Exception {
        new SignupMenu().start(DataBank.getStage());
    }

    private void submit(TextField textField, PasswordField passwordField, TextField captchaTextField
            , BorderPane pane, VBox vBox1) throws Exception {
        String username = textField.getText();
        String password = passwordField.getText();
        if(!loginController.doesUserExists(username)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("username not found");
            alert.showAndWait();
            reloadCaptcha(vBox1);
        }
        else if(!loginController.isPasswordCorrect(username,password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("password is not correct");
            alert.showAndWait();
            reloadCaptcha(vBox1);
        }
        else if(!captchaTextField.getText().equals(String.valueOf(DataBank.captcha.get(random_int)))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("captcha is not correct");
            alert.showAndWait();
            reloadCaptcha(vBox1);
        }
        else {
            loginController.graphicLogin(username,password);
        }
    }

    private void reloadCaptcha(VBox vBox1) {
        random_int = (int) Math.floor(Math.random() * (DataBank.captcha.size() - 1) + 1);
        vBox1.getChildren().remove(captcha);
        captcha = new ImageView(new Image(
                Objects.requireNonNull(LoginMenu.class.getResource("/IMAGE/Captcha/" + DataBank.captcha.get(random_int) +
                        ".png" )).toString(), 160 ,60, false, false));
        vBox1.getChildren().add(0,captcha);
    }
}
