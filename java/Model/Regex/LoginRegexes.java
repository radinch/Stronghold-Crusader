package Model.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginRegexes {
    USERNAME("\\-u\\s+(?<username>(\"(.+)\")|\\S+)"),
    PASSWORD("\\-p\\s+(?<password>\"[^\"]+\"|\\S+)"),
    STAY_LOGGED_IN("\\-\\-stay\\-logged\\-in"),
    USER_LOGIN("user\\s+login\\s+.+"),
    FORGOT_PASSWORD("forgot\\s+my\\s+password\\s+\\-u\\s+(?<username>(\"(.+)\")|\\S+)");
    private String regex;

    private LoginRegexes(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
}
