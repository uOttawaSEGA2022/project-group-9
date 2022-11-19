package com.example.application;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DatabaseServices extends MainActivity {

    FirebaseAuth fAuth;
    FirebaseDatabase database;
    String[] chefRegisterInfo;
    String[] customerRegisterInfo;

    //So Admin can view complaints
    static String[] tempListOfChefIDs;
    static String[] tempListOfReasons;
    ArrayList<Complaint> listOfComplaints;
    Integer numOfComplaints = 0;

    public DatabaseServices(){
        FirebaseApp.initializeApp(this);

        this.chefRegisterInfo = new String[] {"role", "firstname", "lastname", "email", "password", "addressline1", "addressline2", "city", "province", "postalcode", "shortdesc", "voidcheque", "isSuspended", "Suspended until"};
        this.customerRegisterInfo = new String[] {"role", "first_name", "last_name", "email", "password",
                "addressline1", "addressline2", "city", "province", "postalcode",
                "nameoncard", "creditcardnumber", "cvvnumber", "expirationdate",
                "addressline1", "addressline2", "city", "province", "postalcode"};

        this.fAuth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance("https://application-67368-default-rtdb.firebaseio.com/");

//        //WORK IN PROGRESS BY YASH!
//        //Allows admin to edit list
//        Intent allowAdminEditList = new Intent(this, AdminActionComplaint.class);
//        allowAdminEditList.putExtra("chefList", tempListOfChefIDs);
//        allowAdminEditList.putExtra("reasonList", tempListOfReasons);
//        this.startActivity(allowAdminEditList);
    }

    public static void updateList(String removal) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query reasonQuery = ref.child("Complaints").orderByChild("reason").equalTo(removal);

        reasonQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot reasonSnapshot : dataSnapshot.getChildren()) {
                    reasonSnapshot.getRef().removeValue();
                }

                System.out.println("Removed correctly");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Did not remove reason correctly");
            }
        });
    }

    public static void updateChefStatus(String chefID, String date) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Chef").child(chefID);

        ref.child("isSuspensed").setValue("true");
        ref.child("Suspended until").setValue(date);
    }



