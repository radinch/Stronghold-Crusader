package Controller;

import Model.Regex.LoginRegexes;
import Model.User;
import View.Captcha;
import View.Slogan;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;

import javax.print.DocFlavor;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.LinkPermission;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController {
    public String register(String input, Scanner scanner) {
        ArrayList<User> users = readFromJson();
        String slogan = "";
        DataBank.setAllUsers(users);
        if (!LoginRegexes.NICKNAME.getMatcher(input).find()) return "you entered no nickname!";
        if (!LoginRegexes.PASSWORD.getMatcher(input).find()) return "you entered no password!";
        if (!LoginRegexes.USERNAME.getMatcher(input).find()) return "you entered no username!";
        if (!LoginRegexes.EMAIL.getMatcher(input).find()) return "you entered no email!";
        if (LoginRegexes.SLOGAN.getMatcher(input).find())
            slogan = LoginRegexes.SLOGAN.getMatcher(input).group("slogan");
        if (LoginRegexes.IS_A_FIELD_EMPTY.getMatcher(input).find() || input.charAt(input.length() - 2) == '-')
            return "a field is empty";
        String username = LoginRegexes.USERNAME.getMatcher(input).group("username");
        String password = LoginRegexes.PASSWORD.getMatcher(input).group("password");
        String nickname = LoginRegexes.NICKNAME.getMatcher(input).group("nickname");
        String email = LoginRegexes.EMAIL.getMatcher(input).group("email");
        String repeated = null;
        String randomPassword = generateRandomString(10);
        if (!isUsernameValid(username)) return "username format invalid";
        if (isUsernameUsed(username)) {
            username = username + "12";
            System.out.println("this username is already used.\ndo you want your username to be " + username + " ?\ntype yes or no for conformation!");
            if (scanner.nextLine().equals("no")) return "ok,bye!";
        }
        if (!password.equals("random")) {
            if (!isPasswordStrong(password)) return "password is weak";
            if (LoginRegexes.PASSWORD_CONFIRMATION.getMatcher(input).find())
                repeated = LoginRegexes.PASSWORD_CONFIRMATION.getMatcher(input).group("repeated");
            if (!repeated.equals(password)) return "please make sure that password matches repeated password";
        }
        if (!whenPasswordIsRandom(scanner, password)) return "so please re-register again";
        if (isEmailUsed(email)) return "this email is already used";
        if (!isEmailFormatOk(email)) return "email format is not corroct";
        if (slogan.equals("random")) slogan = randomSlogan();
        User newUser = new User(username, password, nickname, email);
        if (!slogan.equals("")) newUser.setSlogan(slogan);
        users.add(newUser);
        System.out.println(securityQuestion(scanner, newUser));
        System.out.println(checkingCaptcha(scanner));
        return "your register was successful";
    }

    private boolean whenPasswordIsRandom(Scanner scanner, String password) {
        System.out.println("Your random password is: " + password + "\n" + "Please re-enter your password here:");
        if (scanner.nextLine().equals(password)) {
            System.out.println("nice job! remember your password for next time!");
            return true;
        }
        System.out.println("you did not re-enter your password right!");
        return false;
    }

    private ArrayList<User> readFromJson() {
        File file = new File("users.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(file, new TypeReference<ArrayList<User>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<User>();
        }
    }

    private boolean isUsernameValid(String username) {
        String regex = "\\w+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    private boolean isUsernameUsed(String username) {
        for (User user : DataBank.getAllUsers()) {
            if (user.getUsername().equals(username)) return true;
        }
        return false;
    }

    private boolean isPasswordStrong(String password) {
        String regex1 = "\\d";
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(password);
        if (!matcher1.find()) {
            System.out.println("make sure you have digit in there!");
            return false;
        }
        String regex2 = "[a-z]";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(password);
        if (!matcher2.find()) {
            System.out.println("make sure you have a lowercase letter in there!");
            return false;
        }
        String regex3 = "[A-Z]";
        Pattern pattern3 = Pattern.compile(regex3);
        Matcher matcher3 = pattern3.matcher(password);
        if (!matcher3.find()) {
            System.out.println("make sure you have a uppercase letter in there!");
            return false;
        }
        String regex4 = "[^a-zA-Z0-9]";
        Matcher matcher4 = Pattern.compile(regex4).matcher(password);
        if (!matcher4.find()) {
            System.out.println("make sure you have a non-digit-letter character in there!");
            return false;
        }
        if (password.length() < 6) {
            System.out.println("your password should be at least 6 character long!");
            return false;
        }
        return true;
    }

    public static String generateRandomString(int length) {
        // Define the character sets
        String digits = "0123456789";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String nonAlphanumericChars = "!@#$%^&*()_+-=[]{}|;':\",./<>?";

        // Make sure each character set has at least one character
        StringBuilder randomChars = new StringBuilder();
        SecureRandom random = new SecureRandom();
        randomChars.append(digits.charAt(random.nextInt(digits.length())));
        randomChars.append(lowercaseLetters.charAt(random.nextInt(lowercaseLetters.length())));
        randomChars.append(uppercaseLetters.charAt(random.nextInt(uppercaseLetters.length())));
        randomChars.append(nonAlphanumericChars.charAt(random.nextInt(nonAlphanumericChars.length())));

        // Generate the remaining random characters
        int remainingLength = length - randomChars.length();
        for (int i = 0; i < remainingLength; i++) {
            String characterSet = digits + lowercaseLetters + uppercaseLetters + nonAlphanumericChars;
            randomChars.append(characterSet.charAt(random.nextInt(characterSet.length())));
        }

        return randomChars.toString();
    }

    private boolean isEmailUsed(String email) {
        for (User user : DataBank.getAllUsers()) {
            if (user.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }

    private boolean isEmailFormatOk(String email) {
        String regex = "[a-zA-Z0-9_.]+@[a-zA-Z0-9_.]+\\.[a-zA-Z0-9_.]+";
        if (Pattern.compile(regex).matcher(email).matches()) return true;
        return false;
    }

    private String randomSlogan() {
        Random random = new Random();
        int rand = random.nextInt(4);
        return Slogan.randomSlogan(rand);
    }

    private String securityQuestion(Scanner scanner, User user) {
        while (true) {
            String input = scanner.nextLine().trim();
            System.out.println("Pick your security question: 1. What is my father’s name? 2. What\n" + "was my first pet’s name? 3. What is my mother’s last name?");
            if (LoginRegexes.PICK_QUESTION.getMatcher(input).matches()) {
                if (!LoginRegexes.PICK_QUESTION.getMatcher(input).group("answer").equals(LoginRegexes.PICK_QUESTION.getMatcher(input).group("confirm"))) {
                    System.out.println("you did not confirm your answer correctly");
                } else {
                    user.setSecurityQuestion(Integer.parseInt(LoginRegexes.PICK_QUESTION.getMatcher(input).group("number")));
                    user.setAnswer(LoginRegexes.PICK_QUESTION.getMatcher(input).group("answer"));
                    return "successful";
                }
            } else System.out.println("invalid command");
        }
    }

    private String checkingCaptcha(Scanner scanner) {
        while (true){
            Captcha.generateCaptcha();
            String input=scanner.nextLine().trim();
            if(Captcha.checkCaptcha(input))  return"correct!";
            else System.out.println("incorrect");
        }
    }
    private void writeToJson(ArrayList<User> users){
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            objectMapper.writeValue(new File("users.json"),users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

