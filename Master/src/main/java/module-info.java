module Master {
    requires com.google.gson;
    exports org.example;
    opens org.example to com.google.gson;
}