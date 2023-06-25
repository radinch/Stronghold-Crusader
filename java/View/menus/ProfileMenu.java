package View.menus;

import java.util.Scanner;
import java.util.regex.Matcher;

import Controller.MainMenuController;
import Controller.ProfileMenuController;
import Model.Regex.ProfileMenuRegexes;
import Model.signup_login_profile.User;
import View.enums.Validations;

public class ProfileMenu{

    private final ProfileMenuController profileMenuController;
    public ProfileMenu(ProfileMenuController profileMenuController) {
        this.profileMenuController = profileMenuController;
    }

    public void run(Scanner scanner) {
        System.out.println("Welcome to the profile menu");
        String command;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if ((matcher = ProfileMenuRegexes.getMatcher(command, ProfileMenuRegexes.CHANGE_PROFILE_ATTRIBUTE)) != null) {
                Validations changeAttributeEnum = profileMenuController.changeProfileAttribute(matcher);
                changeProfileAttribute(changeAttributeEnum, scanner, matcher);
            } else if ((matcher = ProfileMenuRegexes.getMatcher(command, ProfileMenuRegexes.CHANGE_SLOGAN)) != null) {
                Validations changeSloganEnum = profileMenuController.changeProfileSlogan(matcher);
                changeProfileSlogan(changeSloganEnum);
            } else if ((matcher = ProfileMenuRegexes.getMatcher(command, ProfileMenuRegexes.CHANGE_PASSWORD)) != null) {
                Validations changePasswordEnum = profileMenuController.changeProfilePassword(command, scanner);
                changePassword(changePasswordEnum, scanner);
            } else if (ProfileMenuRegexes.getMatcher(command, ProfileMenuRegexes.DISPLAY_HIGH_SCORE) != null)
                System.out.println(profileMenuController.displayHighScore());
            else if (ProfileMenuRegexes.getMatcher(command, ProfileMenuRegexes.DISPLAY_RANK) != null)
                System.out.println(profileMenuController.displayRank());
            else if (ProfileMenuRegexes.getMatcher(command, ProfileMenuRegexes.DISPLAY_SLOGAN) != null)
                System.out.println(profileMenuController.displaySlogan());
            else if (ProfileMenuRegexes.getMatcher(command, ProfileMenuRegexes.DISPLAY_PROFILE) != null)
                System.out.println(profileMenuController.displayProfile());
            else if (ProfileMenuRegexes.getMatcher(command, ProfileMenuRegexes.REMOVE_SLOGAN) != null) {
                profileMenuController.removeSlogan();
                System.out.println("your slogan removed successfully");
            } else if (command.matches("user\\s+logout")) {
                System.out.println("user successfully logged out");
                return;
            } else if(command.matches("main\\s+menu")) {
                MainMenuController mainMenuController = new MainMenuController();
                mainMenuController.run(scanner);
            }
            else if(command.equals("exit")) return;
            else
                System.out.println("invalid command");
        }
    }

    public void changeProfileAttribute(Validations changeAttribute, Scanner scanner, Matcher matcher) {
        switch (changeAttribute) {
            case INVALID_USERNAME:
                System.out.println("change profile failed: invalid username");
                break;
            case EMPTY_FIELD:
                System.out.println("change profile failed: empty field");
                break;
            case DUPLICATE_USERNAME:
                String recommendedUsername = profileMenuController.recommendUsername(matcher.group("characteristic"));
                String answerForRecommendation;
                System.out.println("change profile failed: duplicate username");
                System.out.println("recommended Username: " + recommendedUsername);
                System.out.println("do you want to choose this username? [YES/NO]");
                answerForRecommendation = scanner.nextLine();
                while (!answerForRecommendation.matches("YES|NO")) {
                    answerForRecommendation = scanner.nextLine();
                    System.out.println("invalid answer");
                }
                if (answerForRecommendation.equals("YES")) {
                    profileMenuController.setRecommendedUsername(recommendedUsername);
                    System.out.println("username change successful");
                } else
                    System.out.println("ok!");
                break;
            case INVALID_FLAG:
                System.out.println("change profile failed: invalid flag");
                break;
            case USERNAME_CHANGE_SUCCESSFUL:
                System.out.println("username change successful");
                break;
            case NICKNAME_CHANGE_SUCCESSFUL:
                System.out.println("nickname change successful");
                break;
            case INVALID_EMAIL:
                System.out.println("change profile failed: invalid email");
                break;
            case DUPLICATE_EMAIL:
                System.out.println("change profile failed: duplicate email");
                break;
            case EMAIL_CHANGE_SUCCESSFUL:
                System.out.println("email change successful");
                break;
        }
    }

    public void changeProfileSlogan(Validations changeSlogan) {
        switch (changeSlogan) {
            case EMPTY_FIELD:
                System.out.println("change profile failed: empty field");
                break;
            case INVALID_FLAG:
                System.out.println("change profile failed: invalid flag");
                break;
            case SLOGAN_CHANGE_SUCCESSFUL:
                System.out.println("slogan change successful");
                break;
            case SLOGAN_CHANGED_TO_RANDOM:
                System.out.println("slogan change successful\nyour new slogan is: " +
                        profileMenuController.getCurrentUser().getSlogan());
                break;
        }
    }

    public void changePassword(Validations changePasswordEnum, Scanner scanner) {
        switch (changePasswordEnum) {
            case EMPTY_FIELD:
                System.out.println("change profile failed: empty field");
                break;
            case INCORRECT_PASSWORD:
                System.out.println("change profile failed: incorrect password");
                break;
            case NOT_NEW_PASSWORD:
                System.out.println("change profile failed: not new password");
                break;
            case WEAK_PASSWORD:
                //System.out.println("CHANGE PROFILE FAILED: WEAK PASSWORD");
                break;
            case PASSWORD_CHANGE_SUCCESSFUL:
                System.out.println("please enter your new password again");
                while (!profileMenuController.getCurrentUser().getCodedPassword().equals(User.hashString(scanner.nextLine()))) {
                    System.out.println("please enter your new password correct");
                }
                System.out.println("change password successful");
                break;
            case INVALID_CONFIRM_FOR_RANDOM:
                System.out.println("so please try registering again!");
                break;
        }
    }
}
