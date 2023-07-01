package View.graphic;

import Controller.DataBank;
import Controller.SignUpController;
import Model.signup_login_profile.Avatar;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class AvatarMenu extends Application {
    @FXML
    private AnchorPane avatarPane;
    @Override
    public void start(Stage stage) throws Exception {
        avatarPane = FXMLLoader.load(new URL(AvatarMenu.class.getResource("/FXML/Avatars.fxml").toExternalForm()));
        javafx.scene.image.ImageView background = new ImageView(new Image(
                Objects.requireNonNull(ProfileMenu.class.getResource("/IMAGE/15.jpg")).toString(), 1200 ,700, false, false));
        TextField number = new TextField();
        number.setPromptText("choose a number between 1 and 10");
        FileChooser fileChooser;
        fileChooser = new FileChooser();
        Button chooseNumber = new Button("Choose");
        chooseNumber.getStyleClass().add("submit-button");
//        chooseNumber.setLayoutX(600-125);
//        chooseNumber.setLayoutY(300);
        chooseNumber.setOnMouseClicked(mouseEvent -> {
            try {
                chooseAvatar(number);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Button chooseFile = new Button("Choose File");
        chooseFile.getStyleClass().add("submit-button");
//        chooseFile.setLayoutX(600-125);
//        chooseFile.setLayoutY(400);
        chooseFile.setOnMouseClicked(mouseEvent -> {
            try {
                chooseFile(fileChooser);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg")
                ,new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );
        avatarPane.getChildren().add(background);
        for (int i = 1; i <= 9; i++) {
            Avatar avatar = new Avatar(10+120*i,200,Avatar.class.getResource("/IMAGE/Avatars/"+i+".png").toExternalForm());
            avatarPane.getChildren().add(avatar);
        }
        VBox vBox = new VBox(number,chooseNumber,chooseFile);
        vBox.setSpacing(30);
        vBox.setAlignment(Pos.CENTER);
        vBox.setLayoutX(600-125);
        vBox.setLayoutY(300);
        avatarPane.getChildren().add(vBox);
        Scene scene = new Scene(avatarPane);
        stage.setScene(scene);
        stage.show();
    }
    public void initialize() {
        avatarPane.setOnDragOver(event -> {
            if (event.getGestureSource() != avatarPane && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        avatarPane.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasFiles()) {
                List<File> files = dragboard.getFiles();
                // Handle the dropped files here
                try {
                    handleDroppedFiles(files);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void handleDroppedFiles(List<File> files) throws Exception {
        for (File file : files) {
            // Check if the dropped file is an image file
            if (isImageFile(file)) {
                // Load and display the image
                DataBank.getCurrentUser().setAvatar(file.getPath());
                SignUpController.writeToJson(DataBank.getAllUsers());
                new ProfileMenu().start(DataBank.getStage());
            }
        }
    }

    private boolean isImageFile(File file) {
        // Implement your own logic to check if the file is an image file
        // For example, you can use file extension or MIME type
        return file.getName().toLowerCase().endsWith(".png") ||
                file.getName().toLowerCase().endsWith(".jpg") ||
                file.getName().toLowerCase().endsWith(".jpeg");
    }

    public void chooseAvatar(TextField number) throws Exception {
        DataBank.getCurrentUser().setAvatar(Avatar.class.getResource("/IMAGE/Avatars/"+number.getText()+".png").toExternalForm());
        SignUpController.writeToJson(DataBank.getAllUsers());
        new ProfileMenu().start(DataBank.getStage());
    }

    public void chooseFile(FileChooser fileChooser) throws Exception {
        File selectedFile = fileChooser.showOpenDialog(DataBank.getStage());
        DataBank.getCurrentUser().setAvatar(selectedFile.getPath());
        SignUpController.writeToJson(DataBank.getAllUsers());
        new ProfileMenu().start(DataBank.getStage());
    }
}
