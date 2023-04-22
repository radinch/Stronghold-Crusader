package Model.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginRegexes {
    REGISTER("user\\s+create\\s+.+"),
    NICKNAME("\\-n\\s+((?<nickname>\\S+)|(\"(?<nickname>.+)\"))"),
    USERNAME("\\-u\\s+((?<username>\\S+)|(\"(?<username>.+)\"))"),
    PASSWORD("\\-p\\s+((?<password>\\S+)|(\"(?<password>.+)\"))\\s+((?<repeated>\\S+)|(\"(?<repeated>.+)\"))"),
    PASSWORD_CONFIRMATION("\\-p\\s+((?<password>\\S+)|(\"(?<password>.+)\"))\\s+((?<repeated>\\S+)|(\"(?<repeated>.+)\"))"),
    EMAIL("\\-email\\s+(?<email>\\S+)"),
    SLOGAN("\\-s\\s+((?<slogan>\\S+)|(\"(?<slogan>.+)\"))"),
    IS_A_FIELD_EMPTY("\\-\\S+\\s+\\-");

    private String regex;
    private LoginRegexes(String regex){
        this.regex=regex;
    }
    public Matcher getMatcher(String input){
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher= pattern.matcher(input);
        return matcher;
    }
}
