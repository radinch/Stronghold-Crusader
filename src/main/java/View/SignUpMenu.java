package View;

import Controller.SignUpController;
import Controller.SignUpController;
import Model.Regex.SignUpRegexes;

import java.util.Scanner;

public class SignUpMenu {
    public void run(Scanner scanner){
        String input;
        SignUpController signUpController=new SignUpController();
        while (true){
            input= scanner.nextLine().trim();
            if(SignUpRegexes.REGISTER.getMatcher(input).matches()) System.out.println(signUpController.register(input,scanner));
            else if(input.equals("exit")) break;
            else System.out.println("invalid command");
        }
    }
}
