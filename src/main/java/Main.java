import Controller.DataBank;
import Controller.ProfileMenuController;
import Controller.SignUpController;
import Model.signup_login_profile.User;
import View.menus.LoginMenu;
import View.menus.SignUpMenu;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // TODO: 4/12/2023 1.unit 2.final check 3.package 4.errors method
        DataBank.setAllUsers(SignUpController.readFromJson());
        ObjectMapper mapper = new ObjectMapper();
        Scanner scanner = new Scanner(System.in);
        File loggedInUser = new File("loggedInUser.json");
        boolean invalidCommandChecker = false;
        if (loggedInUser.length() == 0) {
            label:
            while (true) {
                if (!invalidCommandChecker)
                    System.out.println("welcome to the start menu of the game!\nwould you like to sign up or login?\nalso if you want to close just type close!\ntype sign up or login:");
                invalidCommandChecker = false;
                String input = scanner.nextLine();
                switch (input) {
                    case "login":
                        LoginMenu loginMenu = new LoginMenu();
                        loginMenu.run(scanner);
                        break;
                    case "sign up":
                        SignUpMenu signUpMenu = new SignUpMenu();
                        signUpMenu.run(scanner);
                        break;
                    case "close":
                        break label;
                    default:
                        invalidCommandChecker = true;
                        System.out.println("invalid command");
                        break;
                }
            }
        } else {
            DataBank.setCurrentUser(mapper.readValue(loggedInUser, User.class));
            ProfileMenuController controller = new ProfileMenuController();
            controller.run(scanner);
        }
    }
    //salasm
}
