package View.graphic;

import Controller.DataBank;
import Controller.LoginController;
import Controller.SignUpController;
import Model.signup_login_profile.Slogan;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class SignupMenu extends Application {
    public SignUpController signUpController = new SignUpController();
    public TextField Username;
    public Label usernameLabel;
    public TextField Email;
    public Label EmailLabel;
    public PasswordField Password;
    public Label passwordLabel;
    public CheckBox Show;
    public TextField VisiblePass;
    public Button RandomPass;
    public CheckBox sloganBox;
    public TextField slogan;
    public TextField NickName;
    public Button RandomSlogan;
    public Button Submit;

    private static int random_int = (int) Math.floor(Math.random() * (DataBank.captcha.size() - 1) + 1);
    public javafx.scene.image.ImageView captcha;
    public TextField captchaText;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane signupPane = FXMLLoader.load(
                new URL(Objects.requireNonNull(SignupMenu.class.getResource("/FXML/SignupMenu.fxml")).toExternalForm()));
        Scene scene = new Scene(signupPane);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void initialize() {
        captcha.setImage(new Image(
                Objects.requireNonNull(SignupMenu.class.getResource("/IMAGE/Captcha/" + DataBank.captcha.get(random_int) +
                        ".png" )).toString(), 160 ,60, false, false));
        Username.textProperty().addListener((observable, oldText, newText)->{
            usernameLabel.setText(usernameValidation());
        });
        Email.textProperty().addListener((observable, oldText, newText)->{
            EmailLabel.setText(emailValidation());
        });
        Password.textProperty().addListener((observable, oldText, newText) -> {
            passwordLabel.setText(passwordValidation());
        });
        VisiblePass.textProperty().addListener((observable, oldText, newText) -> {
            passwordLabel.setText(passwordValidation());
        });
        Show.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(Password.isVisible()) {
                    VisiblePass.setText(Password.getText());
                    Password.setVisible(false);
                    VisiblePass.setVisible(true);
                }
                else {
                    Password.setText(VisiblePass.getText());
                    Password.setVisible(true);
                    VisiblePass.setVisible(false);
                }
            }
        });
        RandomPass.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String newPass = SignUpController.generateRandomString(9);
                Alert pass = new Alert(Alert.AlertType.INFORMATION);
                pass.setTitle("Success");
                pass.setHeaderText("your random pass word is\nplease re-enter it in the Password Field");
                pass.setContentText(newPass);
                pass.showAndWait();
                Password.setText(newPass);
            }
        });
        sloganBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                slogan.setVisible(!slogan.isVisible());
                RandomSlogan.setVisible(!RandomSlogan.isVisible());
            }
        });
        RandomSlogan.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                slogan.setText(signUpController.randomSlogan());
            }
        });
    }
    public String usernameValidation() {

        if(!signUpController.isUsernameValid(Username.getText())) {
            usernameLabel.getStyleClass().add("error-text");
            return ("Username Not Valid");
        }
        else {
            usernameLabel.getStyleClass().add("success-text");
            return ("Username IS Valid");
        }
    }
    public String emailValidation() {
        if(!signUpController.isEmailFormatOk(Email.getText())) {
            EmailLabel.getStyleClass().add("error-text");
            return ("Email Not Valid");
        }
        else {
            EmailLabel.getStyleClass().add("success-text");
            return ("Email IS Valid");
        }
    }
    public String passwordValidation() {
        if(Password.isVisible()) {
            if (!SignUpController.isPasswordStrong(Password.getText())) {
                passwordLabel.getStyleClass().add("error-text");
                return ("Password is Weak");
            }
            else {
                passwordLabel.getStyleClass().add("success-text");
                return ("Password is Strong");
            }
        }
        else {
            if (!SignUpController.isPasswordStrong(VisiblePass.getText())) {
                passwordLabel.getStyleClass().add("error-text");
                return ("Password is Weak");
            }
            else {
                passwordLabel.getStyleClass().add("success-text");
                return ("Password is Strong");
            }
        }
    }

    public void submit(MouseEvent mouseEvent) throws Exception {
        String capText = captchaText.getText();
        String username = Username.getText();
        String password = Password.getText();
        String email = Email.getText();
        String nickname = NickName.getText();
        String sloganText = null;
        if(slogan != null) {
            sloganText = slogan.getText();
        }
        if(!signUpController.isUsernameValid(username)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("username is not valid");
            alert.showAndWait();
            reloadCaptcha();
        }
        else if(DataBank.getUserByUsername(username) != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("username already exists");
            alert.showAndWait();
            reloadCaptcha();
        }
        else if(!SignUpController.isPasswordStrong(password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("password is weak");
            alert.showAndWait();
            reloadCaptcha();
        }
        else if(!signUpController.isEmailFormatOk(email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("email is not valid");
            alert.showAndWait();
            reloadCaptcha();
        }
        else if(signUpController.isEmailUsed(email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("email is already used");
            alert.showAndWait();
            reloadCaptcha();
        }
        else if(!capText.equals(String.valueOf(DataBank.captcha.get(random_int)))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("captcha is not correct");
            alert.showAndWait();
            reloadCaptcha();
        }
        else if(nickname == null || username == null || password == null || email == null || capText == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("empty field");
            alert.showAndWait();
            reloadCaptcha();
        }
        else {
            signUpController.graphicSignUp(username, password, email, nickname, sloganText);
            new QuestionMenu().start(DataBank.getStage());
        }
    }

    private void reloadCaptcha() {
        random_int = (int) Math.floor(Math.random() * (DataBank.captcha.size() - 1) + 1);
        captcha.setImage(new Image(
                Objects.requireNonNull(LoginMenu.class.getResource("/IMAGE/Captcha/" + DataBank.captcha.get(random_int) +
                        ".png" )).toString(), 160 ,60, false, false));
    }
}