//    public static void updateReasonList(String[] newList) {
//        System.out.println("AFTER UPDATE FOR REASONS");
//        System.out.println(Arrays.toString(newList));
//        tempListOfReasons = newList;
//    }
//
//    public static void updateChefList(String[] newList) {
//        System.out.println("AFTER UPDATE FOR CHEFS");
//        System.out.println(Arrays.toString(newList));
//        tempListOfChefIDs = newList;
//    }

    public void createUser(MainActivity activity, String[] userInfo, String ROLE){
        String[] registerInfo;
        if (ROLE.equals("Chef")){
            registerInfo = chefRegisterInfo;
        }
        else{
            registerInfo = customerRegisterInfo;
        }
        fAuth.createUserWithEmailAndPassword(userInfo[3], userInfo[4]).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    DatabaseReference dataRef = database.getReference(ROLE).child(fAuth.getCurrentUser().getUid());

                    for (int i=0; i<userInfo.length; i++) {
                        if(i == 0) continue;
                        dataRef.child(registerInfo[i]).setValue(userInfo[i]);
                    }

                    Toast.makeText(activity, "sign up successfull!", Toast.LENGTH_SHORT).show();
                } else {
                    if (task.getException().getMessage() != null){
                        Toast.makeText(activity, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void signInUser(Context context, MainActivity activity, String email, String password, String ROLE){
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (ROLE.equals("Customer")) {
                        Intent E1CustomerLoggedInScreen = new Intent(context, E1CustomerLoggedInScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); ;
                        //E1CustomerLoggedInScreen.putExtra("Email", emailText.getText().toString());
                        context.startActivity(E1CustomerLoggedInScreen);
                    } else if (ROLE.equals("Chef")) {

                        Intent E2ChefLoggedInScreen = new Intent(context, E2ChefLoggedInScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); ;
                        context.startActivity(E2ChefLoggedInScreen);

                    } else {
                        Intent E3AdminLoggedInScreen = new Intent(context, E3AdminLoggedInScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); ;
                        //E3AdminLoggedInScreen.putExtra("Email", emailText.getText().toString());
                        context.startActivity(E3AdminLoggedInScreen);
                    }
                } else {

                    Toast.makeText(activity, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void displayComplaintsForAdmin(Context context, ListView listViewComplaints){
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("Complaints");

        //Only 10 complaints can be handled rn, maybe this can turn into a scrolly bar
        tempListOfChefIDs = new String[10];
        tempListOfReasons = new String[10];
        numOfComplaints = 0;
        listOfComplaints = new ArrayList<Complaint>();
        //final int[] test = {numOfComplaints};

        dataRef.addValueEventListener(new ValueEventListener() {

            @Override
            //On start up despite the name
            public void onDataChange(DataSnapshot dataSnapShot) {

                tempListOfChefIDs = new String[10];
                tempListOfReasons = new String[10];
                numOfComplaints = 0;
                //final int[] test = {numOfComplaints};

                //for every complaint entry on firebase
                for (DataSnapshot postSnapshot : dataSnapShot.getChildren()) {
                    //This complaint code does nothing rn but will be reworked for deliverable 3
                    Complaint newComplaint = new Complaint();
                    newComplaint.setChefID((String) postSnapshot.child("chefID").getValue());
                    newComplaint.setReason((String) postSnapshot.child("reason").getValue());

                    listOfComplaints.add(newComplaint);

                    //places data in an array
                    tempListOfChefIDs[numOfComplaints] = (String) postSnapshot.child("chefID").getValue();
                    tempListOfReasons[numOfComplaints] =(String) postSnapshot.child("reason").getValue();
                    numOfComplaints++;

                    Log.i("MSG", (String) postSnapshot.child("chefID").getValue());

                    Log.i("MSG", (String) postSnapshot.child("reason").getValue());
                }


                //uses non-Complaint Object Data from array to display //un comment this to get everything to work in an non-clean, working method

                CustomBaseAdapterSimple customBaseAdapterSimple = new CustomBaseAdapterSimple(context ,tempListOfChefIDs,tempListOfReasons);
                listViewComplaints.setAdapter(customBaseAdapterSimple);


                //Uses Complain Object Data Use this for a clean- semi working version
                /*
                CustomBaseAdapterClass customBaseAdapterClassComplaint = new CustomBaseAdapterClass(context ,listOfComplaints);
                listViewComplaints.setAdapter(customBaseAdapterClassComplaint);
                */
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public boolean isSuspendedChef(String email)
    {
        // Implement this method which gets called whenever the chef signs in successfully
        // The method needs to check if the chef is suspended
        // If the chef is suspended, go to another activity that shows they are suspended and return true (Using the context variable)
        // If the chef isn't suspended, return false

        boolean[] returnValue = new boolean[1];

        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("Chef");

        Log.i("ErrorTesting","method called");

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot chefID : dataSnapshot.getChildren()) {

                    if (chefID.child("email").getValue().toString().equals(email)) {
                        if (chefID.child("isSuspended").getValue().toString().equals("false")) {
                            returnValue[0] = false;
                        } else {
                            returnValue[0] = true;
                        }

                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        Log.i("ErrorTesting", String.valueOf(returnValue[0]));



        return returnValue[0];

    }

    public List<Meal> getCurrentChefMeals(){
        // Implement this method which gets called when a chef goes to see their meals (menu, not offered)
        // This method fetches all the current chef's meals, which are in the database, under the current chef's section
        // It will fetch all the values of every meal, pack them into a HashMap and create a meal and add that meal to the list
        // For reference, check the chef "CaptianMK@gmail.com" in the database to see how the meals are supposed to be implemented and fetched
        // For more reference, check the Meal.java class to see what key value pairs should be in the HashMap

        List<Meal> mealList = new ArrayList<>();

        DatabaseReference databaseReference = database.getReference().child("Chef").child(fAuth.getCurrentUser().getUid()).child("meals");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot mealInDatabase : dataSnapshot.getChildren()){
                    HashMap<String, Object> mealInfo = new HashMap<>();

                    mealInfo.put("Name", mealInDatabase.child("name").getValue());
                    mealInfo.put("Type", mealInDatabase.child("type").getValue());
                    mealInfo.put("Cuisine", mealInDatabase.child("cuisine").getValue());
                    mealInfo.put("Price", mealInDatabase.child("price").getValue());
                    mealInfo.put("Description", mealInDatabase.child("description").getValue());
                    mealInfo.put("Cook", mealInDatabase.child("cook").getValue());
                    mealInfo.put("IsOffered", mealInDatabase.child("isOffered").getValue());

                    List<String> ingredientsArrayList = new ArrayList<>();

                    for (DataSnapshot ingredientsDataSnapshot : mealInDatabase.child("ingredients").getChildren()){
                        ingredientsArrayList.add((String) ingredientsDataSnapshot.getValue());
                    }

                    mealInfo.put("Ingredients", ingredientsArrayList);

                    List<String> allergensArrayList = new ArrayList<>();

                    for (DataSnapshot allergensDataSnapshot : mealInDatabase.child("allergens").getChildren()){
                        ingredientsArrayList.add((String) allergensDataSnapshot.getValue());
                    }

                    mealInfo.put("Allergens", allergensArrayList);


                    Meal currentMeal = new Meal(mealInfo);

                    mealList.add(currentMeal);




                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // The following is for testing purposes, delete when implementing the main functionality

//        HashMap<String, Object> mealInfo = new HashMap<>();
//
//        List<String> ingredients = new ArrayList<>();
//        ingredients.add("Bun");
//        ingredients.add("Beef Patty");
//
//        List<String> allergens = new ArrayList<>();
//        allergens.add("Gluten");
//
//        mealInfo.put("Name", "burger");
//        mealInfo.put("Type", "main dish");
//        mealInfo.put("Cuisine", "american");
//        mealInfo.put("Ingredients", ingredients);
//        mealInfo.put("Allergens", allergens);
//        mealInfo.put("Price", "6");
//        mealInfo.put("Description", "This is a burger");
//        mealInfo.put("Cook", "Masterchef");
//        mealInfo.put("IsOffered", true);
//
//        Meal test = new Meal(mealInfo);
//
//        mealInfo.put("Name", "Pizza");
//        mealInfo.put("IsOffered", false);
//        Meal test2 = new Meal(mealInfo);
//
//        mealList.add(test);
//        mealList.add(test2);

        Log.d("HelloThereBro", mealList.toString());
        return mealList;
    }

    public void updateOrAddChefMeal(Meal meal, String editingOrAddingMeal) {
        // Implement this which gets called after the cook finishes adding or updating one of his meals on the menu
        // It's very important to be able to differentiate between updating an existing meal or adding a new one, using the given argument

        // This method is also called when the chef changes the offered status of a meal, it's called as "Editing"
        // So if implemented correctly, it should support that as well

        DatabaseReference databaseReference = database.getReference().child("Chef").child(fAuth.getCurrentUser().getUid()).child("meals");

        databaseReference.child(meal.name).child("name").setValue(meal.name);
        databaseReference.child(meal.name).child("type").setValue(meal.getType());
        databaseReference.child(meal.name).child("cuisine").setValue(meal.getCuisine());
        databaseReference.child(meal.name).child("ingredients").setValue(meal.ingredients);
        databaseReference.child(meal.name).child("allergens").setValue(meal.allergens);
        databaseReference.child(meal.name).child("price").setValue(meal.getPrice());
        databaseReference.child(meal.name).child("description").setValue(meal.getDescription());
        databaseReference.child(meal.name).child("cook").setValue(meal.getCook());
        databaseReference.child(meal.name).child("isOffered").setValue(meal.isOffered);

    }

    public String getCurrentChef(){
        // Implement this method which gets called when a cook finishes ADDING a new meal and its cook is yet unknown

        return fAuth.getCurrentUser().getUid();
    }

    public void removeMeal(Meal meal){
        // Implement this method which gets called when a cook deletes a meal from his menu
        // It needs to go to the current chef in the realtime database, then delete the meal that matches the meal deleted from the menu locally
        DatabaseReference databaseReference = database.getReference().child("Chef").child(fAuth.getCurrentUser().getUid()).child("meals");
        databaseReference.child(meal.name).removeValue();
    }
}
