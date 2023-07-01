package org.example;

import com.google.gson.Gson;

import java.io.Serializable;

public class Edit implements Serializable {
    private int i;
    private String text;

    public Edit(int i, String text) {
        this.i = i;
        this.text = text;
    }
    public Edit(){

    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public String toJson(){
        return new Gson().toJson(this);
    }
}
