package Model.Regex;

import View.menus.DropElementMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum DropElementMenuRegexes {
    SET_A_TILE_TEXTURE("settexture\\s+\\-x\\s+(?<x>\\-?\\d+)\\s+\\-y\\s+(?<y>\\-?\\d+)\\s+\\-t\\s+(?<type>.+)"),
    SET_A_GROUP_TEXTURE("settexture\\s+\\-x1\\s+(?<x1>\\-?\\d+)\\s+\\-y1\\s+(?<y1>\\-?\\d+)\\s+\\-x2\\s+(?<x2>\\-?\\d+)\\s+\\-y2\\s+(?<y2>\\-?\\d+)\\-t\\s+(?<type>.+)"),
    CLEAR("clear\\s+\\-x\\s+(?<x>-?\\d+)\\s+\\-y\\s+(?<y>-?\\d+)"),
    DROP_ROCK("\"droprock\\s+\\-x\\s+(?<x>-?\\d+)\\s+\\-y\\s+(?<y>-?\\d+)\\s+\\-d\\s+(?<d>s|w|e|n|r)"),
    DROP_TREE("droptree\\s+\\-x\\s+(?<x>\\-?\\d+)\\s+\\-y\\s+(?<y>\\-?\\d+)\\s+\\-t\\s+(?<type>.+)"),
    DROP_WATER("dropwater\\s+\\-x\\s+(?<x>\\-?\\d+)\\s+\\-y\\s+(?<y>\\-?\\d+)\\s+\\-t\\s+(?<type>.+)"),
    DROP_BUILDING(""),
    DROP_UNIT("");
    private final String regex;

    DropElementMenuRegexes(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, DropElementMenuRegexes dropElementMenuRegexes) {
        Matcher matcher = Pattern.compile(dropElementMenuRegexes.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
