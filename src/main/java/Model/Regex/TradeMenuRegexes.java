package Model.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuRegexes {
    TRADE("trade\\s+\\-t\\s+(?<type>.+)\\s+\\-a\\s+(?<amount>.+)\\s+\\-p\\s+(?<price>.+)\\s+\\-m\\s+(?<message>.+)"),
    ACCEPT_TRADE("trade accept\\s+\\-i\\s+(?<id>\\d+)\\s+\\-m\\s+(?<message>.+)");
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
