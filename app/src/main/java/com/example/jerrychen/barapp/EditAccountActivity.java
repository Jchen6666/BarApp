package com.example.jerrychen.barapp;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditAccountActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    private FirebaseUser user;
    private String gender;
    private int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        //Initialize view objects
        final Button update=findViewById(R.id.updateAccount);
        final EditText editTextName=findViewById(R.id.editAccountName);
        final EditText editTextAge=findViewById(R.id.editAccountAge);
        final Spinner spinnerGender=findViewById(R.id.spinnerGender);
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        dbRef=firebaseDatabase.getReference();
        user=firebaseAuth.getCurrentUser();
        spinnerGender.setAdapter(new ArrayAdapter<Gender>(this,android.R.layout.simple_list_item_1,Gender.values()));
        //Retrieve user profile from firebase database
        dbRef.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 //Get name and age

                  String name=dataSnapshot.child("name").getValue(String.class);
                  Long age= dataSnapshot.child("age").getValue(Long.class);
                  gender=dataSnapshot.child("gender").getValue(String.class);
                  editTextName.setText(name);
                  if(age!=null) {
                      editTextAge.setText(age.toString());
                  }
                  if (gender!=null) {
                      for (int i = 0; i < Gender.values().length; i++) {
                          if (gender.equals(Gender.values()[i].toString())) {
                              index = i;
                              break;
                          }
                      }
                      spinnerGender.setSelection(index);
                  }
               }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        editTextAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (editTextAge.getText().toString().isEmpty()||editTextName.getText().toString().isEmpty()) {
                  //  update.setEnabled(false);
                }

            }
        });
        editTextName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (editTextAge.getText().toString().isEmpty()||editTextName.getText().toString().isEmpty()) {
                   // update.setEnabled(false);
                }

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Gender userGender=(Gender)spinnerGender.getItemAtPosition(spinnerGender.getSelectedItemPosition());
                    if (editTextAge.getText().toString().isEmpty()==false&&editTextName.getText().toString().isEmpty()==false) {
                        if (Long.parseLong(editTextAge.getText().toString()) > 0) {
                            dbRef.child("users").child(user.getUid()).child("name").setValue(editTextName.getText().toString());
                            dbRef.child("users").child(user.getUid()).child("age").setValue(Long.parseLong(editTextAge.getText().toString()));
                            dbRef.child("users").child(user.getUid()).child("gender").setValue(userGender);
                        }
                        else {
                            Toast.makeText(EditAccountActivity.this, "Age must be bigger than 0", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                        Toast.makeText(EditAccountActivity.this,"fill out the list",Toast.LENGTH_LONG);
            }
        });
    }
}
