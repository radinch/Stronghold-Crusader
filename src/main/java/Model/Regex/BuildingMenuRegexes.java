package Model.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum BuildingMenuRegexes {
    DROP_BUILDING("dropbuilding\\s+\\-x\\s+(?<x>\\-?\\d+)\\s+\\-y\\s+(?<y>\\-?\\d+)\\s+\\-type\\s+(?<type>.+)"),
    SELECT_BUILDING("select building\\s+\\-x\\s+(?<x>\\-?\\d+)\\s+\\-y\\s+(?<y>\\-?\\d+)"),
    CREATE_UNIT("createunit\\s+\\-t\\s+(?<type>.+)\\s+\\-c\\s+(?<count>\\d+)");

    private final String regex;

    BuildingMenuRegexes(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, BuildingMenuRegexes buildingMenuRegexes) {
        Matcher matcher=Pattern.compile(buildingMenuRegexes.regex).matcher(input);
        if(matcher.matches())
            return matcher;
        return null;
    }
}
