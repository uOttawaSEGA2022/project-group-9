package com.example.application;

import java.util.List;

public class Meal {
    String name;
    String type;
    String cuisine;
    List<String> ingrediants;
    List<String> allergens;
    double price;
    String desc;
    String cook;
    public Meal(String name,
    String type,
    String cuisine,
    List<String> ingrediants,
    List<String> allergens,
    double price,
    String desc,
    String cook)
    {
         this.name=name;
        this.type=type;
        this.cuisine=cuisine;
        this.ingrediants=ingrediants;
        this.allergens=allergens;
        this.price=price;
         this.desc=desc;
        this.cook=cook;
    }
    public Meal(String name,
                String type,
                String cuisine,


                double price,
                String desc,
                String cook)
    {
        this.name=name;
        this.type=type;
        this.cuisine=cuisine;

        this.price=price;
        this.desc=desc;
        this.cook=cook;
    }

    public void addingrediants(List<String> l)
    {
        this.ingrediants=l;
    }

    public void addallergens(List<String> l)
    {
        this.allergens=l;
    }
    public String getName()
    {
        return name;
    }
}
