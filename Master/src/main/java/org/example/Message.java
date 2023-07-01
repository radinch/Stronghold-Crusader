package org.example;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    User owner;
    String content;
    private ArrayList<User> receivers;
    public Message(){

    }

    public Message(User owner, String content) {
        this.owner = owner;
        this.content = content;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String toJson(){
        return new Gson().toJson(this);
    }

    public ArrayList<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(ArrayList<User> receivers) {
        this.receivers = receivers;
    }
}
