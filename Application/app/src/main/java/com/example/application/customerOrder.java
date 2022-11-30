package com.example.application;

import java.util.*;
import java.io.*;

public class customerOrder implements Serializable {
    private final String orderID;
    private final String chefID;
    private final String mealName;
    private final String quantity;
    private final String hasRated;
    private final String hasComplaint;
    private final String status;

    public customerOrder(HashMap<String, Object> orderInfo, String orderID) {
        this.orderID = orderID;
        this.chefID = (String) orderInfo.get("chefID");
        this.mealName = (String) orderInfo.get("mealName");
        this.quantity = (String) orderInfo.get("quantity");
        this.hasRated = (String) orderInfo.get("hasRated");
        this.hasComplaint = (String) orderInfo.get("hasComplaint");
        this.status = (String) orderInfo.get("status");
    }

    public String getOrderID() { return this.orderID; }
    public String getChefID() { return this.chefID; }
    public String getMealName() { return this.mealName; }
    public String getQuantity() { return this.quantity; }
    public String getHasRated() { return this.hasRated; }
    public String getHasComplaint() { return this.hasComplaint; }
    public String getStatus() { return this.status; }
}
