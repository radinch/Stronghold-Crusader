package View.graphic;

import Controller.DataBank;
import Model.ChatType;
import Model.UserNetwork;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Message;

import java.io.IOException;


public class ChatRoom extends Application {
    private VBox mode;
    private Pane pane;
    private HBox messageAndSend;
    private Scene scene;
    private Button send;
    private ChatType selectedType=ChatType.GLOBAL;
    private VBox messagesShow;
    private TextField message;
    {
        messagesShow=new VBox();
        messagesShow.setLayoutY(500); messagesShow.setLayoutX(200);
        UserNetwork userNetwork=DataBank.getUserNetworkByUsername(DataBank.getCurrentUser().getUsername());
        userNetwork.setChatRoom(this);
        mode=new VBox();
        messageAndSend=new HBox();
        message=new TextField();
        pane=new Pane();
    }
    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(false);
        DataBank.setStage(stage);
        stage.setWidth(1200); stage.setHeight(800);
        initialize();
        scene=new Scene(pane);
        stage.setScene(scene);
        pane.getChildren().add(messagesShow);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initialize() {
        message.setPromptText("message");
        message.setLayoutX(50);
        message.setPrefWidth(900);
        send = new Button("send");
        send.setOnMouseClicked(e -> {
            try {
                sendMessage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        messageAndSend.setSpacing(10);
        messageAndSend.getChildren().addAll(message, send);
        messageAndSend.setLayoutX(200);
        messageAndSend.setLayoutY(DataBank.getStage().getHeight() - 70);
        pane.getChildren().add(messageAndSend);
        modes();
    }

    private void modes() {
        Button privateChat=new Button("private");
        Button publicChat=new Button("public");
        Button room=new Button("room");
        privateChat.setOnMouseClicked(e->clickedPrivate());
        mode.getChildren().addAll(privateChat,publicChat,room);
        mode.setSpacing(6);
        mode.setLayoutY(300);
        pane.getChildren().add(mode);
    }

    private void clickedPrivate() {
    }

    private void sendMessage() throws IOException {
        if (selectedType==ChatType.GLOBAL) globalSelected();
    }

    private void globalSelected() throws IOException {
        String text=message.getText();
        Message message1=new Message(DataBank.getCurrentUser(),text);
        UserNetwork userNetwork=DataBank.getUserNetworkByUsername(DataBank.getCurrentUser().getUsername());
        userNetwork.getDataOutputStream().writeUTF("Message");
        userNetwork.getDataOutputStream().writeUTF(message1.toJson());
    }

    public VBox getMode() {
        return mode;
    }

    public void setMode(VBox mode) {
        this.mode = mode;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public HBox getMessageAndSend() {
        return messageAndSend;
    }

    public void setMessageAndSend(HBox messageAndSend) {
        this.messageAndSend = messageAndSend;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Button getSend() {
        return send;
    }

    public void setSend(Button send) {
        this.send = send;
    }

    public ChatType getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(ChatType selectedType) {
        this.selectedType = selectedType;
    }

    public VBox getMessagesShow() {
        return messagesShow;
    }

    public void setMessagesShow(VBox messagesShow) {
        this.messagesShow = messagesShow;
    }

    public TextField getMessage() {
        return message;
    }

    public void setMessage(TextField message) {
        this.message = message;
    }
}
