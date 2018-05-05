package com.example.jerrychen.barapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    AutoCompleteTextView editTextEmail,editTextPassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    protected static String isStaff;
    //SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        editTextEmail=(AutoCompleteTextView)findViewById(R.id.userEmail);
        editTextPassword=(AutoCompleteTextView)findViewById(R.id.userPassword);
        firebaseDatabase=FirebaseDatabase.getInstance();
    }

    public void buttonClickLogin(View view) {
        String email=editTextEmail.getText().toString();
       String password=editTextPassword.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user=firebaseAuth.getCurrentUser();
                         //   Toast.makeText(LoginActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                            DatabaseReference dbRef=firebaseDatabase.getReference();
                            dbRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                Intent intent=new Intent(LoginActivity.this,StaffInterfaceActivity.class);
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Map<String,String>map=(Map<String, String>)dataSnapshot.getValue();
                                     isStaff=String.valueOf(map.get("staff"));
                                    if (isStaff=="true"){
                                        Toast.makeText(LoginActivity.this,"Staff Login Successfully",Toast.LENGTH_LONG).show();
                                        Staff staff= dataSnapshot.getValue(Staff.class);
                                        intent.putExtra("isStaff",isStaff);
                                        intent.putExtra("user",staff);
                                    }else if(isStaff=="false"){
                                        Toast.makeText(LoginActivity.this,"Customer Login Successfully",Toast.LENGTH_LONG).show();
                                        Customer customer=dataSnapshot.getValue(Customer.class);
                                        intent.putExtra("isStaff",isStaff);
                                        intent.putExtra("user",customer);
                                    }
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }else {
                            Toast.makeText(LoginActivity.this,"Login failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void buttonClickCreate(View view) {
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}
