package View.graphic;

import Controller.DataBank;
import Controller.SignUpController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.User;

import java.util.Objects;


public class FriendshipMenu extends Application {
    private BorderPane pane;
    private TextField textField;
    private VBox vBox;
    private HBox searchedUser;
    private HBox searchHBox;
    private javafx.scene.image.ImageView background;

    @Override
    public void start(Stage stage) throws Exception {
        background = new ImageView(new Image(
                Objects.requireNonNull(ProfileMenu.class.getResource("/IMAGE/02.jpg")).toString(), 1200, 700, false, false));
        pane = new BorderPane();
        vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        searchedUser = new HBox();
        Label label = new Label("Create new friendship");
        textField = new TextField();
        searchedUser.setSpacing(10);
        searchedUser.setAlignment(Pos.CENTER);
        textField.setPromptText("search");
        textField.setMaxWidth(400);
        Button search = new Button("search");
        searchHBox = new HBox();
        searchHBox.setSpacing(10);
        searchHBox.setAlignment(Pos.CENTER);
        search.setOnMouseClicked(me -> searchUser());
        Button requests = new Button("Requests");
        Button friends = new Button("Friends");
        friends.setOnMouseClicked(me -> showFriends());
        requests.setOnMouseClicked(me -> showRequests());
        searchHBox.getChildren().addAll(requests, friends, search);
        vBox.getChildren().addAll(label, textField, searchHBox, searchedUser);
       // pane.getChildren().add(background);
        pane.setCenter(vBox);
        createBackButton();
        Scene scene = new Scene(pane);
        //scene.getStylesheets().add(String.valueOf(ProfileMenu.class.getResource("/CSS/style1.css")));
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(700);
        stage.show();
    }

    private void showRequests() {
        pane.getChildren().clear();
        HBox hBox = new HBox();
        hBox.setSpacing(30);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();
        vBox1.setSpacing(30);
        vBox1.setAlignment(Pos.CENTER);
        vBox2.setSpacing(30);
        vBox2.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(vBox1, vBox2);
        pane.setCenter(hBox);
        Label label = new Label("Sent Requests");
        vBox1.getChildren().add(label);
        Label label1 = new Label("Received Requests");
        vBox2.getChildren().add(label1);
        int counter1 = 0;
        int counter2 = 0;
        for (User user : DataBank.getCurrentUser().getSentRequests()) {
            showRequestsVBoxes(counter1,vBox1, user, "send");
            counter1++;
        }
        for (User user : DataBank.getCurrentUser().getReceivedRequests()) {
            showRequestsVBoxes(counter2,vBox2, user, "receive");
            counter2++;
        }
        Button button = new Button("back");
        button.setOnMouseClicked(me -> backToFriendshipMenu());
        pane.setBottom(button);
    }

    private void showRequestsVBoxes(int counter, VBox vBox1, User user, String type) {
        ImageView imageView = new ImageView(new Image(user.getAvatar()));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        Text text = new Text(user.getUsername());
        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(10);
        if(type.equals("send")) {
            Text text1 = new Text(getSituation(DataBank.getCurrentUser().getSentSituations().get(counter)));
            hBox1.getChildren().add(text1);
        } else{
            if(DataBank.getCurrentUser().getReceivedSituations().get(counter) == 0) {
                Button accept = new Button("accept");
                accept.setOnMouseClicked(me -> acceptRequest(user, counter));
                Button decline = new Button("decline");
                decline.setOnMouseClicked(me -> declineRequest(user, counter));
                hBox1.getChildren().addAll(accept, decline);
            }
        }
        hBox1.getChildren().addAll(imageView, text);
        vBox1.getChildren().add(hBox1);

    }

    private void declineRequest(User user, int counter) {
        DataBank.getCurrentUser().getReceivedSituations().set(counter,2);
        user.getSentSituations().set(counter,2);
    }

    private void acceptRequest(User user, int counter) {
        DataBank.getCurrentUser().getFriends().add(user);
        DataBank.getCurrentUser().getReceivedSituations().set(counter,1);
        user.getSentSituations().set(counter,1);
    }

    private String getSituation(int situation) {
        if(situation == 0) return " Waiting";
        else if(situation == 1) return " Accepted";
        else return " Declined";
    }

    private void showFriends() {
        pane.getChildren().clear();
        VBox friends = new VBox();
        friends.setAlignment(Pos.CENTER);
        friends.setSpacing(10);
        pane.setCenter(friends);
        for (User friend : DataBank.getCurrentUser().getFriends()) {
            ImageView imageView = new ImageView(new Image(friend.getAvatar()));
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            Text text = new Text(friend.getUsername());
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(10);
            hBox.getChildren().addAll(imageView, text);
            friends.getChildren().add(hBox);
        }
        if (friends.getChildren().size() == 0) {
            Text text = new Text("you have no friends");
            friends.getChildren().add(text);
        }
        Button button = new Button("back");
        button.setOnMouseClicked(me -> backToFriendshipMenu());
        friends.getChildren().add(button);
    }

    private void backToFriendshipMenu() {
        pane.getChildren().clear();
        pane.getChildren().add(vBox);
        createBackButton();
    }

    private void searchUser() {
        searchedUser.getChildren().clear();
        Text text = new Text("User not found");
        String username = textField.getText();
        User user;
        if ((user = DataBank.getUserByUsername(username)) != null) {
            ImageView imageView = new ImageView(new Image(user.getAvatar()));
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            text.setText(username);
            Button button = new Button("Request");
            Button shareMap = new Button("Share Map");
            TextField shareTextField = new TextField();
            shareTextField.setPromptText("Map number");
            button.setOnMouseClicked(me -> requestUser(username));
            searchedUser.getChildren().addAll(imageView, text, button,shareTextField,shareMap);
        } else {
            searchedUser.getChildren().add(text);
        }
        textField.setText("");
    }

    private void requestUser(String username) {
        System.out.println("salam");
        User receiver = DataBank.getUserByUsername(username);
        User sender = DataBank.getCurrentUser();
        receiver.getReceivedRequests().add(sender);
        receiver.getReceivedSituations().add(0);
        sender.getSentRequests().add(receiver);
        sender.getSentSituations().add(0);
        SignUpController.writeToJson(DataBank.getAllUsers());
    }

    private void createBackButton() {
        Button back = new Button("back");
        back.setOnMouseClicked(me -> {
            try {
                new ProfileMenu().start(DataBank.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        pane.setBottom(back);
    }
}
