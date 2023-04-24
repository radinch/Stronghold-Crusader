package view;

import controller.ProfileMenuController;
import view.enums.Validations;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {

    private final ProfileMenuController profileMenuController;
    public ProfileMenu(ProfileMenuController profileMenuController) {
        this.profileMenuController=profileMenuController;
    }

    public void run() {
        String command;
        Matcher matcher;
        Scanner scanner=Commands.getScanner();
        while(true) {
            command=scanner.nextLine();
            if((matcher = Commands.getMatcher(command, Commands.CHANGE_PROFILE_ATTRIBUTE)) != null) {
                Validations changeAttribute=profileMenuController.changeProfileAttribute(matcher);
                switch (changeAttribute) {
                    case INVALID_USERNAME:
                        System.out.println("CHANGE PROFILE FAILED: INVALID USERNAME");
                        break;
                    case EMPTY_FIELD:
                        System.out.println("CHANGE PROFILE FAILED: EMPTY FLAG");
                        break;
                    case DUPLICATE_USERNAME:
                        System.out.println("CHANGE PROFILE FAILED: DUPLICATE USERNAME");
                        System.out.println("you can choose this username: " +
                                profileMenuController.recommendUsername(matcher.group("characteristic")));
                        break;
                    case USERNAME_CHANGE_SUCCESSFUL:
                        System.out.println("USERNAME CHANGE SUCCESSFUL");
                }
            }
        }
    }
}
