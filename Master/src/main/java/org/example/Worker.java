package org.example;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Worker {
    private ArrayList<Task> tasks;
    private int id;
    public static int MAX_TASK_NUMBER=3;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    public Worker(Socket socket,DataOutputStream dataOutputStream){
        id=Database.getWorkers().size();
        tasks=new ArrayList<>();
        this.socket=socket;
        this.dataOutputStream=dataOutputStream;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }
}
