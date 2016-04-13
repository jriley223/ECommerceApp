package controller;

import java.util.Date;
import java.sql.Timestamp;
import javax.persistence.TemporalType;

/**
 *
 * @author juriley
 */
public class UserTransaction {

    private String userName;
    private int itemID;
    private String description;
    private double price;
    private String name;
    private int id;
    private Timestamp timeStamp;

    public UserTransaction() {
    }

    public UserTransaction(String username, int itemID) {
        this.userName = username;
        this.itemID = itemID;
    }

    public UserTransaction(String username, int itemID, Timestamp timeStamp) {
        this.userName = username;
        this.itemID = itemID;
        this.timeStamp = timeStamp;
    }

    public UserTransaction(String username, int itemID, String name, String description, double price, Timestamp timeStamp) {
        this.userName = username;
        this.itemID = itemID;
        this.description = description;
        this.price = price;
        this.name = name;
        this.timeStamp = timeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp() {
        Date date = new Date();
        this.timeStamp = new Timestamp(date.getTime());
    }

}
