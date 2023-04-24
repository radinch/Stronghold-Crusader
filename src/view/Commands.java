package view;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {

    CHANGE_PROFILE_ATTRIBUTE("change\\s+profile(\\s+\\-(?<flag>\\w)\\s+(?<characteristic>.+)?)?");
    private final String regex;
    private static final Scanner scanner = new Scanner(System.in);

    Commands(String regex) {
        this.regex=regex;
    }

    public static Matcher getMatcher(String input, Commands commands) {
        Matcher matcher=Pattern.compile(commands.regex).matcher(input);
        if(matcher.matches())
            return matcher;
        return null;
    }

    public static Scanner getScanner() {
        return scanner;
    }
}
