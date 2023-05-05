package Model.signup_login_profile;

public enum SecurityQuestion {
    FIRST("What is my father’s name"),
    SECOND("What was my first pet’s name?"),
    THIRD("What is my mother’s last name?");
    private String question;

    SecurityQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public static String getQuestionByNUmber(int number) {
        if (number == 1) return FIRST.question;
        if (number == 2) return SECOND.question;
        return THIRD.question;
    }
}
