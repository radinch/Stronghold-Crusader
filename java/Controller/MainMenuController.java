package Controller;

import Model.gameandbattle.Government;
import org.example.User;
import View.menus.MainMenu;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenuController {
    private User host;
    private  ArrayList<User> players = new ArrayList<>();
    private  ArrayList<Government> governments = new ArrayList<>();
    private MainMenu mainMenu = new MainMenu(this);

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

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public ArrayList<User> getPlayers() {
        return players;
    }

    public ArrayList<Government> getGovernments() {
        return governments;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public void setPlayers(ArrayList<User> players) {
        this.players = players;
    }

    public void setGovernments(ArrayList<Government> governments) {
        this.governments = governments;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }
}
