package org.example;

import java.io.DataOutput;
import java.io.DataOutputStream;

public class Task {
    private String name;
    private Worker worker;
    private TaskPossibility taskPossibility;
    private DataOutputStream dataOutputStream;
    private int targetedWorker;

    public Task(String name, Worker worker, TaskPossibility taskPossibility,DataOutputStream dataOutputStream,int targetedWorker) {
        this.name = name;
        this.worker = worker;
        this.taskPossibility = taskPossibility;
        this.dataOutputStream=dataOutputStream;
        this.targetedWorker=targetedWorker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public TaskPossibility getTaskPossibility() {
        return taskPossibility;
    }

    public void setTaskPossibility(TaskPossibility taskPossibility) {
        this.taskPossibility = taskPossibility;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public int getTargetedWorker() {
        return targetedWorker;
    }

    public void setTargetedWorker(int targetedWorker) {
        this.targetedWorker = targetedWorker;
    }
}
