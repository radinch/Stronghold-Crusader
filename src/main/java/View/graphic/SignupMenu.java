package View.graphic;

import Controller.SignUpController;
import Model.signup_login_profile.Slogan;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;

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

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane signupPane = FXMLLoader.load(
                new URL(SignupMenu.class.getResource("/FXML/SignupMenu.fxml").toExternalForm()));
        Scene scene = new Scene(signupPane);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void initialize() {
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
        Submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(Username.getText().isEmpty())
                    usernameLabel.setText("Username is null");
                else if(Password.getText().isEmpty())
                    passwordLabel.setText("Password is null");
                else if(Email.getText().isEmpty())
                    usernameLabel.setText("Email is null");
                else if(NickName.getText().isEmpty())
                    usernameLabel.setText("NickName is null");
            }
        });
    }
    public String usernameValidation() {

        if(!signUpController.isUsernameValid(Username.getText()))
            return ("Username Not Valid");
        else
            return ("Username IS Valid");
    }
    public String emailValidation() {
        if(!signUpController.isEmailFormatOk(Email.getText()))
            return ("Email Not Valid");
        else
            return ("Email IS Valid");
    }
    public String passwordValidation() {
        if(Password.isVisible()) {
            if (!SignUpController.isPasswordStrong(Password.getText()))
                return ("Password is Weak");
            else
                return ("Password is Strong");
        }
        else {
            if (!SignUpController.isPasswordStrong(VisiblePass.getText()))
                return ("Password is Weak");
            else
                return ("Password is Strong");
        }
    }

    public void submit(MouseEvent mouseEvent) {
    }
}
