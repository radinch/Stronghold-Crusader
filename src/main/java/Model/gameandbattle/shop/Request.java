package Model.gameandbattle.shop;

import Controller.DataBank;
import Model.gameandbattle.Government;
import Model.gameandbattle.stockpile.Resource;

public class Request {
    private Government sender;
    private Government receiver;
    private Resource resource;
    private String senderMessage;
    private String receiverMessage;
    private int price;
    private int amount;
    private int id;

    public Request(Government sender, Government receiver, Resource resource, String senderMessage, int price, int amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.resource = resource;
        this.senderMessage = senderMessage;
        this.price = price;
        this.amount = amount;
        receiverMessage=null;
        this.id= DataBank.getRequests().size()+1;
    }

    public Government getSender() {
        return sender;
    }

    public void setSender(Government sender) {
        this.sender = sender;
    }

    public Government getReceiver() {
        return receiver;
    }

    public void setReceiver(Government receiver) {
        this.receiver = receiver;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getSenderMessage() {
        return senderMessage;
    }

    public void setSenderMessage(String senderMessage) {
        this.senderMessage = senderMessage;
    }

    public String getReceiverMessage() {
        return receiverMessage;
    }

    public void setReceiverMessage(String receiverMessage) {
        this.receiverMessage = receiverMessage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
