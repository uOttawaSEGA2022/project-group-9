package com.example.application;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Meal implements Serializable {
    private final String ID;
    private String name;
    private String type;
    private String cuisine;
    private Map<String, String> ingredients;
    private Map<String, String> allergens;
    private String price;
    private String description;
    private String cook;
    private String isOffered;

    @SuppressWarnings({"All"})
    public Meal(HashMap<String, Object> mealInfo){
        this.ID = UUID.randomUUID().toString();
        this.name = (String) mealInfo.get("name");
        this.type = (String) mealInfo.get("type");
        this.cuisine = (String) mealInfo.get("cuisine");
        this.ingredients = (Map<String, String>) mealInfo.get("ingredients");
        this.allergens = (Map<String, String>) mealInfo.get("allergens");
        this.price = (String) mealInfo.get("price");
        this.description = (String) mealInfo.get("description");
        this.cook = (String) mealInfo.get("cook");
        this.isOffered = (String) mealInfo.get("isOffered");

    }

    @SuppressWarnings({"All"})
    public Meal(HashMap<String, Object> mealInfo, String ID){
        this.ID = ID;
        this.name = (String) mealInfo.get("name");
        this.type = (String) mealInfo.get("type");
        this.cuisine = (String) mealInfo.get("cuisine");
        this.ingredients = (Map<String, String>) mealInfo.get("ingredients");
        this.allergens = (Map<String, String>) mealInfo.get("allergens");
        this.price = (String) mealInfo.get("price");
        this.description = (String) mealInfo.get("description");
        this.cook = (String) mealInfo.get("cook");
        this.isOffered = (String) mealInfo.get("isOffered");

    }

    public HashMap<String, Object> toHashMap(){
        HashMap<String, Object> mealHashMap = new HashMap<String, Object>();
        mealHashMap.put("name", name);
        mealHashMap.put("type", type);
        mealHashMap.put("cuisine", cuisine);
        mealHashMap.put("ingredients", ingredients);
        mealHashMap.put("allergens", allergens);
        mealHashMap.put("price", price);
        mealHashMap.put("description", description);
        mealHashMap.put("cook", cook);
        mealHashMap.put("isOffered", isOffered);

        return mealHashMap;

    }

    public String getID(){
        return this.ID;
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

    public Map<String, String> getIngredients(){
        return this.ingredients;
    }

    public Map<String, String> getAllergens(){ return this.allergens; }

    public String getPrice(){
        return this.price;
    }

    public String getDescription(){
        return this.description;
    }

    public String getCook(){
        return this.cook;
    }

    public String getIsOffered(){
        return this.isOffered;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setType(String newType){
        this.type = newType;
    }

    public void setCuisine(String newCuisine){
        this.cuisine = newCuisine;
    }

    public void setIngredients(Map<String, String> newIngredients){
        this.ingredients = newIngredients;
    }

    public void setAllergens(Map<String, String>newAllergens){
        this.allergens = newAllergens;
    }

    public void setPrice(String newPrice){
        this.price = newPrice;
    }

    public void setDescription(String newDescription){
        this.description = newDescription;
    }

    public void setCook(String newCook){
        this.cook = newCook;
    }

    public void setOffered(String newIsOffered){
        this.isOffered = newIsOffered;
    }
}
