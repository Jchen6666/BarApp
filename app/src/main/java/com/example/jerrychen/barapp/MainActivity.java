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

public class MainActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword, editTextAge;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextAge = findViewById(R.id.editTextAge);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void buttonClickRegister(View view) {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        int age = Integer.parseInt(editTextAge.getText().toString().trim());
        if (age >= 18) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Registered failed", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else
            Toast.makeText(MainActivity.this,"Under 18 not allowed", Toast.LENGTH_LONG).show();


    }
}





