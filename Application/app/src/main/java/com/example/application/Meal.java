package com.example.application;

import java.util.HashMap;
import java.util.List;

public class Meal {
    String name;
    String type;
    String cuisine;
    List<String> ingredients;
    List<String> allergens;
    String price;
    String description;
    String cook;
    boolean isOffered;

    @SuppressWarnings({"All"})
    public Meal(HashMap<String, Object> mealInfo){
        this.name = (String) mealInfo.get("Name");
        this.type = (String) mealInfo.get("Type");
        this.cuisine = (String) mealInfo.get("Cuisine");
        this.ingredients = (List<String>) mealInfo.get("Ingredients");
        this.allergens = (List<String>) mealInfo.get("Allergens");
        this.price = (String) mealInfo.get("Price");
        this.description = (String) mealInfo.get("Description");
        this.cook = (String) mealInfo.get("Cook");
        this.isOffered = (boolean) mealInfo.get("IsOffered");
    }

    public HashMap<String, Object> toHashMap(){
        HashMap<String, Object> mealHashMap = new HashMap<String, Object>();
        mealHashMap.put("Name", name);
        mealHashMap.put("Type", type);
        mealHashMap.put("Cuisine", cuisine);
        mealHashMap.put("Ingredients", ingredients);
        mealHashMap.put("Allergens", allergens);
        mealHashMap.put("Price", price);
        mealHashMap.put("Description", description);
        mealHashMap.put("Cook", cook);
        mealHashMap.put("IsOffered", isOffered);

        return mealHashMap;

    }

    public String getName(){
        return this.name;
    }

    public String getType(){
        return this.type;
    }

    public String getCuisine(){
        return this.cuisine;
    }

    public List<String> getIngredients(){
        return this.ingredients;
    }

    public List<String> getAllergens(){ return this.allergens; }

    public String getPrice(){
        return this.price;
    }

    public String getDescription(){
        return this.description;
    }

    public String getCook(){
        return this.cook;
    }

    public boolean getIsOffered(){
        return this.isOffered;
    }

    public void setName(String newName){
        this.name = newName;
    }
}
