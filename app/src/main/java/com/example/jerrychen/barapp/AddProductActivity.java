package com.example.jerrychen.barapp;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProductActivity extends AppCompatActivity {
    private EditText etName,etPrice,etVolume,etDescription,etURL;
    private Spinner sCategory;
    private CheckBox cAvailability;
    private Button bAdd;
    private DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        etName=(EditText)findViewById(R.id.editTextName);
        etPrice=(EditText)findViewById(R.id.editTextPrice);
        etVolume=(EditText)findViewById(R.id.editTextVolume);
        etDescription=(EditText)findViewById(R.id.editTextDescription);
        etURL=(EditText)findViewById(R.id.editTextURL);
        sCategory=(Spinner)findViewById(R.id.spinnerCategory);
        sCategory.setAdapter(new ArrayAdapter<ProductCategory>(this,android.R.layout.simple_list_item_1,ProductCategory.values()));
        cAvailability=(CheckBox)findViewById(R.id.checkBoxAvailable);
        bAdd=(Button)findViewById(R.id.buttonAdd);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference();

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    String name=etName.getText().toString();
                    Integer price=Integer.valueOf(etPrice.getText().toString());
                    Double volume=Double.valueOf(etVolume.getText().toString());
                    String description=etDescription.getText().toString();
                    String URL=etURL.getText().toString();
                    boolean availability=cAvailability.isChecked();
                    ProductCategory category=(ProductCategory)sCategory.getItemAtPosition(sCategory.getSelectedItemPosition());
                    Product product=new Product(URL,name,price,volume,description,category,availability);
                    mDatabaseReference.child("Products").child(product.getCategory().toString()).child(product.getName()).setValue(product);
                    Toast.makeText(AddProductActivity.this,"Product successfully added to the menu",Toast.LENGTH_LONG).show();
                    etName.setText("");
                    etPrice.setText("");
                    etVolume.setText("");
                    etDescription.setText("");
                    etURL.setText("");
                    cAvailability.setChecked(false);
                }   catch(Exception e){
                    Toast.makeText(AddProductActivity.this,"Product not added, missing parameters",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
