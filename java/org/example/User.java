package org.example;

import Controller.DataBank;
import Model.UserNetwork;
import Model.gameandbattle.shop.Request;
import Model.signup_login_profile.Avatar;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User implements Serializable {
    String username;
     String codedPassword;
    String nickname;
    String email;
     String slogan;
    int securityQuestion;
     String answer;
     int failedAttemptsToLogin;
    int highScore;
    int rank;
     String avatar;
    public User(String username, String password, String nickname, String email,String host,int port) throws IOException {
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        slogan = null;
        answer = null;
        this.codedPassword = hashString(password);
        highScore = 0;
        rank = DataBank.getAllUsers().size() + 1;
        avatar = Avatar.class.getResource("/IMAGE/Avatars/"+8+".png").toExternalForm();
        Socket socket = new Socket(host, port);
        UserNetwork userNetwork=new UserNetwork(this,socket);
        DataBank.userNetworks.add(userNetwork);
        userNetwork.start();
    }

    public User() {

    }
    ////////////////////////////////////////////////////////////////////////////// setters and getters

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCodedPassword() {
        return codedPassword;
    }

    public void setCodedPassword(String codedPassword) {
        this.codedPassword = codedPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(int securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setFailedAttemptsToLogin(int failedAttemptsToLogin) {
        this.failedAttemptsToLogin = failedAttemptsToLogin;
    }

    public int getFailedAttemptsToLogin() {
        return failedAttemptsToLogin;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static String hashString(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public int calculateTimeToWait() {
        return failedAttemptsToLogin * 5;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String toJson(){
        return new Gson().toJson(this);
    }
}
