package mgarzon.createbest.productcatalog;

import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import mgarzon.createbest.productcatalog.Product;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

//Adding Imports
import android.widget.Spinner;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextPrice;
    Button buttonAddProduct;
    ListView listViewProducts;

    public List<Product> products;
    int test;

    //Creating an instance of DatabaseReference so that we can read/write data from/to our data base
    DatabaseReference databaseProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        listViewProducts = (ListView) findViewById(R.id.listViewProducts);
        buttonAddProduct = (Button) findViewById(R.id.addButton);

        products = new ArrayList<>();
        test = 5;
        
        //Read/write data from/to our data base
        databaseProducts = FirebaseDatabase.getInstance().getReference("products");

        //adding an onclicklistener to button
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });

        //Adding / Removing a Product
        listViewProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = products.get(i);
                test+= 5;
                showUpdateDeleteDialog(product.getId(), product.getProductName());
                return true;
            }
        });
    }


    /*
    This method is for reading data ata the database and listens for changes to the database
    by adding a ValueEventLister to the DatabaseReference
     */
    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener

        //We need an event listener
        databaseProducts.addValueEventListener(new ValueEventListener() {

            @Override
                    //When data changes
            public void onDataChange(DataSnapshot dataSnapShot) {
                //Clearing the previous artist list
                products.clear();

                //We thought it was a scope issue
                //We thought it was a scope issue
                // The issues is placing is an elephant in a car (Wrong item in a slot)
                for (DataSnapshot postSnapshot : dataSnapShot.getChildren()) {
                    //Getting product
                    Product product = postSnapshot.getValue(Product.class);
                    //Adding product to the list
                    products.add(product);
                }

                //Creating adapter
                ProductList productsAdapter = new ProductList(MainActivity.this, products);

                //Attaching adapter to the list view
                listViewProducts.setAdapter(productsAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void showUpdateDeleteDialog(final String productId, String productName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextPrice  = (EditText) dialogView.findViewById(R.id.editTextPrice);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);

        dialogBuilder.setTitle(productName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                double price = Double.parseDouble(String.valueOf(editTextPrice.getText().toString()));
                if (!TextUtils.isEmpty(name)) {
                    updateProduct(productId, name, price);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(productId);
                b.dismiss();
            }
        });
    }

    //Parameters get the current id, name, and price of the product so that it can be updated
    private void updateProduct(String id, String name, double price) {
        //Toast.makeText(getApplicationContext(), "NOT IMPLEMENTED YET", Toast.LENGTH_LONG).show();

        //Getting the specified product reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("products").child(id);

        //Updating product
        Product product = new Product(id, name, price);
        dR.setValue(product); //Setting the new values for the product to be updated

        //Show a success message using a toast
        Toast.makeText(getApplicationContext(), "Product Updated", Toast.LENGTH_LONG).show();
    }

    //Parameter is the unique ID reference to the product that will be deleted from the database
    private void deleteProduct(String id) {
        //Toast.makeText(getApplicationContext(), "NOT IMPLEMENTED YET", Toast.LENGTH_LONG).show();

        //Getting the specified product reference from the ID parameter
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("products").child(id);

        //Removing the product
        dR.removeValue();

        //Show a success message using a toast
        Toast.makeText(getApplicationContext(), "Product Deleted", Toast.LENGTH_SHORT).show();

        //In the slides this method is not a void method but instead a boolean method
        //I am not sure if we should keep this
        //return true;
    }

    //Adding products to the database method
    private void addProduct() {
        //Toast.makeText(this, "NOT IMPLEMENTED YET", Toast.LENGTH_LONG).show();

        //Getting the values from the text boxes for name and the price
        String name = editTextName.getText().toString().trim();
        double price = Double.parseDouble(String.valueOf(editTextPrice.getText().toString()));

        //Checking the boxes to validate that the boxes are provided
        //If the text boxes contain values do the following and print a success
        if (!TextUtils.isEmpty((name))) {

            //Getting a unique id using push().getKey() method
            //It will create an unique id and we will use it as the Primary Key for our product
            //The ID for each product will be saved to the database
            String id = databaseProducts.push().getKey();

            //Creating a new product object (this is the product that we are going to be adding to the database with a id, name, and price)
            Product product = new Product (id, name, price);

            //Saving the Product
            databaseProducts.child(id).setValue(product);

            //Clearing the text boxes so that it will be blank again
            editTextName.setText("");
            editTextPrice.setText("");

            Toast.makeText(this, "Product ADDED", Toast.LENGTH_LONG).show();
        }

        //The text box value was not given! Displaying a toast
        else {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }
}