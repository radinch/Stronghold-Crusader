module project {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires javafx.media;
    requires java.desktop;
    requires com.google.gson;
    exports  View.graphic;
    opens View.graphic to javafx.fxml,com.fasterxml.jackson.core,com.google.gson;
    exports Controller;
    opens Controller to com.fasterxml.jackson.core;
    exports Model.signup_login_profile;
    opens Model.signup_login_profile to com.fasterxml.jackson.core;
    exports org.example;
    opens org.example to com.google.gson;
}