package Model.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UnitMenuRegexes {
    SELECT_UNIT("select unit -x (?<x>\\d+) -y (?<y>\\d+)"),
    MOVE_UNIT("move unit -x (?<x>\\d+) -y (?<y>\\d+)"),
    PATROL_UNIT("patrol unit -x (?<x>\\d+) -y (?<y>\\d+)"),
    SET_CONDITION("set -x (?<x>\\d+) -y (?<y>\\d+) -s (?<condition>.+)"),
    ATTACK(""),
    SKY_ATTACK(""),
    POUR_OIL(""),
    DIG_TUNNEL(""),
    BUILD_SURROUNDINGS(""),
    DISBAND_UNIT("");
    private String regex;

    private UnitMenuRegexes(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
}
