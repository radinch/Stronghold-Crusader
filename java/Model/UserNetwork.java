package Model;

import Controller.DataBank;
import View.graphic.ChatRoom;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;
import org.example.Edit;
import org.example.Message;
import org.example.User;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserNetwork extends Thread{
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private User user;
    private Socket socket;
    private String type;
    private String data;
    private ChatRoom chatRoom;

    public UserNetwork(User user, Socket socket) throws IOException {
        dataInputStream=new DataInputStream(socket.getInputStream());
        dataOutputStream=new DataOutputStream(socket.getOutputStream());
        this.user = user;
        this.socket=socket;
        dataOutputStream.writeUTF("User");
        dataOutputStream.writeUTF(user.toJson());
        DataBank.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                try {
                    dataOutputStream.writeUTF("Offline");
                    dataOutputStream.writeUTF(user.toJson());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    dataOutputStream.writeUTF(user.toJson());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void run() {
        Platform.setImplicitExit(false);
        while (true){
            try {
                type = dataInputStream.readUTF();
                data = dataInputStream.readUTF();
            }catch (Exception e){

            }
            if (type.equals("User")){
                handleUser();
            }
            if (type.equals("Message")){
                handleMessage();
            }
            if (type.equals("Edit")){
                handleEdit();
            }
            if(type.equals("Offline")) {
                handleOffline();
            }
            ////maifamim message dade
        }
    }

    private void handleOffline() {
        User user1 = new Gson().fromJson(data,User.class);
        System.out.println(user1.getUsername() + " is offline now");
        for (int i = DataBank.onlineUsers.size() - 1; i >= 0 ; i--) {
            if(DataBank.onlineUsers.get(i).getUsername().equals(user1.getUsername()))
                DataBank.onlineUsers.remove(DataBank.onlineUsers.get(i));
        }
    }

    private void handleEdit() {
        Edit edit=new Gson().fromJson(data,Edit.class);
        Text text= (Text) DataBank.hBoxes.get(edit.getI()).getChildren().get(0);
        Platform.runLater(()->text.setText(edit.getText()));
    }

    private void handleMessage() {
        HBox hBox=new HBox();
        Message message=new Gson().fromJson(data,Message.class);
        ImageView imageView=new ImageView(new Image(message.getOwner().getAvatar()));
        Text text=new Text(message.getOwner().getUsername()+": "+message.getContent()); text.setFont(Font.font(15));
        Button button=new Button("done"); TextField textField=new TextField("edit");
        button.setVisible(false); textField.setVisible(false);
        Button delete=new Button("delete");
        imageView.setFitWidth(20); imageView.setFitHeight(20);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Text text1=new Text(dtf.format(now)); text1.setFont(Font.font(20));
        hBox.getChildren().addAll(text,textField,button,delete,imageView,text1);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                text.setText(message.getOwner().getUsername() + ": " + textField.getText());
                textField.setVisible(false);
                button.setVisible(false);
                int i = getTheIndexOfThisHBox(hBox);
                Edit edit = new Edit(i, text.getText());
                try {
                    dataOutputStream.writeUTF("Edit");
                    dataOutputStream.writeUTF(edit.toJson());
                } catch (Exception e){

                }
            }
        });
        delete.setOnMouseClicked(e->deleteClicked(text,hBox));
        DataBank.hBoxes.add(hBox);
        text.setOnMouseClicked(e->clickedText(button,textField));
        if (chatRoom!=null) Platform.runLater(()->chatRoom.getMessagesShow().getChildren().add(hBox));
    }

    private void deleteClicked(Text text,HBox hBox) {
        text.setText("deleted message");
        int i = getTheIndexOfThisHBox(hBox);
        Edit edit = new Edit(i, text.getText());
        try {
            dataOutputStream.writeUTF("Edit");
            dataOutputStream.writeUTF(edit.toJson());
        } catch (Exception e){
            
        }
    }

    private int getTheIndexOfThisHBox(HBox hBox) {
        for (int i=0;i<DataBank.hBoxes.size();i++){
            if(DataBank.hBoxes.get(i)==hBox) return i;
        }
        return -1;
    }

    private void clickedText(Button button, TextField textField) {
        button.setVisible(true); textField.setVisible(true);
    }


    private void handleUser() {
        User user1=new Gson().fromJson(data, User.class);
        System.out.println(user1.getUsername());
        DataBank.onlineUsers.add(user1);
        DataBank.getAllUsers().add(user1);

        if (DataBank.getUserByUsername(user1.getUsername()) == null) {
            DataBank.getAllUsers().add(user1);
        }
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public void setDataInputStream(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
