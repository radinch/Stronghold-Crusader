package Controller;

import Model.gameandbattle.Government;
import Model.signup_login_profile.User;
import View.menus.MainMenu;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenuController {
    private User host;
    private final ArrayList<User> players = new ArrayList<>();
    private final ArrayList<Government> governments = new ArrayList<>();
    private final MainMenu mainMenu = new MainMenu(this);

    public void run(Scanner scanner) {
        host=DataBank.getCurrentUser();
        mainMenu.run(scanner);
    }

    public boolean isUserNameValid(String username) {
        return DataBank.getUserByUsername(username) != null;
    }

    public void addPlayer(String username) {
        players.add(DataBank.getUserByUsername(username));
    }

    public ArrayList<Government> createGovernments() {
        for (User player : players) {
            governments.add(new Government(100,0,player,0,100000,0,8));
        }
        return governments;
    }
}
