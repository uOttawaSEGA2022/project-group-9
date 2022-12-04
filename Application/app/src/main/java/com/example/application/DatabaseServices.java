package com.example.application;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import java.util.UUID;


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
    int totalNumOfStars;
    int numOfRatings;
    double rating;

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

        ref.child("isSuspended").setValue("true");
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
                        dataRef.child(registerInfo[i]).setValue(userInfo[i]);
                    }
                    if (ROLE.equals("Chef")){
                        dataRef.child("ratings").child("numOfRatings").setValue("0");
                        dataRef.child("ratings").child("totalNumOfStars").setValue("0");
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


    public void isSuspendedChef(Button goToMealsButton, Button goToOrdersButton, Button goToAcceptedOrdersButton, Button goToProfileButton,  TextView chefIsSuspendedTextView)
    {
        // Implement this method which gets called whenever the chef signs in successfully
        // The method needs to check if the chef is suspended
        // If the chef is suspended, go to another activity that shows they are suspended and return true (Using the context variable)
        // If the chef isn't suspended, return false

        boolean[] returnValue = new boolean[1];

        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("Chef").child(fAuth.getCurrentUser().getUid());

        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("isSuspended").getValue() != null){
                    if (dataSnapshot.child("isSuspended").getValue().toString().equals("false")) {
                        returnValue[0] = false;
                    } else {
                        returnValue[0] = true;
                    }

                    E2ChefLoggedInScreen e2ChefLoggedInScreen = new E2ChefLoggedInScreen();
                    e2ChefLoggedInScreen.checkIfSuspended(returnValue[0], goToMealsButton, goToOrdersButton, goToAcceptedOrdersButton, goToProfileButton, chefIsSuspendedTextView);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void displayChefMeals(LinearLayout allChefMeals, Context context, String allChefMealsOrModifying){
        // Implement this method which gets called when a chef goes to see their meals (menu, not offered)
        // This method fetches all the current chef's meals, which are in the database, under the current chef's section
        // It will fetch all the values of every meal, pack them into a HashMap and create a meal and add that meal to the list
        // For reference, check the chef "CaptianMK@gmail.com" in the database to see how the meals are supposed to be implemented and fetched
        // For more reference, check the Meal.java class to see what key value pairs should be in the HashMap

        allChefMeals.removeAllViews();

        DatabaseServices databaseServices = new DatabaseServices();

        LayoutInflater inflater = LayoutInflater.from(context);

        DatabaseReference databaseReference = database.getReference().child("Chef").child(fAuth.getCurrentUser().getUid()).child("meals");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    allChefMeals.removeAllViews();
                    for (DataSnapshot mealInDatabase : dataSnapshot.getChildren()){

                        HashMap<String, Object> mealInfo = new HashMap<>();

                        mealInfo.put("name", mealInDatabase.child("name").getValue());
                        mealInfo.put("type", mealInDatabase.child("type").getValue());
                        mealInfo.put("cuisine", mealInDatabase.child("cuisine").getValue());
                        mealInfo.put("price", mealInDatabase.child("price").getValue());
                        mealInfo.put("description", mealInDatabase.child("description").getValue());
                        mealInfo.put("cook", mealInDatabase.child("cook").getValue());
                        mealInfo.put("isOffered", mealInDatabase.child("isOffered").getValue());

                        Map<String, String> ingredientsHashMap= new HashMap<>();

                        for (DataSnapshot ingredientsDataSnapshot : mealInDatabase.child("ingredients").getChildren()){
                            ingredientsHashMap.put(String.valueOf(ingredientsHashMap.size()), (String) ingredientsDataSnapshot.getValue());
                        }

                        mealInfo.put("ingredients", ingredientsHashMap);

                        Map<String, String> allergensHashMap = new HashMap<>();

                        for (DataSnapshot allergensDataSnapshot : mealInDatabase.child("allergens").getChildren()){
                            allergensHashMap.put(String.valueOf(allergensHashMap.size()), (String) allergensDataSnapshot.getValue());
                        }

                        mealInfo.put("allergens", allergensHashMap);


                        Meal meal = new Meal(mealInfo, mealInDatabase.getKey());



                        if (allChefMealsOrModifying.equals("allChefMeals")){
                            String mealName = meal.getName();
                            String mealCuisine = meal.getCuisine();
                            String mealType = meal.getType();
                            String mealIsOffered = meal.getIsOffered();

                            View mealTemplate = inflater.inflate(R.layout.chef_meal_template, null);

                            TextView mealNameTextView = mealTemplate.findViewById(R.id.mealName);
                            TextView mealCuisineTextView = mealTemplate.findViewById(R.id.mealCuisine);
                            TextView mealTypeTextView = mealTemplate.findViewById(R.id.mealType);

                            mealNameTextView.setText(mealName);
                            mealCuisineTextView.setText(mealCuisine);
                            mealTypeTextView.setText(mealType);

                            if (mealIsOffered.equals("false")){
                                TextView mealIsOfferedTextView = mealTemplate.findViewById(R.id.mealIsOfferedTextView);
                                ImageView mealIsOfferedIcon = mealTemplate.findViewById(R.id.mealIsOffered);

                                mealIsOfferedTextView.setText("Not Offered");
                                mealIsOfferedIcon.setImageResource(R.drawable.not_offered_unchecked_mark);
                            }

                            mealTemplate.setClickable(true);
                            mealTemplate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent goToEditChefMeal = new Intent(context, AddOrEditChefMeal.class);
                                    goToEditChefMeal.putExtra("Meal", meal);
                                    goToEditChefMeal.putExtra("Editing or Adding", "Editing");
                                    context.startActivity(goToEditChefMeal);
                                }
                            });

                            mealTemplate.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View currentMealTemplateView) {
                                    addDeleteDialog(currentMealTemplateView, databaseServices, meal, context);
                                    return true;
                                }
                            });

                            allChefMeals.addView(mealTemplate);
                        }
                        else{
                            String mealName = meal.getName();
                            String mealCuisine = meal.getCuisine();
                            String mealType = meal.getType();
                            String mealIsOffered = meal.getIsOffered();

                            View mealTemplate = inflater.inflate(R.layout.chef_meal_template, null);

                            TextView mealNameTextView = mealTemplate.findViewById(R.id.mealName);
                            TextView mealCuisineTextView = mealTemplate.findViewById(R.id.mealCuisine);
                            TextView mealTypeTextView = mealTemplate.findViewById(R.id.mealType);

                            mealNameTextView.setText(mealName);
                            mealCuisineTextView.setText(mealCuisine);
                            mealTypeTextView.setText(mealType);

                            if (mealIsOffered.equals("false")) {
                                TextView mealIsOfferedTextView = mealTemplate.findViewById(R.id.mealIsOfferedTextView);
                                ImageView mealIsOfferedIcon = mealTemplate.findViewById(R.id.mealIsOffered);

                                mealIsOfferedTextView.setText("Not Offered");
                                mealIsOfferedIcon.setImageResource(R.drawable.not_offered_unchecked_mark);
                            }

                            mealTemplate.setClickable(true);
                            mealTemplate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO: Create a better fix than the one in the following line. This is a cheap barely working fix
                                    allChefMeals.removeAllViews();
                                    TextView mealIsOfferedTextView = mealTemplate.findViewById(R.id.mealIsOfferedTextView);
                                    ImageView mealIsOfferedIcon = mealTemplate.findViewById(R.id.mealIsOffered);
                                    String mealIsOffered = meal.getIsOffered();
                                    if (mealIsOffered.equals("true")){
                                        meal.setOffered("false");
                                        mealIsOfferedTextView.setText("Not Offered");
                                        mealIsOfferedIcon.setImageResource(R.drawable.not_offered_unchecked_mark);
                                    }
                                    else{
                                        meal.setOffered("true");
                                        mealIsOfferedTextView.setText("Offered");
                                        mealIsOfferedIcon.setImageResource(R.drawable.offered_checkmark);
                                    }

                                    // Editing the current meal in the database, but only changing the offered status
                                    // There's an alternative solution to this since realtime database may crash if the cook spams clicking
                                    // We can get the offered status of each meal when the finish button is clicked
                                    // The only problem is that the meal object is not related in any way to the UI elements
                                    // The UI elements have access to the meal object at creation only and the meal never has access to the UI elements
                                    // So getting meals out of the UI elements will be a really challenging task which will require heavy modification to the whole system
                                    databaseServices.updateOrAddChefMeal(meal);
                                }
                            });

                            allChefMeals.addView(mealTemplate);
                        }


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }

    public void addDeleteDialog(View currentMealTemplateView, DatabaseServices databaseServices, Meal meal, Context context){
        TextView isOfferedMealTextView = currentMealTemplateView.findViewById(R.id.mealIsOfferedTextView);
        TextView mealNameTextView = currentMealTemplateView.findViewById(R.id.mealName);
        if (!String.valueOf(isOfferedMealTextView.getText()).equals("Offered")){
            LayoutInflater inflater = LayoutInflater.from(context);
            View popupView = inflater.inflate(R.layout.delete_meal_popup_window, null);

            final AlertDialog alertDialog = new AlertDialog.Builder(context, com.google.android.material.R.style.Base_Theme_AppCompat_Dialog_Alert).create();
            alertDialog.setView(popupView);

            TextView dialogTitleTextView = popupView.findViewById(R.id.deleteMealPopupTitle);
            dialogTitleTextView.setText("Are you sure you want to remove " + String.valueOf(mealNameTextView.getText()) + " from your menu?");

            Button popupCancelButton = popupView.findViewById(R.id.deleteMealCancelBtn);
            Button popupDeleteButton = popupView.findViewById(R.id.deleteMealDelteBtn);

            popupCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            popupDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout allChefMeals = (LinearLayout) currentMealTemplateView.getParent();
                    allChefMeals.removeAllViews();

                    // This is where the meal gets deleted from the database
                    // You can have different approaches to this problem:
                    // 1: You can unpack the meal object into the different strings and lists it contains
                    // Then you can search for the element in the database that matches that meal and delete that element from the db
                    // 2: A different approach (I'm not sure it works, I'll explain why in a bit) is to get all the meals that are currently in the linear layout
                    // Unpack them all and override the whole meal section of the database
                    // This may not work since, in this code block, the methods only see one meal at a time, notice how all of this is in a for loop
                    // So they may not see the rest of the meal templates that are in the linear layout with the current meal template
                    // And this approach is also not efficient, but may work, so do with it as you wish
                    databaseServices.removeMeal(meal);

                    alertDialog.dismiss();
                }
            });

            alertDialog.show();
            alertDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        else{
            Toast.makeText(context, "This meal is currently offered, cannot remove from menu", Toast.LENGTH_LONG).show();
        }
    }

    public void updateOrAddChefMeal(Meal meal) {
        // Implement this which gets called after the cook finishes adding or updating one of his meals on the menu

        // This method is also called when the chef changes the offered status of a meal, it's called as "Editing"
        // So if implemented correctly, it should support that as well


        DatabaseReference databaseReference = database.getReference().child("Chef").child(fAuth.getCurrentUser().getUid()).child("meals");

        databaseReference.child(meal.getID()).setValue(meal.toHashMap());

    }

    public String getCurrentChef(){
        // Implement this method which gets called when a cook finishes ADDING a new meal and its cook is yet unknown

        return fAuth.getCurrentUser().getUid();
    }

    public String getCurrentCustomer(){
        return fAuth.getCurrentUser().getUid();
    }

    public void removeMeal(Meal meal){
        // Implement this method which gets called when a cook deletes a meal from his menu
        // It needs to go to the current chef in the realtime database, then delete the meal that matches the meal deleted from the menu locally
        DatabaseReference databaseReference = database.getReference().child("Chef").child(fAuth.getCurrentUser().getUid()).child("meals");
        databaseReference.child(meal.getID()).removeValue();
    }


    public void viewSpecifiedMeals(String searchQuery, TextView searchResultsTextView, LayoutInflater inflater, LinearLayout mealSearchResultsLinearLayout, Context context){
        // This method gets called when a customer presses the search icon
        // We have access to the search text he inputted in the search bar, given as a parameter searchQuery
        // This method should go through all chefs, look into their meals, check the meal name and see if it's close to the searchQuery by using the method startsWith()
        // Perform String cleaning and handling such as forcing the searchQuery and the meal name we're checking to be lowercase and strip them from spaces on the side
        // If a certain meal corresponds to the search query, add it to the list of meals, then return that list

        List<Meal> mealList = new ArrayList<>();

        DatabaseReference databaseReference = database.getReference().child("Chef");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot chefInDatabase: dataSnapshot.getChildren()){
                    DataSnapshot chefMeals = chefInDatabase.child("meals");
                    String suspendedStatus = String.valueOf(chefInDatabase.child("isSuspended").getValue());
                    if (chefMeals != null && suspendedStatus.equals("false")){
                        String mealCook = (String) chefInDatabase.getKey();
                        for (DataSnapshot currentChefMeal: chefMeals.getChildren()){
                            String mealName = (String) currentChefMeal.child("name").getValue();
                            String mealType = (String) currentChefMeal.child("type").getValue();
                            String mealCuisine = (String) currentChefMeal.child("cuisine").getValue();
                            String mealIsOffered = (String) currentChefMeal.child("isOffered").getValue();

                            if ((mealName.strip().toLowerCase().startsWith(searchQuery) ||
                                    mealType.strip().toLowerCase().startsWith(searchQuery) ||
                                    mealCuisine.strip().toLowerCase().startsWith(searchQuery)) &&
                                    mealIsOffered.strip().equalsIgnoreCase("true")){
                                HashMap<String, Object> mealInfo = new HashMap<>();
                                String mealID = currentChefMeal.getKey();
                                String mealPrice = (String) currentChefMeal.child("price").getValue();
                                String mealDescription = (String) currentChefMeal.child("description").getValue();

                                HashMap<String, String> ingredientsHashMap = new HashMap<>();
                                int count = 0;
                                for (DataSnapshot currentIngredient: currentChefMeal.child("ingredients").getChildren()){
                                    ingredientsHashMap.put(String.valueOf(count), (String) currentIngredient.getValue());
                                    count++;
                                }

                                HashMap<String, String> allergensHashMap = new HashMap<>();
                                count = 0;
                                for (DataSnapshot currentAllergen: currentChefMeal.child("allergens").getChildren()){
                                    allergensHashMap.put(String.valueOf(count), (String) currentAllergen.getValue());
                                    count++;
                                }

                                mealInfo.put("name", mealName);
                                mealInfo.put("type", mealType);
                                mealInfo.put("cuisine", mealCuisine);
                                mealInfo.put("price", mealPrice);
                                mealInfo.put("description", mealDescription);
                                mealInfo.put("ingredients", ingredientsHashMap);
                                mealInfo.put("allergens", allergensHashMap);
                                mealInfo.put("isOffered", mealIsOffered);
                                mealInfo.put("cook", mealCook);

                                Meal currentMeal = new Meal(mealInfo, mealID);

                                mealList.add(currentMeal);
                            }
                        }
                    }
                }

                CustomerSearchForMealsScreen customerSearchForMealsScreen = new CustomerSearchForMealsScreen();
                customerSearchForMealsScreen.displaySearchResults(mealList, searchResultsTextView, inflater, mealSearchResultsLinearLayout, context);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void submitRatingToChef(String chefID, String chefRating, String orderID) {
        DatabaseServices dbServices = new DatabaseServices();
        DatabaseReference databaseReference = database.getReference().child("Customer").child(getCurrentCustomer()).child("orderHistory").child(orderID).child("hasRated");
        databaseReference.setValue("true");

        databaseReference = database.getReference().child("Chef").child(chefID).child("ratings");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String currentNumOfRatings = dataSnapshot.child("numOfRatings").getValue(String.class);
                String currentNumOfStars = dataSnapshot.child("totalNumOfStars").getValue(String.class);

                if (currentNumOfRatings == null) {
                    setRatingsNew(chefRating, chefID);
                }
                else {
                    setRatingsOld(currentNumOfRatings, currentNumOfStars, chefRating, chefID);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Unable to submit a rating!");
            }
        });

    }

    public void setRatingsNew(String chefRating, String chefID) {
        DatabaseReference databaseReference = database.getReference().child("Chef").child(chefID).child("ratings");
        databaseReference.child("numOfRatings").setValue("1");
        databaseReference.child("totalNumOfStars").setValue(chefRating);
    }

    public void setRatingsOld(String currentNumOfRatings, String currentNumOfStars, String chefRating, String chefID) {
        DatabaseReference databaseReference = database.getReference().child("Chef").child(chefID).child("ratings");

        double arthRatings = Double.parseDouble(currentNumOfRatings) + 1;
        double arthStars = Double.parseDouble(currentNumOfStars) + Double.parseDouble(chefRating);

        String ratingToAdd = String.valueOf(arthRatings);
        String starsToAdd = String.valueOf(arthStars);

        databaseReference.child("numOfRatings").setValue(ratingToAdd);
        databaseReference.child("totalNumOfStars").setValue(starsToAdd);
    }

    public void submitComplaint(String chefID, String complaint, String orderID) {
        DatabaseServices dbServices = new DatabaseServices();
        String complaintID = UUID.randomUUID().toString();

        DatabaseReference databaseReference = database.getReference().child("Complaints");

        databaseReference.child(complaintID).child("chefID").setValue(chefID);
        databaseReference.child(complaintID).child("reason").setValue(complaint);

        databaseReference = database.getReference().child("Customer").child(getCurrentCustomer()).child("orderHistory").child(orderID).child("hasComplaint");
        databaseReference.setValue("true");
    }

    public void viewCustomerOrders(LayoutInflater inflater, LinearLayout mealSearchResultsLinearLayout, Context context) {
        DatabaseServices dbServices = new DatabaseServices();
        DatabaseReference databaseReference = database.getReference().child("Customer").child(getCurrentCustomer()).child("orderHistory");

        List<customerOrder> allOrders = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot orderInDatabase : dataSnapshot.getChildren()) {
                    HashMap<String, Object> orderInfo = new HashMap<>();

                    String mealID = (String) orderInDatabase.child("mealID").getValue();
                    String chefID = (String) orderInDatabase.child("chefID").getValue();

                    if (mealID != null && chefID != null){
                        database.getReference().child("Chef").child(chefID).child("meals").child(mealID).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String mealName = (String) dataSnapshot.getValue();

                                orderInfo.put("chefID", orderInDatabase.child("chefID").getValue());
                                orderInfo.put("mealName",  mealName);
                                orderInfo.put("quantity", orderInDatabase.child("quantity").getValue());
                                orderInfo.put("hasRated",  orderInDatabase.child("hasRated").getValue());
                                orderInfo.put("hasComplaint", orderInDatabase.child("hasComplaint").getValue());
                                orderInfo.put("status", orderInDatabase.child("status").getValue());

                                customerOrder orders = new customerOrder(orderInfo, orderInDatabase.getKey());

                                allOrders.add(orders);

                                CustomerOrderHistoryScreen customerOrderHistoryScreen = new CustomerOrderHistoryScreen();
                                customerOrderHistoryScreen.displayOrders(allOrders, inflater, mealSearchResultsLinearLayout, context);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Failure in Viewing Orders.");
            }
        });
    }

    //Return type to TBH
    public void viewAllMealInfo (String chefID, String mealName) {
        DatabaseReference databaseReference = database.getReference().child("Chef");

    }

    //Return type to TBH
    public void placeOrder(Meal meal, int quantity) {
        //untested code
        String chefId;
        double price;
        chefId=meal.getCook();
        price=Double.parseDouble(meal.getPrice());
        String orderId;
        orderId= UUID.randomUUID().toString();
        DatabaseReference chefReference = database.getReference().child("Chef").child(chefId);
        // This method is called after the customer has chosen a meal and ordered it with a set quantity
        // This method should place this order in the chef's section for him to approve or decline
        // Check db hierarchy document to see how the order should be placed in the db under the chef's section
        // Check the meal object to know how to generate the orderID
        DatabaseReference customerReference = database.getReference().child("Customer").child(fAuth.getCurrentUser().getUid());
        chefReference.child("orders").child(orderId).child("customerID").setValue(customerReference.getKey());
        chefReference.child("orders").child(orderId).child("priceOfOrder").setValue(Double.toString(price * quantity));
        chefReference.child("orders").child(orderId).child("mealID").setValue(meal.getID());
        chefReference.child("orders").child(orderId).child("quantity").setValue(Integer.toString(quantity));
        chefReference.child("orders").child(orderId).child("status").setValue("pending");
        customerReference.child("orderHistory").child(orderId).child("chefID").setValue(chefId);
        customerReference.child("orderHistory").child(orderId).child("mealID").setValue(meal.getID());
        customerReference.child("orderHistory").child(orderId).child("quantity").setValue(Integer.toString(quantity));
        customerReference.child("orderHistory").child(orderId).child("status").setValue("pending");
        customerReference.child("orderHistory").child(orderId).child("hasRated").setValue("false");
        customerReference.child("orderHistory").child(orderId).child("hasComplaint").setValue("false");
    }










    //Return type to TBH
    public HashMap<String, Meal> viewChefOrders(String chefID) {
        //untested code
        DatabaseReference databaseReference = database.getReference().child("Chef").child(chefID);
        HashMap<String,Meal> chefOrdersHashMap = new HashMap<String, Meal>();
        databaseReference.child("orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot p: dataSnapshot.getChildren())
                {
                    chefOrdersHashMap.put(p.getKey(),(Meal)(p.child("mealID").getValue()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return chefOrdersHashMap;

    }


    public void chefDeclinedOrder(String orderID, String customerID) {
        //Finished
        DatabaseReference chefDatabaseReference = database.getReference().child("Chef").child(fAuth.getCurrentUser().getUid()).child("orders").child(orderID);
        chefDatabaseReference.child("status").setValue("rejected");
        DatabaseReference customerDatabaseReference = database.getReference().child("Customer").child(customerID).child("orderHistory").child(orderID);
        customerDatabaseReference.child("status").setValue("rejected");

    }

    public void chefApprovedOrder(String orderID, String customerID) {
        //Finished
        DatabaseReference databaseReferenceToUpdateChefSideOrder = database.getReference().child("Chef").child(fAuth.getCurrentUser().getUid()).child("orders").child(orderID).child("status");
        databaseReferenceToUpdateChefSideOrder.setValue("accepted");


        DatabaseReference databaseReferenceToUpdateCustomerSideOrder = database.getReference().child("Customer").child(customerID).child("orderHistory").child(orderID).child("status");
        databaseReferenceToUpdateCustomerSideOrder.setValue("accepted");

    }

    /*
    Create Database Reference and move to the chefID reference using the parameter
    Do not Use the Query Thing
    Then go to the child reference of ratings
    Then check the numOfRatings child under ratings (create local variable Var Int chefNumOfRatings)

    Then go the to child reference - listOfRatings
    Then create a var which stores all the values from the ratings (5+4+3+2+3+4+5) = 26 as an Int (chefTotalPoints)
    Then chefTotalPoints / chefNumOfRatings return the value
     */
    public double getStarRating(String chefID) {
        //untested code
        rating=0.0;
        DatabaseReference databaseReference = database.getReference().child("Chef").child(fAuth.getCurrentUser().getUid());
        databaseReference.child("ratings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 1;
                for (DataSnapshot p : dataSnapshot.getChildren()) {
                    if (i == 1) {
                        numOfRatings = Integer.parseInt(dataSnapshot.getValue().toString());
                    } else if (i == 2) {
                        totalNumOfStars = Integer.parseInt(dataSnapshot.getValue().toString());
                    }
                    i++;
                }
                if (numOfRatings!=0)
                {
                    rating = (double)totalNumOfStars/numOfRatings;
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rating;
    }

    public void getChefProfileInfo (TextView chefNameTextView,
                                    TextView chefEmailTextView, TextView chefRatingTextView,
                                    TextView chefDescriptionTextView) {

        DatabaseReference databaseReference = database.getReference().child("Chef").child(fAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot chefInfo) {
                String chefFirstName = (String) chefInfo.child("firstname").getValue();
                String chefLastName = (String) chefInfo.child("lastname").getValue();
                String chefName = chefFirstName + " " +chefLastName;

                String chefEmail = (String) chefInfo.child("email").getValue();

                String chefDescription = (String) chefInfo.child("shortdesc").getValue();

                double chefNumberOfRatings = Double.parseDouble(String.valueOf(chefInfo.child("ratings").child("numOfRatings").getValue()));
                double chefTotalNumberOfStars = Double.parseDouble(String.valueOf(chefInfo.child("ratings").child("totalNumOfStars").getValue()));

                double doubleChefRating = 0;
                if (chefNumberOfRatings != 0){
                    doubleChefRating = (double) chefTotalNumberOfStars / chefNumberOfRatings;
                }


                String chefRating = String.valueOf(doubleChefRating);

                ChefProfileScreen chefProfile = new ChefProfileScreen();
                chefProfile.displayChefInfo(chefNameTextView, chefEmailTextView,
                        chefRatingTextView, chefDescriptionTextView,
                        chefName, chefEmail, chefRating, chefDescription);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void getChefInfoForSelectedMeal(String chefID, TextView chefNameTextView, TextView chefRatingTextView,
                                           TextView chefDescriptionTextView){

        DatabaseReference databaseReference = database.getReference().child("Chef").child(chefID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot chefInfo) {
                String chefFirstName = (String) chefInfo.child("firstname").getValue();
                String chefLastName = (String) chefInfo.child("lastname").getValue();
                String chefName = chefFirstName + " " + chefLastName;


                String chefDescription = (String) chefInfo.child("shortdesc").getValue();

                double chefNumberOfRatings = Double.parseDouble(String.valueOf(chefInfo.child("ratings").child("numOfRatings").getValue()));
                double chefTotalNumberOfStars = Double.parseDouble(String.valueOf(chefInfo.child("ratings").child("totalNumOfStars").getValue()));

                double doubleChefRating = 0;
                if (chefNumberOfRatings != 0){
                    doubleChefRating = (double) chefTotalNumberOfStars / chefNumberOfRatings;

                }

                String chefRating = String.valueOf(doubleChefRating);

                CustomerViewMealScreen customerViewMealScreen = new CustomerViewMealScreen();
                customerViewMealScreen.displayChefInfoForSelectedMeal(chefNameTextView,
                        chefRatingTextView, chefDescriptionTextView,
                        chefName, chefRating, chefDescription);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public void getChefOrders(LayoutInflater inflater, LinearLayout ordersLinearLayout, String acceptedOrAllIndicator, Context context){
        DatabaseReference chefDatabaseReference = database.getReference().child("Chef").child(fAuth.getCurrentUser().getUid()).child("orders");

        chefDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ChefOrder> orderList = new ArrayList<>();
                for (DataSnapshot orderIDInDatabase: dataSnapshot.getChildren()){
                    HashMap<String, Object> orderInfo = new HashMap<>();
                    orderInfo.put("orderID", orderIDInDatabase.getKey());
                    orderInfo.put("priceOfOrder", orderIDInDatabase.child("priceOfOrder").getValue());
                    orderInfo.put("status", orderIDInDatabase.child("status").getValue());
                    orderInfo.put("customerID", orderIDInDatabase.child("customerID").getValue());
                    orderInfo.put("quantity", orderIDInDatabase.child("quantity").getValue());

                    String mealID = (String) orderIDInDatabase.child("mealID").getValue();

                    DatabaseReference mealReference = database.getReference().child("Chef").child(fAuth.getCurrentUser().getUid()).child("meals").child(mealID);
                    mealReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot mealInDatabase) {
                            HashMap<String, Object> mealInfo = new HashMap<>();
                            mealInfo.put("name", mealInDatabase.child("name").getValue());
                            mealInfo.put("type", mealInDatabase.child("type").getValue());
                            mealInfo.put("cuisine", mealInDatabase.child("cuisine").getValue());
                            mealInfo.put("price", mealInDatabase.child("price").getValue());
                            mealInfo.put("description", mealInDatabase.child("description").getValue());

                            HashMap<String, String> ingredientsHashMap= new HashMap<>();

                            for (DataSnapshot ingredientsDataSnapshot : mealInDatabase.child("ingredients").getChildren()){
                                ingredientsHashMap.put(String.valueOf(ingredientsHashMap.size()), (String) ingredientsDataSnapshot.getValue());
                            }

                            mealInfo.put("ingredients", ingredientsHashMap);

                            Map<String, String> allergensHashMap = new HashMap<>();

                            for (DataSnapshot allergenDataSnapshot : mealInDatabase.child("allergens").getChildren()){
                                allergensHashMap.put(String.valueOf(allergensHashMap.size()), (String) allergenDataSnapshot.getValue());
                            }

                            mealInfo.put("allergens", allergensHashMap);

                            Log.d("HelloThereBro", orderInfo.toString());
                            Log.d("HelloThereBro", mealInfo.toString());

                            ChefOrder chefOrder = new ChefOrder(orderInfo, new Meal(mealInfo));

                            DatabaseReference customerDatabaseReference = database.getReference().child("Customer").child(chefOrder.getCustomerID());

                            customerDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String firstName = (String) dataSnapshot.child("first_name").getValue();
                                    String lastName = (String) dataSnapshot.child("last_name").getValue();
                                    String fullName = firstName + " " + lastName;
                                    orderList.add(chefOrder);

                                    if (acceptedOrAllIndicator.equals("accepted")){
                                        ChefViewAcceptedOrdersScreen acceptedChefOrders = new ChefViewAcceptedOrdersScreen();
                                        acceptedChefOrders.displayAcceptedOrders(orderList, inflater, ordersLinearLayout, fullName);
                                    }
                                    else{
                                        ChefViewOrdersScreen chefViewOrderScreen = new ChefViewOrdersScreen();
                                        chefViewOrderScreen.displayChefOrders(orderList, inflater, ordersLinearLayout, fullName, context);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }





}
