package Controller;

import Model.Regex.LoginRegexes;
import Model.SecurityQuestion;
import Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

public class LoginController {
    public String login(String input,Scanner scanner) throws IOException {
        boolean wantToStayLoggedIn;
        String username = null;
        String password = null;
        Matcher userMatcher= LoginRegexes.USERNAME.getMatcher(input);
        Matcher passMatcher=LoginRegexes.PASSWORD.getMatcher(input);
        Matcher loggedin=LoginRegexes.STAY_LOGGED_IN.getMatcher(input);
        if(userMatcher.find()) username=userMatcher.group("username");
        if(passMatcher.find()) password=passMatcher.group("password");
        wantToStayLoggedIn=loggedin.find();
        if(DataBank.getUserByUsername(username)==null) return "this username does not exist!";
        if(!DataBank.getUserByUsername(username).equals(User.hashString(password))) {
            DataBank.getUserByUsername(username).setFailedAttemptsToLogin(DataBank.getUserByUsername(username).getFailedAttemptsToLogin()+1);
            System.out.println("password was invalid now you must wait!");
            try {
                Thread.sleep(DataBank.getUserByUsername(username).calculateTimeToWait()* 1000L);
            } catch (InterruptedException e) {
                System.out.println("gotta wait "+DataBank.getUserByUsername(username).calculateTimeToWait()+" seconds");
            }
            return "incorrect password";
        }
        if(wantToStayLoggedIn)  writeToJson(DataBank.getUserByUsername(username));
        else{
            FileWriter fileWriter=new FileWriter(new File("loggedInUser.json"));
            fileWriter.close();
        }
        DataBank.setCurrentUser(DataBank.getUserByUsername(username));
        DataBank.getUserByUsername(username).setFailedAttemptsToLogin(0);
        SignUpController.writeToJson(DataBank.getAllUsers());
        System.out.println(SignUpController.checkingCaptcha(scanner));
        return "welcome to the game!";
    }
    public String forgotPassword(Scanner scanner,Matcher matcher){
        DataBank.setAllUsers(SignUpController.readFromJson());
        String username=matcher.group("username");
        User user=DataBank.getUserByUsername(username);
        System.out.println("answer this:\n"+SecurityQuestion.getQuestionByNUmber(user.getSecurityQuestion()));
        if(scanner.nextLine().trim().equals(user.getAnswer())) {
            System.out.println("alright! type in another password:");
            user.setCodedPassword(User.hashString(scanner.nextLine()));
            SignUpController.writeToJson(DataBank.getAllUsers());
            return "password changed successfully!";
        }
        return "wrong answer!";
    }
    private void writeToJson(User user) throws IOException {
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(new File("loggedInUser.json"),User.class);
    }
}
