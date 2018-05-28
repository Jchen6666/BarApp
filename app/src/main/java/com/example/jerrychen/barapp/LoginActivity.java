package com.example.jerrychen.barapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
/**
 * A simple activity containing form for logging in
 * @see AppCompatActivity
 */
public class LoginActivity extends AppCompatActivity {
    AutoCompleteTextView editTextEmail,editTextPassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;
    protected static String isStaff;
    private ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
         dbRef = firebaseDatabase.getReference();
        editTextEmail=(AutoCompleteTextView)findViewById(R.id.userEmail);
        editTextPassword=(AutoCompleteTextView)findViewById(R.id.userPassword);
        firebaseDatabase=FirebaseDatabase.getInstance();
        if (user!=null){
            Intent intent = new Intent(LoginActivity.this, StaffInterfaceActivity.class);
            dbRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                    isStaff = String.valueOf(map.get("staff"));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            startActivity(intent);
        }
        cd=new ConnectionDetector(this);
    }

    public void buttonClickLogin(View view) {
        String email=editTextEmail.getText().toString();
       String password=editTextPassword.getText().toString();
       if (cd.isConnected()) {
           Log.d("TAG CONNECTION","Connected");
           firebaseAuth.signInWithEmailAndPassword(email, password)
                   .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               FirebaseUser user = firebaseAuth.getCurrentUser();
                               dbRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                   Intent intent = new Intent(LoginActivity.this, StaffInterfaceActivity.class);
                                   @Override
                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                       Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                                       isStaff = String.valueOf(map.get("staff"));
                                       if (isStaff == "true") {
                                           Toast.makeText(LoginActivity.this, "Staff Login Successfully", Toast.LENGTH_LONG).show();
                                           Staff staff = dataSnapshot.getValue(Staff.class);
                                           intent.putExtra("isStaff", isStaff);
                                           intent.putExtra("user", staff);
                                       } else if (isStaff == "false") {
                                           Toast.makeText(LoginActivity.this, "Customer Login Successfully", Toast.LENGTH_LONG).show();
                                       }
                                       startActivity(intent);
                                   }

                                   @Override
                                   public void onCancelled(DatabaseError databaseError) {

                                   }
                               });

                           } else {
                               Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                           }
                       }
                   });
       }
       else {
           Toast.makeText(LoginActivity.this,"Please check the internet",Toast.LENGTH_LONG).show();
       }
    }

    public void buttonClickCreate(View view) {
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}
