package com.example.jerrychen.barapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
   private EditText editTextEmail,editTextPassword;
    private FirebaseAuth firebaseAuth;
    private String email,password;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);
        firebaseAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        dbRef=database.getReferenceFromUrl("https://barapp-de43a.firebaseio.com/User");
    }

    public void buttonClickRegister(View view) {
         email=editTextEmail.getText().toString().trim();
         password=editTextPassword.getText().toString().trim();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Registered Successfully",Toast.LENGTH_LONG).show();
                    FirebaseUser user=firebaseAuth .getCurrentUser();
                    User customer=new Customer("jerry",email);

                  //  Toast.makeText(MainActivity.this,user.getUid(),Toast.LENGTH_LONG).show();
                    if (user.getUid().isEmpty()==false) {
                        registerInDb(user.getUid(),customer);
                    }else {
                        Toast.makeText(MainActivity.this,"cannot get data",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(MainActivity.this,"Registered failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public void buttonClickLogin(View view) {
        email=editTextEmail.getText().toString();
        password=editTextPassword.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "email or password cannot be empty", Toast.LENGTH_LONG).show();
        }else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                                FirebaseUser user=firebaseAuth .getCurrentUser();
                                uid=user.getUid();
                                readDb(uid);
                            } else {
                                Toast.makeText(MainActivity.this, "Wrong email or password", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
    }
    public void readDb(String uid){
        DatabaseReference userDb=database.getReferenceFromUrl("https://barapp-de43a.firebaseio.com/User/"+ uid);
        Log.d("D_value", "URL "+"https://barapp-de43a.firebaseio.com/User/"+ uid);
       userDb.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,String> map=(Map<String, String>) dataSnapshot.getValue();
                String name=map.get("name");
                String age=map.get("age");
                String email=map.get("email");

                Log.d("D_value", "name "+name);
                Log.d("D_value", "age "+age);
                Log.d("D_value", "email "+email);

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
    }
    public void registerInDb(String uid ,User user){
//        DatabaseReference childRef=dbRef.child(uid);
//        childRef.child("name").setValue("Jerry");
//        childRef.child("age").setValue(22);
        dbRef.child(uid).setValue(user);
    }

    public void buttonClickDbTest(View view) {
     //   registerInDb("qwjdoiqwjd");
    }

    public void buttonClickNavigation(View view) {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
