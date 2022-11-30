package com.example.application;

import java.io.Serializable;
import java.util.HashMap;

public class ChefOrder implements Serializable {

    private final String orderID;
    private final String customerID;
    private final String quantity;
    private final Meal meal;
    private final String priceOfOrder;
    private final String status;

    public ChefOrder(HashMap<String, Object> orderInfo, Meal meal){
        this.orderID = (String) orderInfo.get("orderID");
        this.customerID = (String) orderInfo.get("customerID");
        this.quantity = (String) orderInfo.get("quantity");
        this.meal = meal;
        this.priceOfOrder = (String) orderInfo.get("priceOfOrder");
        this.status = (String) orderInfo.get("status");
    }

    public Meal getMeal() {
        return meal;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getPriceOfOrder() {
        return priceOfOrder;
    }

    public String getQuantity() {
        return quantity;
    }
}
