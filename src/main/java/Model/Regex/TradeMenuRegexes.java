package Model.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuRegexes {
    TRADE(""),
    TRADE_LIST(""),
    ACCEPT_TRADE(""),
    TRADE_HISTORY("");
    private String regex;

    private TradeMenuRegexes(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
}
