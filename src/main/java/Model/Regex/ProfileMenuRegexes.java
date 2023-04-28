package Model.Regex;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuRegexes {
    CHANGE_PROFILE_ATTRIBUTE("profile\\s+change(\\s+\\-(?<flag>\\S+)\\s+(?<characteristic>\\S+|(\"(.+)\"))?)?"),
    CHANGE_SLOGAN("profile\\s+change\\s+slogan(\\s+\\-(?<flag>\\S+)\\s+(?<slogan>.+)?)?"),
    REMOVE_SLOGAN("Profile remove slogan"),
    CHANGE_PASSWORD("profile change password\\s+.+"),
    NEW_PASSWORD("\\-n\\s+(?<password>\"[^\"]+\"|\\S+)"),
    OLD_PASSWORD("\\-o\\s+(?<password>\"[^\"]+\"|\\S+)"),
    DISPLAY_HIGH_SCORE("profile display highscore"),
    DISPLAY_RANK("profile display rank"),
    DISPLAY_SLOGAN("profile display slogan"),
    DISPLAY_PROFILE("profile display");

    private final String regex;
    private static final Scanner scanner = new Scanner(System.in);

    ProfileMenuRegexes(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, ProfileMenuRegexes profileMenuRegexes) {
        Matcher matcher = Pattern.compile(profileMenuRegexes.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }

    public static Scanner getScanner() {
        return scanner;
    }
}
