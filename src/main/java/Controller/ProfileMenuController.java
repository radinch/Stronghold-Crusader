package Controller;

import Model.signup_login_profile.User;
import View.menus.ProfileMenu;
import Model.signup_login_profile.Slogan;
import View.enums.Validations;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProfileMenuController {
    private final ProfileMenu profileMenu;
    private final SignUpController signUpController;
    private User currentUser;

    public ProfileMenuController() {
        profileMenu = new ProfileMenu(this);
        signUpController = new SignUpController();
        currentUser = DataBank.getCurrentUser();
    }

    public void run(Scanner scanner) {
        profileMenu.run(scanner);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Validations changeProfileAttribute(Matcher matcher) {
        String flag = matcher.group("flag");
        String characteristic = matcher.group("characteristic");
        if (flag == null || characteristic == null)
            return Validations.EMPTY_FIELD;
        return switch (flag) {
            case "u" -> (changeUsername(characteristic));
            case "n" -> (changeNickname(characteristic));
            case "e" -> (changeEmail(characteristic));
            default -> Validations.INVALID_FLAG;
        };
    }

    public Validations changeProfileSlogan(Matcher matcher) {
        String flag = matcher.group("flag");
        String newSlogan = matcher.group("slogan");
        if (flag == null || newSlogan == null)
            return Validations.EMPTY_FIELD;
        if (!flag.equals("s"))
            return Validations.INVALID_FLAG;
        if (newSlogan.equals("random")) {
            newSlogan = randomSlogan();
            currentUser.setSlogan(newSlogan);
            return Validations.SLOGAN_CHANGED_TO_RANDOM;
        }
        currentUser.setSlogan(withoutQuotation(newSlogan));
        SignUpController.writeToJson(DataBank.getAllUsers());
        return Validations.SLOGAN_CHANGE_SUCCESSFUL;
    }

    public void removeSlogan() {
        DataBank.setAllUsers(SignUpController.readFromJson());
        currentUser.setSlogan(null);
        SignUpController.writeToJson(DataBank.getAllUsers());
    }

    public Validations changeProfilePassword(String command, Scanner scanner) {
        String newPasswordRegex = "\\-n\\s+(?<password>\"[^\"]+\"|\\S+)";
        String oldPasswordRegex = "\\-o\\s+(?<password>\"[^\"]+\"|\\S+)";
        Matcher newPassMatcher = Pattern.compile(newPasswordRegex).matcher(command);
        Matcher oldPassMatcher = Pattern.compile(oldPasswordRegex).matcher(command);
        if (!newPassMatcher.find() || !oldPassMatcher.find())
            return Validations.EMPTY_FIELD;
        String newPassword = withoutQuotation(newPassMatcher.group("password"));
        String oldPassword = withoutQuotation(oldPassMatcher.group("password"));
        if (!currentUser.getCodedPassword().equals(User.hashString(oldPassword)))
            return Validations.INCORRECT_PASSWORD;
        if (newPassword.equals(oldPassword))
            return Validations.NOT_NEW_PASSWORD;
        if (!newPassword.equals("random")) {
            if (!signUpController.isPasswordStrong(newPassword))
                return Validations.WEAK_PASSWORD;
            System.out.println(signUpController.checkingCaptcha(scanner));
            currentUser.setCodedPassword(User.hashString(newPassword));
            SignUpController.writeToJson(DataBank.getAllUsers());
            return Validations.PASSWORD_CHANGE_SUCCESSFUL;
        } else {
            newPassword = signUpController.whenPasswordIsRandom(scanner, newPassword);
            if (newPassword.equals("false"))
                return Validations.INVALID_CONFIRM_FOR_RANDOM;
            System.out.println(signUpController.checkingCaptcha(scanner));
            currentUser.setCodedPassword(User.hashString(newPassword));
            SignUpController.writeToJson(DataBank.getAllUsers());
            return Validations.PASSWORD_CHANGED_TO_RANDOM;
        }

    }

    private String randomSlogan() {
        Random random = new Random();
        int rand = random.nextInt(4);
        return Slogan.randomSlogan(rand);
    }

    public Validations changeUsername(String newUsername) {
        if (newUsername.matches(".*\\W.*"))
            return Validations.INVALID_USERNAME;
        if (DataBank.getUserByUsername(newUsername) != null)
            return Validations.DUPLICATE_USERNAME;
        currentUser.setUsername(withoutQuotation(newUsername));
        SignUpController.writeToJson(DataBank.getAllUsers());
        return Validations.USERNAME_CHANGE_SUCCESSFUL;
    }

    public String recommendUsername(String username) {
        return username + "_" + DataBank.getAllUsers().size();
    }

    public void setRecommendedUsername(String username) {
        currentUser.setUsername(username);
        SignUpController.writeToJson(DataBank.getAllUsers());
    }

    public Validations changeNickname(String newNickname) {
        currentUser.setNickname(withoutQuotation(newNickname));
        SignUpController.writeToJson(DataBank.getAllUsers());
        return Validations.NICKNAME_CHANGE_SUCCESSFUL;
    }

    public Validations changeEmail(String newEmail) {
        if (isEmailUsed(newEmail))
            return Validations.DUPLICATE_EMAIL;
        if (!isEmailFormatOk(newEmail))
            return Validations.INVALID_EMAIL;
        currentUser.setEmail(withoutQuotation(newEmail));
        SignUpController.writeToJson(DataBank.getAllUsers());
        return Validations.EMAIL_CHANGE_SUCCESSFUL;
    }

    private boolean isEmailUsed(String email) {
        for (User user : DataBank.getAllUsers()) {
            if (user.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }

    private boolean isEmailFormatOk(String email) {
        String regex = "[a-zA-Z0-9_.]+@[a-zA-Z0-9_.]+\\.[a-zA-Z0-9_.]+";
        return Pattern.compile(regex).matcher(email).matches();
    }

    public String withoutQuotation(String string) {
        if (string.charAt(0) == '"') return string.substring(1, string.length() - 1);
        return string;
    }

    public int displayHighScore() {
        return currentUser.getHighScore();
    }

    public int displayRank() {
        return currentUser.getRank();
    }

    public String displaySlogan() {
        if (currentUser.getSlogan() == null)
            return "Slogan is empty!";
        return currentUser.getSlogan();
    }

    public String displayProfile() {
        String result = "";
        result += "Username: " + currentUser.getUsername() + "\n";
        result += "Nickname: " + currentUser.getNickname() + "\n";
        result += "Email: " + currentUser.getEmail() + "\n";
        if (currentUser.getSlogan() != null)
            result += "Slogan: " + currentUser.getSlogan() + "\n";
        result += "HighScore: " + currentUser.getHighScore() + "\n";
        result += "rank: " + currentUser.getRank();
        return result;
    }
}
