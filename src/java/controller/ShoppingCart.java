package controller;

import java.util.List;
import java.util.ArrayList;
import java.text.NumberFormat;

public class ShoppingCart {

    private static NumberFormat currency = NumberFormat.getCurrencyInstance();
    private String userName;
    private int itemID;
    private int id;

    public ShoppingCart() {
    }

    public ShoppingCart(String userName, int itemId) {
        this.userName = userName;
        this.itemID = itemId;
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

}
