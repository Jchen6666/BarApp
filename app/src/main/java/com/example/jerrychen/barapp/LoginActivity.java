package com.example.jerrychen.barapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    AutoCompleteTextView editTextEmail,editTextPassword;
    private FirebaseAuth firebaseAuth;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        editTextEmail=(AutoCompleteTextView)findViewById(R.id.userEmail);
        editTextPassword=(AutoCompleteTextView)findViewById(R.id.userPassword);

    }

    public void buttonClickLogin(View view) {
        Toast.makeText(LoginActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();

        String email=editTextEmail.getText().toString();
       String password=editTextPassword.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                         //   Toast.makeText(LoginActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
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
