package View;

import Controller.LoginController;
import Model.Regex.LoginRegexes;

import java.util.Scanner;

public class LoginMenu {
    public void run(Scanner scanner){
        String input;
        LoginController loginController=new LoginController();
        while (true){
            input= scanner.nextLine();
            if(LoginRegexes.REGISTER.getMatcher(input).matches()) System.out.println(loginController.register(input,scanner));
        }
    }
}
