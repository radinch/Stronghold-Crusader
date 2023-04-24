package Controller;

import Model.User;

import java.util.ArrayList;

public class DataBank {
    private static ArrayList<User> allUsers = new ArrayList<>();
    private static User currentUser;

    ///////////////////////////////////// getters and setters

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ArrayList<User> allUsers) {
        DataBank.allUsers = allUsers;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        DataBank.currentUser = currentUser;
    }

    ///////////////////////////////////////////////////////
    public static User getUserByUsername(String username) {
        for (User allUser : allUsers) {
            if (allUser.getUsername().equals(username)) return allUser;
        }
        return null;
    }
}
