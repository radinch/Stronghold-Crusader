package Controller;

import Model.Regex.SignUpRegexes;
import Model.signup_login_profile.User;
import Model.signup_login_profile.Captcha;
import Model.signup_login_profile.Slogan;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SignUpController {
    public String register(String input, Scanner scanner) {
        ArrayList<User> users = readFromJson();
        String slogan = "";
        DataBank.setAllUsers(users);
        Matcher nickMatcher = SignUpRegexes.NICKNAME.getMatcher(input);
        Matcher passMatcher = SignUpRegexes.PASSWORD.getMatcher(input);
        Matcher userMatcher = SignUpRegexes.USERNAME.getMatcher(input);
        Matcher emailMatcher = SignUpRegexes.EMAIL.getMatcher(input);
        Matcher sloganMatcher = SignUpRegexes.SLOGAN.getMatcher(input);
        if (!nickMatcher.find()) return "you entered no nickname!";
        if (!passMatcher.find()) return "you entered no password!";
        if (!userMatcher.find()) return "you entered no username!";
        if (!emailMatcher.find()) return "you entered no email!";
        if (sloganMatcher.find())
            slogan = withoutQuotation(sloganMatcher.group("slogan"));
        if (SignUpRegexes.IS_A_FIELD_EMPTY.getMatcher(input).find() || input.charAt(input.length() - 2) == '-')
            return "a field is empty";
        String username = withoutQuotation(userMatcher.group("username"));
        String password = withoutQuotation(passMatcher.group("password"));
        String nickname = withoutQuotation(nickMatcher.group("nickname"));
        String email = withoutQuotation(emailMatcher.group("email"));
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
            Matcher confirmMatcher = SignUpRegexes.PASSWORD_CONFIRMATION.getMatcher(input);
            if (confirmMatcher.find())
                repeated = withoutQuotation(confirmMatcher.group("repeated"));
            if (!repeated.equals(password)) return "please make sure that password matches repeated password";
        } else {
            password = whenPasswordIsRandom(scanner, password);
            if (password.equals("false")) return "so please try registering again!";
        }
        if (isEmailUsed(email)) return "this email is already used";
        if (!isEmailFormatOk(email)) return "email format is not corroct";
        if (slogan.equals("random")) {
            slogan = randomSlogan();
            System.out.println("your random slogan is: " + slogan);
        }
        User newUser = new User(username, password, nickname, email);
        if (!slogan.equals("")) newUser.setSlogan(slogan);
        users.add(newUser);
        System.out.println(securityQuestion(scanner, newUser));
        System.out.println(checkingCaptcha(scanner));
        writeToJson(users);
        return "your register was successful";
    }

    public String whenPasswordIsRandom(Scanner scanner, String password) {
        String newPass = generateRandomString(9);
        password = newPass;
        System.out.println("Your random password is: " + password + "\n" + "Please re-enter your password here:");
        if (scanner.nextLine().equals(password)) {
            System.out.println("nice job! remember your password for next time!");
            return password;
        }
        System.out.println("you did not re-enter your password right!");
        return "false";
    }

    public static ArrayList<User> readFromJson() {
        File file = new File("users.json");
        ObjectMapper objectMapper = new ObjectMapper();
        if (file.length() != 0) {
            try {
                return objectMapper.readValue(file, new TypeReference<ArrayList<User>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<User>();
            }
        }
        return new ArrayList<>();
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

    public static boolean isPasswordStrong(String password) {
        //System.out.println(password);
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
            System.out.println("Pick your security question: 1. What is my fathers name? 2. What\n" + "was my first pets name? 3. What is my mothers last name?");
            String input = scanner.nextLine().trim();
            Matcher matcher = SignUpRegexes.PICK_QUESTION.getMatcher(input);
            if (matcher.matches()) {
                Matcher confirmMatcher = SignUpRegexes.QUESTION_CONFIRMATION.getMatcher(input);
                Matcher answerMatcher = SignUpRegexes.QUESTION_ANSWER.getMatcher(input);
                Matcher numberMatcher = SignUpRegexes.QUESTION_NUMBER.getMatcher(input);
                if (confirmMatcher.find() && answerMatcher.find() && numberMatcher.find()) {
                    if (!withoutQuotation(answerMatcher.group("answer")).equals(withoutQuotation(confirmMatcher.group("confirm")))) {
                        System.out.println("you did not confirm your answer correctly");
                    } else {
                        user.setSecurityQuestion(Integer.parseInt(numberMatcher.group("number")));
                        user.setAnswer(withoutQuotation(answerMatcher.group("answer")));
                        return "successful";
                    }
                } else System.out.println("invalid command!");
            } else System.out.println("invalid command");
        }
    }

    public static String checkingCaptcha(Scanner scanner) {
        while (true) {
            Captcha.generateCaptcha();
            String input = scanner.nextLine().trim();
            if (Captcha.checkCaptcha(input)) return "correct!";
            else System.out.println("incorrect! try again!");
        }
    }

    public static void writeToJson(ArrayList<User> users) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("users.json"), users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String withoutQuotation(String string) {
        if (string.charAt(0) == '"') return string.substring(1, string.length() - 1);
        return string;
    }
}

