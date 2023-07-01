package org.example;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import org.example.Controller.DataBank;

import java.io.*;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.Type;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Connection extends Thread {
    Socket socket;
    private ObjectInputStream objectInputStream;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    private String type;
    private String data;

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public Connection(Socket socket) throws IOException {
        System.out.println("New connection form: " + socket.getInetAddress() + ":" + socket.getPort());
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public synchronized void run() {
        while (true){
            try {
                handleTasks();
            } catch (IOException | ClassNotFoundException e) {

            }
        }
    }

    private void handleTasks() throws IOException, ClassNotFoundException {
        type=dataInputStream.readUTF();
        data = dataInputStream.readUTF();
        if (type.equals("User")){
            handleUser();
        }
        if (type.equals("Message")){
            handleMessage();
        }
        if (type.equals("Edit")){
            handleEdit();
        }
        if(type.equals("Offline")) {
            handleOffline();
        }
    }


    private void handleOffline() {
        User user = new Gson().fromJson(data,User.class);
        notifyAllUsersForScoreBoard(user);
    }

    private void handleEdit() {
        Edit edit=new Gson().fromJson(data,Edit.class);
        for (Connection connection: DataBank.connections){
            try {
                connection.getDataOutputStream().writeUTF("Edit");
                connection.getDataOutputStream().writeUTF(edit.toJson());
            }catch (Exception e){

            }
        }
    }

    private void handleMessage() {
        Message message=new Gson().fromJson(data,Message.class);
        if(message.getReceivers()==null) handleGlobal(message);
    }

    private void handleGlobal(Message message) {
        for (Connection connection: DataBank.connections){
            try {
                connection.getDataOutputStream().writeUTF("Message");
                connection.getDataOutputStream().writeUTF(message.toJson());
            }catch (Exception e){

            }
        }
    }

    private void handleUser() {
        User user=new Gson().fromJson(data,User.class);
        DataBank.getAllUsers().add(user);
        notifyALLUsers(user);
        System.out.println(user.getUsername());
    }

    private void notifyAllUsersForScoreBoard(User user) {
        for (Connection connection : DataBank.connections) {
            try {
                connection.getDataOutputStream().writeUTF("Offline");
                connection.getDataOutputStream().writeUTF(user.toJson());
            } catch (Exception ignored) {

            }
        }
    }

    private void notifyALLUsers(User user) {
        for (Connection connection: DataBank.connections){
            try {
                connection.getDataOutputStream().writeUTF("User");
                connection.getDataOutputStream().writeUTF(user.toJson());
            }catch (Exception e){

            }
        }
    }

    private synchronized void handleClient() throws IOException {
        String data = dataInputStream.readUTF();
        Pattern pattern1=Pattern.compile("k create task --name=(?<name>\\w+)");
        Matcher matcher1=pattern1.matcher(data);
        Pattern pattern2=Pattern.compile("k create task --name=(?<name>\\w+) --node=(?<id>\\d+)");
        Matcher matcher2=pattern2.matcher(data);
        Pattern pattern3=Pattern.compile("k delete task --name=(?<name>\\w+)");
        Matcher matcher3=pattern3.matcher(data);
        if (matcher1.matches()){
            String name=matcher1.group("name");
            if (Database.getTaskByName(name)!=null){
                dataOutputStream.writeUTF("there is already another task with this id");
            }
            else if (isThereWorker()!=null) {
                Worker worker=isThereWorker();
                Task task=new Task(name,worker,TaskPossibility.RUNNING,dataOutputStream,-1);
                Database.getAllTasks().add(task);
                worker.getTasks().add(task);
                worker.getDataOutputStream().writeUTF("task "+ task.getName()+" was scheduled on time: "+ getTime());
                dataOutputStream.writeUTF("task is running on worker with id: "+worker.getId());
            }
            else {
                Task task=new Task(name,null,TaskPossibility.PENDING,dataOutputStream,-1);
                Database.getAllTasks().add(task);
                dataOutputStream.writeUTF("task pending");
            }
        }
        else if (matcher2.matches()){
            String name=matcher2.group("name");
            int id=Integer.parseInt(matcher2.group("id"));
            if (Database.getTaskByName(name)!=null){
                dataOutputStream.writeUTF("there is already another task with this id");
            }
            else if (Database.getWorkerById(id)==null){
                dataOutputStream.writeUTF("no worker with this id!");
            }
            else if (Database.getWorkerById(id).getTasks().size()<3) {
                Worker worker=Database.getWorkerById(id);
                Task task=new Task(name,worker,TaskPossibility.RUNNING,dataOutputStream,id);
                Database.getAllTasks().add(task);
                worker.getTasks().add(task);
                worker.getDataOutputStream().writeUTF("task "+ task.getName()+" was scheduled on time: "+ getTime());
                dataOutputStream.writeUTF("task is running on worker with id: "+worker.getId());
            }
            else {
                Task task=new Task(name,null,TaskPossibility.PENDING,dataOutputStream,id);
                Database.getAllTasks().add(task);
                dataOutputStream.writeUTF("task pending");
            }
        }
        else if (data.equals("k get tasks")){
            String output="";
            int counter=0;
            for (Task task:Database.getAllTasks()){
                if (task.getTaskPossibility()==TaskPossibility.RUNNING){
                    output=output+"task name is "+task.getName()+" and it is "+task.getTaskPossibility().toString()+" on worker: "+task.getWorker().getId()+"\n";
                    counter++;
                }
                else {
                    output=output+"task name is "+task.getName()+" and it is "+task.getTaskPossibility().toString()+"\n";
                }
            }
            if (counter==0) output=output+"no task is running";
            else output=output+"that's all!";
            dataOutputStream.writeUTF(output);
        }
        else if (data.equals("k get nodes")) {
            String output="";
            if (Database.getWorkers().size() == 0) dataOutputStream.writeUTF("there are no workers!");
            else {
                for (Worker worker : Database.getWorkers()) {
                    output=output+"worker id is: "+worker.getId()+" on ip: "+worker.getSocket().getInetAddress()+" on port: "+worker.getSocket().getPort()+"\n";
                }
                output=output+"done!";
                dataOutputStream.writeUTF(output);
            }
        }
        else if (matcher3.matches()){
            String name=matcher3.group("name");
            if (Database.getTaskByName(name)==null) dataOutputStream.writeUTF("no task with this name!");
            else {
                if (Database.getTaskByName(name).getTaskPossibility()==TaskPossibility.PENDING) Database.getAllTasks().remove(Database.getTaskByName(name));
                else {
                    Worker worker=Database.getTaskByName(name).getWorker();
                    handlePendings(worker);
                }
            }
        }
        else dataOutputStream.writeUTF("wrong command");
    }

    private String getTime() {
        LocalDateTime currentTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        return formattedTime;
    }

    private synchronized void handlePendings(Worker worker) throws IOException {
        for (Task task:Database.getAllTasks()){
            if (task.getTaskPossibility()==TaskPossibility.PENDING&&(task.getTargetedWorker()==-1||task.getTargetedWorker()==worker.getId())){
                task.setTaskPossibility(TaskPossibility.RUNNING);
                worker.getTasks().add(task);
                worker.getDataOutputStream().writeUTF("task "+ task.getName()+" was scheduled on time: "+ getTime());
                task.getDataOutputStream().writeUTF("task named "+task.getName()+" is now RUNNING");
                task.setWorker(worker);
                return;
            }
        }
    }

    public synchronized Worker isThereWorker(){
        for (Worker worker:Database.getWorkers()){
            if (worker.getTasks().size()<3) return worker;
        }
        return null;
    }

    private synchronized void handleWorker() {
        Worker worker=new Worker(socket,dataOutputStream);
        Database.getWorkers().add(worker);
    }

    private ClientType get_intro() throws IOException {
        String intro = dataInputStream.readUTF();
        switch (intro) {
            case "worker" -> {
                dataOutputStream.writeUTF("200: You are registered as a worker client.");
                return ClientType.WORKER;
            }
            case "client" -> {
                dataOutputStream.writeUTF("200: You are registered as a client.");
                return ClientType.CLIENT;
            }
            default -> {
                dataOutputStream.writeUTF("400: Invalid intro");
                return ClientType.UNKNOWN;
            }
        }
    }
    public String toJson(){
        return new Gson().toJson(this);
    }
}
