package Model.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UnitMenuRegexes {
    SELECT_UNIT(""),
    MOVE_UNIT(""),
    SET_CONDITION(""),
    ATTACK("attack\\s+\\-e\\s+(?<x>\\-?\\d+)\\s+(?<y>\\-?\\d+)"),
    AIR_ATTACK("attack\\s+\\-x\\s+(?<x>\\-?\\d+)\\-y\\s+(?<y>\\-?\\d+)"),
    EQUIP_WITT_OIL("Equip with oil"),
    POUR_OIL("pour oil\\s+\\-d\\s+(?<direction>.+)"),
    DIG_DITCH("dig ditch\\s+\\-x\\s+(?<x>\\-?\\d+)\\-y\\s+(?<y>\\-?\\d+)"),
    CANCEL_DITCH("cancel ditch\\s+\\-x\\s+(?<x>\\-?\\d+)\\-y\\s+(?<y>\\-?\\d+)"),
    REMOVE_DITCH("remove ditch\\s+\\-x\\s+(?<x>\\-?\\d+)\\-y\\s+(?<y>\\-?\\d+)"),
    DIG_TUNNEL(""),
    BUILD_SURROUNDINGS("");
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
