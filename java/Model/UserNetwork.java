package Model;

import Controller.DataBank;
import View.graphic.ChatRoom;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.Message;
import org.example.User;

import java.io.*;
import java.net.Socket;

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
            ////maifamim message dade
        }
    }

    private void handleMessage() {
        Message message=new Gson().fromJson(data,Message.class);
        Text text=new Text(message.getOwner().getUsername()+": "+message.getContent()); text.setFont(Font.font(15));
        chatRoom.getMessagesShow().getChildren().add(text);
    }

    private void handleUser() {
        User user1=new Gson().fromJson(data, User.class);
        System.out.println(user1.getUsername());
        if (!DataBank.getAllUsers().contains(user1)) DataBank.getAllUsers().add(user1);
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
