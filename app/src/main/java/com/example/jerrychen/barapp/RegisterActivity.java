package com.example.jerrychen.barapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword, editTextName, editTextAge;
    String email,name,password;
    int age;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReferenceFromUrl("https://barapp-de43a.firebaseio.com/users");
    }

    public void buttonClickRegister(View view) {
         email = editTextEmail.getText().toString().trim();
         password = editTextPassword.getText().toString().trim();
         name = editTextName.getText().toString().trim();
         age = Integer.parseInt(editTextAge.getText().toString());

        if (age >= 18) {

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Exception exception=task.getException();
                        if (exception instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(RegisterActivity.this,"email already being used",Toast.LENGTH_LONG).show();
                        }
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Registerd successfully", Toast.LENGTH_LONG).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String uid = user.getUid();
                            if (uid.isEmpty()) {
                                Toast.makeText(RegisterActivity.this, "cannot get data", Toast.LENGTH_LONG).show();
                            } else {
                                User customer = new Customer(name, email, age);
                                dbRef.child(uid).setValue(customer);
                            }
                        }
                    }
                });
            }
        else {
            Toast.makeText(RegisterActivity.this,"Under 18 not allowed",Toast.LENGTH_LONG).show();
        }
    }


    }

