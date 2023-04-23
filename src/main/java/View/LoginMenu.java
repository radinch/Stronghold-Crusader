package View;

import Controller.LoginController;
import Model.Regex.LoginRegexes;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {
    public void run(Scanner scanner) throws IOException {
        LoginController controller=new LoginController();
        while (true){
            String input= scanner.nextLine().trim();
            Matcher matcher=LoginRegexes.FORGOT_PASSWORD.getMatcher(input);
            if(LoginRegexes.USER_LOGIN.getMatcher(input).matches()) {
                String output=controller.login(input,scanner);
                System.out.println(output);
                if(output.equals("welcome to the game!")){
                    ProfileMenu profileMenu=new ProfileMenu();
                    profileMenu.run(scanner);
                }
            }
            else if(matcher.matches()) System.out.println(controller.forgotPassword(scanner,matcher));
        }
    }
}
