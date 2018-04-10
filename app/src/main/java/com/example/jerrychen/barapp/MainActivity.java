package com.example.jerrychen.barapp;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
   private EditText editTextEmail,editTextPassword;
    private FirebaseAuth firebaseAuth;
    private String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        firebaseAuth=FirebaseAuth.getInstance();

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
                }else {
                    Toast.makeText(MainActivity.this,"Registered failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public void buttonClickLogin(View view) {
        email=editTextEmail.getText().toString().trim();
        password=editTextPassword.getText().toString().trim();
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(MainActivity.this,"Login failed",Toast.LENGTH_LONG).show();
                    }
                }
            });
        Intent intent=new Intent();

    }
}
