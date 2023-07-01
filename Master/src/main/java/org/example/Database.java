package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private static ArrayList<Worker> workers=new ArrayList<>();
    private static ArrayList<Task> allTasks=new ArrayList<>();

    public synchronized static ArrayList<Task> getAllTasks() {
        return allTasks;
    }

    public synchronized static void setAllTasks(ArrayList<Task> allTasks) {
        Database.allTasks = allTasks;
    }

    public synchronized static ArrayList<Worker> getWorkers() {
        return workers;
    }

    public synchronized static void setWorkers(ArrayList<Worker> workers) {
        Database.workers = workers;
    }
    public static synchronized Worker getWorkerById(int id){
        for (Worker worker:workers) {
            if (worker.getId()==id) return worker;
        }
        return null;
    }
    public static synchronized Task getTaskByName(String name){
        for (Task task:allTasks){
            if (task.getName().equals(name)) return task;
        }
        return null;
    }
}
