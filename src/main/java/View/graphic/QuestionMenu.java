package View.graphic;

import Controller.DataBank;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class QuestionMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = FXMLLoader.load(
                new URL(Objects.requireNonNull(QuestionMenu.class.getResource("/FXML/questionMenu.fxml")).toExternalForm()));
        Scene scene = new Scene(borderPane);
        VBox vBox = new VBox();
        vBox.setSpacing(45);
        vBox.setAlignment(Pos.CENTER);
        Text text = new Text();
        text.setFill(new Color(1,1,1,1));
        text.setFont(new Font("Calibri",30));
        text.setText("Pick a question and answer");
        Text questions = new Text();
        questions.setFill(new Color(1,1,1,1));
        questions.setFont(new Font("Calibri",20));
        questions.setText("1. What is my fathers name? 2. What\nwas my first pets name? 3. What is my mothers last name?");
        TextField textField = new TextField();
        textField.setMaxWidth(300);
        textField.getStyleClass().add("text-input");
        textField.setPromptText("Question number");
        TextField textField1 = new TextField();
        textField1.setMaxWidth(300);
        textField1.getStyleClass().add("text-input");
        textField1.setPromptText("Answer");
        Button button = new Button();
        button.setText("Submit");
        button.getStyleClass().add("submit-button");
        button.setOnMousePressed(mouseEvent -> {
            try {
                setAnswer(textField,textField1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        vBox.getChildren().addAll(text,questions,textField,textField1,button);
        borderPane.setCenter(vBox);
        stage.setScene(scene);
        stage.show();
    }

    private void setAnswer(TextField textField, TextField textField1) throws Exception {
        DataBank.userUnderConstruction.setSecurityQuestion(Integer.parseInt(textField.getText()));
        DataBank.userUnderConstruction.setAnswer(textField1.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Pick question");
        alert.setContentText("question and answer picked successfully");
        alert.showAndWait();
        new LoginMenu().start(DataBank.getStage());
    }
}
