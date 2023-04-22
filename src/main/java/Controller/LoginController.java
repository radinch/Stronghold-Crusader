package Controller;

import Model.Regex.LoginRegexes;
import Model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController {
    public String register(String input, Scanner scanner){
        ArrayList<User> users=readFromJson();
        DataBank.setAllUsers(users);
        if(!LoginRegexes.NICKNAME.getMatcher(input).find()) return "you entered no nickname!";
        if(!LoginRegexes.PASSWORD.getMatcher(input).find()) return "you entered no password!";
        if(!LoginRegexes.USERNAME.getMatcher(input).find()) return "you entered no username!";
        if(!LoginRegexes.EMAIL.getMatcher(input).find()) return "you entered no email!";
        if(LoginRegexes.IS_A_FIELD_EMPTY.getMatcher(input).find()) return "a field is empty";
        String username=LoginRegexes.USERNAME.getMatcher(input).group("username"); String password=LoginRegexes.PASSWORD.getMatcher(input).group("password");
        String nickname=LoginRegexes.NICKNAME.getMatcher(input).group("nickname");String email=LoginRegexes.EMAIL.getMatcher(input).group("email");
        String slogan=LoginRegexes.SLOGAN.getMatcher(input).group("slogan");
        if(!isUsernameValid(username)) return "username format invalid";
        if(isUsernameUsed(username)) {
            username=username+"12";
            System.out.println("this username is already used.\ndo you want your username to be "+username+" ?\ntype yes or no for conformation!");
            if(scanner.nextLine().equals("no")) return "ok,bye!";
        }
        
    }
    private ArrayList<User> readFromJson(){
        File file=new File("users.json");
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            return objectMapper.readValue(file,new TypeReference<ArrayList<User>>(){});
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<User>();
        }
    }
    private boolean isUsernameValid(String username){
        String regex="\\w+";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(username);
        return matcher.matches();
    }
    private boolean isUsernameUsed(String username){
        for(User user:DataBank.getAllUsers()){
            if(user.getUsername().equals(username)) return true;
        }
        return false;
    }
}
