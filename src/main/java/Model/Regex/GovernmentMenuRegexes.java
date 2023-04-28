package Model.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GovernmentMenuRegexes {
    CHANGE_FOOD_RATE("food\\s+rate\\s+-r\\s+(?<rate>-)"),
    CHANGE_TAX_RATE("tax\\s+rate\\s+-r\\s+(?<rate>-)"),
    CHANGE_FEAR_RATE("fear\\s+rate\\s+-r\\s+(?<rate>-)");
    private String regex;

    GovernmentMenuRegexes(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, GovernmentMenuRegexes governmentMenuRegexes) {
       Matcher matcher=Pattern.compile(governmentMenuRegexes.regex).matcher(input);
       if(matcher.matches())
            return matcher;
       return null;
    }
}
