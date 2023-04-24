package controller;

import model.User;
import view.ProfileMenu;
import view.enums.Validations;

import java.util.regex.Matcher;

public class ProfileMenuController {
    private final ProfileMenu profileMenu;
    private User currentUser;
    public ProfileMenuController() {
        profileMenu=new ProfileMenu(this);
    }

    public void run() {
        profileMenu.run();
    }

    public Validations changeProfileAttribute(Matcher matcher) {
        String flag=matcher.group("flag");
        String characteristic= matcher.group("characteristic");
        if(flag == null || characteristic == null)
            return Validations.EMPTY_FIELD;
        return switch (flag) {
            case "u" -> (changeUsername(characteristic));
            case "n" -> (changeNickname(characteristic));
            case "e" -> (changeEmail(characteristic));
            default -> Validations.INVALID_FLAG;
        };
    }

    public Validations changeUsername(String newUsername) {
        if(newUsername.matches(".*\\W.*"))
           return Validations.INVALID_USERNAME;
        if(User.getUserByUsername(newUsername) != null)
            return Validations.DUPLICATE_USERNAME;
        if(newUsername.indexOf("\"") == 0)
            newUsername=newUsername.substring(1,newUsername.length()-1);
        //currentUser.setUsername(newUsername);
        System.out.println(newUsername);
        return Validations.USERNAME_CHANGE_SUCCESSFUL;
    }

    public String recommendUsername(String username) {
        return username + "_" + User.getUsers().size();
    }

    public Validations changeNickname(String newNickname) {
        //currentUser.setNickname(newNickname);
        return Validations.NICKNAME_CHANGE_SUCCESSFUL;
    }

    public Validations changeEmail(String newEmail) {
        return null;
    }
}
