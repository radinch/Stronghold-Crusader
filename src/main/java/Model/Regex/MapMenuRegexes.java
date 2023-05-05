package Model.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapMenuRegexes {
    SHOW_MAP("show\\s+map\\s+\\-x\\s+(?<x>\\d+)\\s+\\-y\\s+(?<y>\\d+)"),
    MOVE_MAP("map.+"),
    DOWN(".*down\\s+(?<number>\\d+)?.*"),
    UP(".*up\\s+(?<number>\\d+)?.*"),
    LEFT(".*left\\s+(?<number>\\d+)?.*"),
    RIGHT(".*right\\s+(?<number>\\d+)?.*"),
    SHOW_DETAILS("show\\s+details\\s+\\-x\\s+(?<x>\\d+)\\s+\\-y\\s+(?<y>\\d+)");
    private final String regex;

    private MapMenuRegexes(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, MapMenuRegexes mapMenuRegexes) {
        Matcher matcher=Pattern.compile(mapMenuRegexes.regex).matcher(input);
        if(matcher.matches())
            return matcher;
        return null;
    }
}
