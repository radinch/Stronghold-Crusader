package org.example.Controller;

import org.example.Connection;
import org.example.User;

import java.io.CharArrayReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DataBank {
    public static ArrayList<User> allUsers=new ArrayList<>();
    public static ArrayList<Connection> connections=new ArrayList<>();

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ArrayList<User> allUsers) {
        DataBank.allUsers = allUsers;
    }

    public static ArrayList<Connection> getConnections() {
        return connections;
    }

    public static void setConnections(ArrayList<Connection> connections) {
        DataBank.connections = connections;
    }
}
