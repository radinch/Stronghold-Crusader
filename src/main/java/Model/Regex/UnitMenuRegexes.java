package Model.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UnitMenuRegexes {
    SELECT_UNIT("select unit \\-x (?<x>\\d+) \\-y (?<y>\\d+)"),
    MOVE_UNIT("move unit \\-x (?<x>\\d+) \\-y (?<y>\\d+)"),
    PATROL_UNIT("patrol unit \\-x (?<x>\\d+) \\-y (?<y>\\d+)"),
    SET_CONDITION("set \\-x (?<x>\\d+) \\-y (?<y>\\d+) \\-s (?<condition>.+)"),
    ATTACK("attack\\s+\\-e\\s+(?<x>\\-?\\d+)\\s+(?<y>\\-?\\d+)"),
    AIR_ATTACK("attack\\s+\\-x\\s+(?<x>\\-?\\d+)\\-y\\s+(?<y>\\-?\\d+)"),
    EQUIP_WITT_OIL("Equip with oil"),
    POUR_OIL("pour oil\\s+\\-d\\s+(?<direction>.+)"),
    DIG_DITCH("dig ditch\\s+\\-x\\s+(?<x>\\-?\\d+)\\-y\\s+(?<y>\\-?\\d+)"),
    CANCEL_DITCH("cancel ditch\\s+\\-x\\s+(?<x>\\-?\\d+)\\-y\\s+(?<y>\\-?\\d+)"),
    REMOVE_DITCH("remove ditch\\s+\\-x\\s+(?<x>\\-?\\d+)\\-y\\s+(?<y>\\-?\\d+)"),
    DIG_TUNNEL("dig tunnel \\-x (?<x>\\d+) \\-y (?<y>\\d+)"),
    BUILD_SURROUNDINGS("build \\-q (?<equipment>.+)");
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
