package Model.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignUpRegexes {
    REGISTER("user\\s+create\\s+.+"),
    NICKNAME("\\-n\\s+(?<nickname>(\"(.+)\")|\\S+)"),
    USERNAME("\\-u\\s+(?<username>(\"(.+)\")|\\S+)"),
    PASSWORD("\\-p\\s+(?<password>\"[^\"]+\"|\\S+)"),
    PASSWORD_CONFIRMATION("\\-p\\s+(?<password>(\"([^\"]+)\")|\\S+)\\s+(?<repeated>(\"([^\"]+)\")|\\S+)"),
    EMAIL("\\-email\\s+(?<email>\\S+)"),
    SLOGAN("\\-s\\s+(?<slogan>(\"(.+)\")|\\S+)"),
    IS_A_FIELD_EMPTY("\\-\\S+\\s+\\-"),
    PICK_QUESTION("question\\s+pick\\s+.+"),
    QUESTION_NUMBER("\\-q\\s+(?<number>\\d)"),
    QUESTION_ANSWER("\\-a\\s+(?<answer>(\"(.+)\")|\\S+)"),
    QUESTION_CONFIRMATION("\\-c\\s+(?<confirm>(\"(.+)\")|\\S+)");

    private String regex;

    private SignUpRegexes(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
}
