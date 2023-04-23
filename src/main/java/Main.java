import Controller.DataBank;
import Controller.SignUpController;
import Model.User;
import View.LoginMenu;
import View.SignUpMenu;
import View.SignUpMenu;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        DataBank.setAllUsers(SignUpController.readFromJson());
        ObjectMapper mapper=new ObjectMapper();
        Scanner scanner=new Scanner(System.in);
        File loggedInUser=new File("loggedInUser.json");
        if(loggedInUser.length()==0) {
            while (true){
                System.out.println("hello and welcome to the game!\nwould you like to sign up or login?\nalso if you want to close just type close!\ntype sign up or login:");
                String input=scanner.nextLine();
                if(input.equals("login")){
                    System.out.println("welcome to login menu");
                    LoginMenu loginMenu=new LoginMenu();
                    loginMenu.run(scanner);
                }
                else if(input.equals("sign up")) {
                    SignUpMenu signUpMenu=new SignUpMenu();
                    System.out.println("welcome to sign up menu!");
                    signUpMenu.run(scanner);
                }
                else if(input.equals("close")) break;
                else System.out.println("invalid command");
            }
        }
        else{
            DataBank.setCurrentUser(mapper.readValue(loggedInUser, User.class));
        }
    }
}
