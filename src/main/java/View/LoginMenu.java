package View;

import Controller.LoginController;
import Controller.ProfileMenuController;
import Model.Regex.LoginRegexes;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {

    private ProfileMenuController profileMenuController;

    public void run(Scanner scanner) throws IOException {
        String result;
        LoginController controller = new LoginController();
        while (true) {
            String input = scanner.nextLine().trim();
            Matcher matcher = LoginRegexes.FORGOT_PASSWORD.getMatcher(input);
            if (LoginRegexes.USER_LOGIN.getMatcher(input).matches()) {
                result = controller.login(input);
                System.out.println(result);
                if (result.equals("welcome to the profile menu!")) {
                    profileMenuController=new ProfileMenuController();
                    profileMenuController.run();
                    return;
                }
            } else if (matcher.matches()) System.out.println(controller.forgotPassword(scanner, matcher));
            else
                System.out.println("invalid command!");
        }
    }
}
