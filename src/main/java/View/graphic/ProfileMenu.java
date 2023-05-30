package View.graphic;

import Controller.DataBank;
import Model.signup_login_profile.Avatar;
import Model.signup_login_profile.User;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import Controller.SignUpController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.Objects;

public class ProfileMenu extends Application {
    private Pane pane;
    private Button changeUsername;
    private TextField newUsername;
    private Button submitUsername;
    private Text usernameError;
    private Button changeNick;
    private TextField newNick;
    private Button submitNick;
    private Button changeEmail;
    private TextField newEmail;
    private Button submitEmail;
    private Button changePassword;
    private Text emailError;
    private Button changeAvatar;
    private VBox allChanges;
    private Text currentUsername;
    private Text currentEmail;



    private Text currentNickname;

    @Override
    public void start(Stage stage) throws Exception {
        //sepehr
        pane = new Pane();
        Button changeAvatar = new Button("Change");
        changeAvatar.getStyleClass().add("button2");
        changeAvatar.setLayoutX(1200 - 145);
        changeAvatar.setLayoutY(100);
        changeAvatar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new AvatarMenu().start(DataBank.getStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(String.valueOf(ProfileMenu.class.getResource("/CSS/style1.css")));
        stage.setScene(scene);
        stage.setWidth(1200);
        stage.setHeight(700);
        javafx.scene.image.ImageView background = new ImageView(new Image(
                Objects.requireNonNull(ProfileMenu.class.getResource("/IMAGE/02.jpg")).toString(), 1200, 700, false, false));
        pane.getChildren().addAll(background, changeAvatar);
        pane.getChildren().add(new Avatar(1200 - 80, 40, DataBank.getCurrentUser().getAvatar()));
        createLabels(pane);
        createChangeButtons();
        stage.show();
    }

    private void createLabels(Pane pane) {
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setLayoutY(20);
        vBox.setLayoutX(20);
        createAttribute("Username",vBox);
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
        Text text = new Text();
        if (attribute.equals("Username")) {
            text.setText(DataBank.getCurrentUser().getUsername());
            currentUsername=text;
        }
        else if (attribute.equals("Nickname")) {
            text.setText(DataBank.getCurrentUser().getNickname());
            currentNickname=text;
        }
        else {
            text.setText(DataBank.getCurrentUser().getEmail());
            currentEmail=text;
        }
        text.setFill(new Color(0.54,0,0,1));
        hBox.getChildren().addAll(userNameText,text);
        vBox.getChildren().add(hBox);
    }

    private void createChangeButtons() {
        allChanges = new VBox();
        allChanges.setSpacing(10);
        pane.getChildren().add(allChanges);
        allChanges.setLayoutX(470);
        allChanges.setLayoutY(100);
        ChangeUsernameButton();
        ChangeNicknameButton();
        ChangeEmailButton();
        changePasswordButton();
        showScoreBoard();
    }

    private void showScoreBoard() {
        Button button = new Button("scoreboard");
        button.getStyleClass().add("submit-button");
        allChanges.getChildren().add(button);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new ScoreboardController().start(DataBank.getStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void changePasswordButton() {
        changePassword = new Button("change password");
        changePassword.getStyleClass().add("submit-button");
        changePassword.setOnAction(e -> {
            // Show the password change dialog
            showPasswordChangeDialog();
        });
        allChanges.getChildren().add(changePassword);
    }

    private void showPasswordChangeDialog() {
        int random_int = (int) Math.floor(Math.random() * (DataBank.captcha.size() - 1) + 1);
        javafx.scene.image.ImageView captcha = new ImageView(new Image(
                Objects.requireNonNull(LoginMenu.class.getResource("/IMAGE/Captcha/" + DataBank.captcha.get(random_int) +
                        ".png" )).toString(), 160 ,60, false, false));
        // Create the dialog
        Dialog<Pair<String,String>> dialog = new Dialog<>();
        dialog.setTitle("Change Password");
        dialog.setHeaderText("Enter your old password and new password");

        // Set the button types
        ButtonType changeButtonType = new ButtonType("Change", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(changeButtonType, ButtonType.CANCEL);

        // Create the username and password fields
        PasswordField oldPasswordField = new PasswordField();
        oldPasswordField.setPromptText("Old Password");
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");
        TextField captchaTextField=new TextField();
        captchaTextField.setPromptText("captcha");
        // Enable/disable the Change button depending on the input fields
        dialog.getDialogPane().lookupButton(changeButtonType).setDisable(true);
        Text text=new Text();
        oldPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            dialog.getDialogPane().lookupButton(changeButtonType).setDisable(newValue.trim().isEmpty());
        });
        newPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            dialog.getDialogPane().lookupButton(changeButtonType).setDisable(newValue.trim().isEmpty());
            if(!SignUpController.isPasswordStrong(newValue)) {
                text.setText("password is not strong");
                text.setFill(Color.RED);
            }
            else {
                text.setFill(Color.GREEN);
                text.setText("password is good");
            }
        });
        // Set the content of the dialog pane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, new Label("Old Password:"), oldPasswordField);
        gridPane.addRow(1, new Label("New Password:"), newPasswordField);
        gridPane.addRow(2,text);
        gridPane.addRow(3,captcha);
        gridPane.addRow(4,captchaTextField);
        dialog.getDialogPane().setPrefWidth(600);
        dialog.getDialogPane().setPrefHeight(400);
        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the old password field by default
        Platform.runLater(oldPasswordField::requestFocus);

        // Convert the result to a username-password pair when the Change button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == changeButtonType) {
                return new Pair<>(oldPasswordField.getText(), newPasswordField.getText());
            }
            return null;
        });

        // Show the dialog and handle the result
        dialog.showAndWait().ifPresent(result -> {
            String oldPassword = result.getKey();
            String newPassword = result.getValue();
            // Perform the password change logic using the provided old and new passwords
            if (DataBank.getCurrentUser().getCodedPassword().equals(User.hashString(oldPassword))&&
                    captchaTextField.getText().equals(String.valueOf(DataBank.captcha.get(random_int)))) {
                DataBank.getCurrentUser().setCodedPassword(User.hashString(newPassword));
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("successful");
                alert.showAndWait();
                // Password changed successfully
                // You can show a success message or perform any other actions here
            } else {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                if (!DataBank.getCurrentUser().getCodedPassword().equals(User.hashString(oldPassword))) alert.setContentText("invalid old password");
                else alert.setContentText("wrong captcha");
                alert.showAndWait();
            }
        });
    }

    private void ChangeEmailButton() {
        changeEmail=new Button("change email"); changeEmail.getStyleClass().add("submit-button");
        newEmail=new TextField();
        newEmail.setPromptText("new email");
        newEmail.setVisible(false);
        emailError=new Text();
        emailError.setVisible(false); emailError.setFont(Font.font(20));
        submitEmail=createEmailSubmit();
        allChanges.getChildren().addAll(changeEmail,newEmail,emailError,submitEmail);
        newEmail.textProperty().addListener((observable, oldText, newText)-> {
            setEmailErrorText(newText);
        });
        changeEmail.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                newEmail.setVisible(!newEmail.isVisible());
                emailError.setVisible(!emailError.isVisible());
                submitEmail.setVisible(!submitEmail.isVisible());
            }
        });
    }

    private void setEmailErrorText(String newText) {
        if(SignUpController.isEmailFormatOk(newText)) {
            emailError.setText("email acceptable");
            emailError.setFill(Color.DARKGREEN);
        }
        else if (SignUpController.isUsernameUsed(newText)){
            emailError.setText("email is used");
            emailError.setFill(Color.RED);
            emailError.getStyleClass().add("error-text");
        }
        else {
            emailError.setText("email is not valid");
            emailError.setFill(Color.RED);
        }
    }

    private Button createEmailSubmit() {
        Button button=new Button("submit");
        button.getStyleClass().add("submit-button");
        button.setVisible(false);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String current=newEmail.getText();
                if (SignUpController.isEmailFormatOk(current)&&(!SignUpController.isEmailUsed(current))){
                    showAlert(true);
                    DataBank.getCurrentUser().setEmail(current);
                    currentEmail.setText(current);
                }
                else {
                    showAlert(false);
                }
            }
        });
        return button;
    }

    private void ChangeNicknameButton() {
        changeNick=new Button("change nickname"); changeNick.getStyleClass().add("submit-button");
        newNick=new TextField();
        newNick.setPromptText("nickname");
        newNick.setVisible(false);
        submitNick=createNicknameSubmit();
        allChanges.getChildren().addAll(changeNick,newNick,submitNick);
        changeNick.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                newNick.setVisible(!newNick.isVisible());
                submitNick.setVisible(!submitNick.isVisible());
            }
        });
    }

    private Button createNicknameSubmit() {
        Button button=new Button("submit");
        button.getStyleClass().add("submit-button");
        button.setVisible(false);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String current=newNick.getText();
                showAlert(true);
                DataBank.getCurrentUser().setNickname(current);
                currentNickname.setText(current);
            }
        });
        return button;
    }

    private void ChangeUsernameButton() {
        changeUsername=new Button("change username"); changeUsername.getStyleClass().add("submit-button");
        newUsername=new TextField();
        newUsername.setPromptText("username");
        newUsername.setVisible(false);
        usernameError=new Text();
        usernameError.setVisible(false); usernameError.setFont(Font.font(20));
        submitUsername=createUsernameSubmit();
        allChanges.getChildren().addAll(changeUsername,newUsername,usernameError,submitUsername);
        newUsername.textProperty().addListener((observable, oldText, newText)-> {
            setUsernameErrorText(newText);
        });
        changeUsername.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                newUsername.setVisible(!newUsername.isVisible());
                usernameError.setVisible(!usernameError.isVisible());
                submitUsername.setVisible(!submitUsername.isVisible());
            }
        });
    }

    private void setUsernameErrorText(String newText) {
        if(SignUpController.isUsernameValid(newText)) {
            usernameError.setText("username acceptable");
            usernameError.setFill(Color.DARKGREEN);
        }
        else if (SignUpController.isUsernameUsed(newText)){
            usernameError.setText("username is used");
            usernameError.setFill(Color.RED);
            usernameError.getStyleClass().add("error-text");
        }
        else {
            usernameError.setText("username is not valid");
            usernameError.setFill(Color.RED);
        }
    }

    private Button createUsernameSubmit() {
        Button button=new Button("submit");
        button.getStyleClass().add("submit-button");
        button.setVisible(false);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String current=newUsername.getText();
                if (SignUpController.isUsernameValid(current)&&(!SignUpController.isUsernameUsed(current))){
                    showAlert(true);
                    DataBank.getCurrentUser().setUsername(current);
                    currentUsername.setText(current);
                }
                else {
                    showAlert(false);
                }
            }
        });
        return button;
    }

    private void showAlert(boolean b) {
        Alert alert;
        if (b){
            alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("successful");
            alert.showAndWait();
        }
        else {
            alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("pay attention to errors!!");
            alert.showAndWait();
        }
    }

}
