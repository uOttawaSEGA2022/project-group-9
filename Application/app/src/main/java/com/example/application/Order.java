package com.example.application;

import java.util.*;
import java.io.*;

public class Order implements Serializable {
    private final String orderID;
    private String emailOfChef;
    private String mealName;
    private String quantity;
    private String hasRated;
    private String hasComplaint;

    public Order(HashMap<String, Object> orderInfo, String orderID) {
        this.orderID = orderID;
        this.emailOfChef = (String) orderInfo.get("emailOfChef");
        this.mealName = (String) orderInfo.get("mealName");
        this.quantity = (String) orderInfo.get("quantity");
        this.hasRated = (String) orderInfo.get("hasRated");
        this.hasComplaint = (String) orderInfo.get("hasComplaint");
    }

    public String getOrderID() { return this.orderID; }
    public String getEmailOfChef() { return this.emailOfChef; }
    public String getMealName() { return this.mealName; }
    public String getQuantity() { return this.quantity; }
    public String getHasRated() { return this.hasRated; }
    public String getHasComplaint() { return this.hasComplaint; }
}
